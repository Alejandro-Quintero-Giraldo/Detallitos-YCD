package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Domicile;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DomicileModel {

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

    public  void createDomicile(Domicile domicile) throws SQLException {
        String query = "INSERT INTO DOMICILIO(id_domicilio, direccion_entrega, encargado_entrega, hora_llegada) VALUES(?.?.?.?)";
        prepareBD(query);
        this.preparedStatement.setString(1, domicile.getDomicileId());
        this.preparedStatement.setString(2, domicile.getDeliveryAddress());
        processQuery("create");
        finishProcess();
    }

    public void updateDomicile(Domicile domicile) throws SQLException {
        String query = "UPDATE DOMICILIO SET direccion_entrega = ?, encargado_entrega = ?, hora_llegada = ? WHERE id_domicilio = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, domicile.getDeliveryAddress());
        this.preparedStatement.setString(2, domicile.getDeliveryPerson());
        this.preparedStatement.setObject(3, domicile.getArrivalTime());
        this.preparedStatement.setString(4, domicile.getDomicileId());
        processQuery("update");
        finishProcess();
    }

}
