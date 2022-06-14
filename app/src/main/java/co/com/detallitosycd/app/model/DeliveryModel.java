package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Delivery;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryModel {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.productType");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery(String action) throws SQLException {
        if(action.equals("query")){
            this.resultSet = this.preparedStatement.executeQuery();
        } else if(action.equals("create") || action.equals("update")){
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

    public void createDelivery(Delivery delivery) throws SQLException {
        String query = "INSERT INTO ENTREGA(id_entrega, tipo_entrega, domicilio_id) VALUES (?,?,?)";
        processQuery(query);
        this.preparedStatement.setString(1, delivery.getDeliveryId());
        this.preparedStatement.setString(2, delivery.getDeliveryType());
        this.preparedStatement.setString(3, delivery.getDomicileId());
        processQuery("create");
        finishProcess();
    }

}
