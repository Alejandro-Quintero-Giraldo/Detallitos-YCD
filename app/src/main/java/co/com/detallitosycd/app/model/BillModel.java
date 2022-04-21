package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Bill;
import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.State;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void createBill(Bill bill, Integer amountPurchased, String especifications,String productId) throws SQLException {
        List<State> stateList = stateModel.findAllState();
        stateList.forEach(state -> {
                    if (state.getStateName().equals("DISPONIBLE")) bill.setStateId(state.getStateId());
                });
        bill.setBillId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMAAAA-hhmmss")));
        billProductModel.createBillProduct(new BillProduct(bill.getBillId(), productId,amountPurchased,null,especifications));
        String query = "INSERT INTO FACTURA(id_factura, fecha_factura, usuario_id, empresa_nit, estado_id) VALUES(?,?,?,?,?)";
        prepareBD(query);
    }

}
