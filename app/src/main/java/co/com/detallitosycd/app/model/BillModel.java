package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillModel {


    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.bill");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ProductModel productModel;
    private BillProductModel billProductModel;


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
        StateModel stateModel = new StateModel();
        return stateModel.findAllState().stream()
                .filter(state -> state.getStateName().equals("DISPONIBLE"))
                .findAny().get().getStateId();
    }

    public void createBill(Bill bill, Integer amountPurchased, String especifications,String productId) throws SQLException {
        bill.setStateId(searchIdAvailableState());
        bill.setBillId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy-hhmmss")));
        billProductModel = new BillProductModel();
        billProductModel.createBillProduct(new BillProduct(bill.getBillId(), productId,amountPurchased,null,especifications));
        String query = "INSERT INTO FACTURA(id_factura,fecha_factura, usuario_id, empresa_nit, estado_id) VALUES(?,?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, bill.getBillId());
        this.preparedStatement.setObject(2, LocalDateTime.now());
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
                    this.resultSet.getObject("fecha_factura",LocalDateTime.class),
                    this.resultSet.getInt("precio_final"),this.resultSet.getString("usuario_id"),
                    this.resultSet.getString("empresa_nit"), this.resultSet.getString("entrega_id"),
                    this.resultSet.getString("estado_id"));
        }
        finishProcess();
        return  bill;
    }

    public Bill findAvailableBill(String userId) throws  SQLException {
        String query = "SELECT * FROM FACTURA WHERE estado_id = ? AND usuario_id = ?";
        String stateAvailableId = searchIdAvailableState();
        prepareBD(query);
        this.preparedStatement.setString(1, stateAvailableId);
        this.preparedStatement.setString(2, userId);
        processQuery("query");
        Bill bill = null;
        if(this.resultSet.next()){
            bill = new Bill(this.resultSet.getString("id_factura"),
                    this.resultSet.getObject("fecha_factura",LocalDateTime.class),
                    this.resultSet.getInt("precio_final"),this.resultSet.getString("usuario_id"),
                    this.resultSet.getString("empresa_nit"), this.resultSet.getString("entrega_id"),
                    this.resultSet.getString("estado_id"));
        }
        finishProcess();
        return bill;
    }

    public boolean putProductInAnAvailableBill(String activeBillId, String productId,
                                            String especifications, Integer amountPurchased) throws SQLException {
        billProductModel = new BillProductModel();
        List<BillProduct> billProductList = billProductModel.findBillProductsByBillId(activeBillId);
        boolean billProductExists = billProductList.stream()
                .filter(billProduct1 -> billProduct1.getIdProduct().equals(productId))
                .anyMatch(billProduct1 -> billProduct1 != null);
        System.out.println("billProductExists "+billProductExists);
        if(billProductExists){
            return false;
        } else {
            System.out.println("error");
            billProductModel.createBillProduct(new BillProduct(activeBillId, productId, amountPurchased, null, especifications));
            return true;
        }
    }

    public void updateBill(Bill bill) throws SQLException {
        String query = "UPDATE FACTURA SET fecha_factura = ?, precio_final = ?, entrega_id = ?, estado_id = ? WHERE id_factura = ? AND usuario_id = ?";
        prepareBD(query);
        this.preparedStatement.setObject(1, bill.getDateBill());
        this.preparedStatement.setInt(2, bill.getFinalPrice());
        this.preparedStatement.setString(3, bill.getDeliverId());
        this.preparedStatement.setString(4, bill.getStateId());
        this.preparedStatement.setString(5, bill.getBillId());
        this.preparedStatement.setString(6, bill.getUserId());
        processQuery("update");
        finishProcess();
    }
}
