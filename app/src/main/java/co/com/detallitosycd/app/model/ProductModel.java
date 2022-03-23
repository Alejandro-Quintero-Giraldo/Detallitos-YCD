package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductModel extends Conection {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.product");

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
        } else if(action.equals("create")){
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

    public User login(User userDTO) throws SQLException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatemen = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM USUARIO WHERE CORREOELECTRONICO = ? AND CONTRASEÑA = ?;";
        //String query = "select * from Usuario where CorreoElectronico = '"+userDTO.getEmail()+"' and Contraseña = '"+userDTO.getPassword()+"';";

        try {
            connection = Conection.conect();
            preparedStatemen = connection.prepareStatement(query);
            preparedStatemen.setString(1, userDTO.getEmail());
            preparedStatemen.setString(2, userDTO.getPassword());
            LOGGER.log(Level.INFO, preparedStatemen.toString());
            resultSet = preparedStatemen.executeQuery();

            while (resultSet.next() ) {
                System.out.println("resultSet " + resultSet.getString(1));
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));
            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error App", e.getMessage());
        } finally {

            System.out.println("por el finally");
        }
        return user;

    }

    public void createProduct(Product productInfo) throws SQLException {
        String query = "INSERT INTO PRODUCTO(id_producto, nombre_producto,tipo_producto,precio_producto,cantidad_existencias," +
                "descripcion) VALUES (?,?,?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, productInfo.getProductId());
        this.preparedStatement.setString(2, productInfo.getProductName());
        this.preparedStatement.setString(3, productInfo.getProductType());
        this.preparedStatement.setInt(4, productInfo.getProductPrice());
        this.preparedStatement.setInt(5, productInfo.getAmountStock());
        this.preparedStatement.setString(6, productInfo.getDescription());
        processQuery("create");
        finishProcess();


        /*System.out.println(userInfo.getUserId()+" "+userInfo.getCellphone()+" "+userInfo.getEmail()+" "+userInfo.getAddress()+" "+userInfo.getPassword());
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String query = "INSERT INTO USUARIO(IdUsuario, NombreUsuario, Celular,CorreoElectronico,Direccion, Contraseña) " +
                "VALUES( ? , ? , ? , ? , ? , ?)";
        try {
            connection = Conection.conect();
            preparedStatement = connection.prepareStatement(query);
            System.out.println("El prepare statement");
            preparedStatement.setString(1,userInfo.getUserId());
            preparedStatement.setString(2,userInfo.getUserName());
            preparedStatement.setString(3,userInfo.getCellphone());
            preparedStatement.setString(4,userInfo.getEmail());
            preparedStatement.setString(5,userInfo.getAddress());
            preparedStatement.setString(6,userInfo.getPassword());
            LOGGER.log(Level.INFO, preparedStatement.toString());
            resultSet = preparedStatement.execute();

            //System.out.println("resultSet " + resultSet.getString("NombreUsuario"));
        /*
            if (resultSet.next() == true) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }*/
        /*} catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error", e.getMessage());
        } finally {

            if (preparedStatement != null && preparedStatement.isClosed() == false) preparedStatement.close();
            preparedStatement = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;
        }*/
    }
}