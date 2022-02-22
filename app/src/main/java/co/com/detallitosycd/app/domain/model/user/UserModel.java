
package co.com.detallitosycd.app.domain.model.user;

import co.com.detallitosycd.app.domain.entity.User;
import co.com.detallitosycd.app.domain.model.conection.Conection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel extends Conection {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.domain.model.user");

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
                LOGGER.log(Level.INFO, user.getUserName());

            }
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.log(Level.INFO, "{0}Error App", e.getMessage());
        } finally {

            if (resultSet != null && resultSet.isClosed() == false) resultSet.close();
            resultSet = null;
            if (preparedStatemen != null && preparedStatemen.isClosed() == false) preparedStatemen.close();
            preparedStatemen = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;
            System.out.println("por el finally");
        }
        return user;

    }

    public void register(User userInfo) throws SQLException {
        System.out.println(userInfo.getUserId()+" "+userInfo.getCellphone()+" "+userInfo.getEmail()+" "+userInfo.getAddress()+" "+userInfo.getPassword());
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        boolean resultSet = false;
        String query = "INSERT INTO USUARIO(IdUsuario, NombreUsuario, Celular,CorreoElectronico,Direccion, Contraseña) " +
                "VALUES( ? , ? , ? , ? , ? , ?)";
        LOGGER.log(Level.INFO, query);
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

            resultSet = preparedStatement.execute();

            //System.out.println("resultSet " + resultSet.getString("NombreUsuario"));
        /*
            if (resultSet.next() == true) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }*/
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error", e.getMessage());
        } finally {

            if (preparedStatement != null && preparedStatement.isClosed() == false) preparedStatement.close();
            preparedStatement = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;
        }
    }
}