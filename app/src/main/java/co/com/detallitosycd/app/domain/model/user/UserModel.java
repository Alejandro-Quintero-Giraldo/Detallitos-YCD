
package co.com.detallitosycd.app.domain.model.user;

import co.com.detallitosycd.app.domain.entity.User;
import co.com.detallitosycd.app.domain.model.conection.Conection;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel extends Conection {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.domain.model.user");

    public User login(User userDTO) throws SQLException {
        User user = null;
        Connection connection = null;
        ResultSet resultSet = null;


        String query = "select * from Usuario where CorreoElectronico = ? and Contraseña = ?";
        connection = Conection.conect();
        PreparedStatement preparedStatemen = connection.prepareStatement(query);
        preparedStatemen.setString(1, userDTO.getEmail());
        preparedStatemen.setString(2, userDTO.getPassword());
        System.out.println("Se pone este mensaje por si acaso");
        LOGGER.log(Level.INFO, preparedStatemen.toString());
        //try {
            resultSet = preparedStatemen.executeQuery(query);
            System.out.println("resultSet " + resultSet.getString("NombreUsuario"));
            if (resultSet.next() == true) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }
        try{
            System.out.println("Despues try");
        } catch (Exception e) {
            System.out.println("Falló la aplicación");
            //LOGGER.log(Level.INFO, "{0}Error App", e.getMessage());
        } finally {

            if (resultSet != null && resultSet.isClosed() == false) resultSet.close();
            resultSet = null;
            if (preparedStatemen != null && preparedStatemen.isClosed() == false) preparedStatemen.close();
            preparedStatemen = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;

        }
        return user;
    }

    public User register(User userInfo) throws SQLException {
        System.out.println(userInfo.getUserId());
        User user = null;
        ResultSet resultSet = null;
        Connection connection = Conection.conect();
        String query = "INSERT INTO USUARIO(IdUsuario, NombreUsuario, Celular,CorreoElectronico,Direccion, Contraseña) VALUES( ?, ?, ?, ?, ?, ?)" ;

        //try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
        System.out.println("El prepare statement");
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getCellphone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPassword());
            resultSet = preparedStatement.executeQuery(query);
            LOGGER.log(Level.INFO, preparedStatement.toString());
            System.out.println("resultSet " + resultSet.getString("NombreUsuario"));

            if (resultSet.next() == true) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }
        try {
            System.out.println("Despues del try register");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error", e.getMessage());
        } finally {

            if (resultSet != null && resultSet.isClosed() == false) resultSet.close();
            resultSet = null;
            if (preparedStatement != null && preparedStatement.isClosed() == false) preparedStatement.close();
            preparedStatement = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;
        }
        return user;
    }
}