package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.State;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BillModel {


    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.bill");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ProductModel productModel;
    private BillProductModel billProductModel;
    private StateModel stateModel;



    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery(String action) throws SQLException {
        if(action.equals("query")){
            this.resultSet = this.preparedStatement.executeQuery();
        } else if(action.equals("create") || action.equals("update") || action.equals("delete")){
            this.preparedStatement.execute();
        }
        LOGGER.log(Level.INFO, this.preparedStatement.toString());
    }

    private void finishProcess() throws SQLException {
        if (this.resultSet != null && Boolean.FALSE.equals(this.resultSet.isClosed())) this.resultSet.close();
        this.resultSet = null;
        if (this.preparedStatement != null && Boolean.FALSE.equals(this.preparedStatement.isClosed())) this.preparedStatement.close();
        this.preparedStatement = null;
        if (this.connection != null && Boolean.FALSE.equals(this.connection.isClosed())) this.connection.close();
        this.connection = null;
    }

    public String searchIdAvailableState() throws SQLException {
        stateModel = new StateModel();
        return stateModel.findAllState().stream()
                .filter(state -> state.getStateName().equals("DISPONIBLE"))
                .findAny().get().getStateId();
    }

    public void createBill(Bill bill, Integer amountPurchased, String especifications,String productId) throws SQLException {
        bill.setStateId(searchIdAvailableState());
        bill.setBillId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy-hhmmss")));
        System.out.println("billID "+bill.getBillId());
        bill.setDateBill(LocalDateTime.now());
        billProductModel = new BillProductModel();
        billProductModel.createBillProduct(new BillProduct(bill.getBillId(), productId,amountPurchased,null,especifications));
        String query = "INSERT INTO FACTURA(id_factura, fecha_factura, usuario_id, empresa_nit, estado_id) VALUES(?,?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, bill.getBillId());
        this.preparedStatement.setDate(2, (Date) Date.from(bill.getDateBill().atZone(ZoneId.systemDefault()).toInstant()));
        this.preparedStatement.setString(3, bill.getUserId());
        this.preparedStatement.setString(4, bill.getCompanyNIT());
        this.preparedStatement.setString(5, bill.getStateId());
        processQuery("create");
        finishProcess();
    }

    public Bill findBillById(String billId) throws SQLException {
        String query = "SELECT * FROM FACTURA WHERE id_factura = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, billId);
        processQuery("query");
        Bill bill = null;
        if(this.resultSet.next()){
            bill = new Bill(this.resultSet.getString("id_factura"),
                    this.resultSet.getDate("fecha_factura").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    this.resultSet.getInt("precio_final"),this.resultSet.getString("usuario_id"),
                    this.resultSet.getString("empresaNIT"), this.resultSet.getString("id_entrega"),
                    this.resultSet.getString("estado_id"));
        }
        finishProcess();
        return  bill;
    }

    public Bill findAvailableBill() throws  SQLException {
        String query = "SELECT * FROM FACTURA WHERE estado_id = ?";
        String stateAvailableId = searchIdAvailableState();
        prepareBD(query);
        this.preparedStatement.setString(1, stateAvailableId);
        processQuery("query");
        Bill bill = null;
        if(this.resultSet.next()){
            bill = new Bill(this.resultSet.getString("id_factura"),
                    this.resultSet.getDate("fecha_factura").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    this.resultSet.getInt("precio_final"),this.resultSet.getString("usuario_id"),
                    this.resultSet.getString("empresaNIT"), this.resultSet.getString("id_entrega"),
                    this.resultSet.getString("estado_id"));
        }
        finishProcess();
        return bill;
    }

    public void putProductInAnAvailableBill(String activeBillId, String productId,
                                            String especifications, Integer amountPurchased) throws SQLException {
        billProductModel.createBillProduct(new BillProduct(activeBillId,productId,amountPurchased, null, especifications));
    }



}
