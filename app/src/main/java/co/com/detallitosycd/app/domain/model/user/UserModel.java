
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
        ResultSet resultSet = null;
        Statement statement = null;
        String query = "select * from Usuario where CorreoElectronico = '"+userDTO.getEmail()+"' and Contraseña = '"+userDTO.getPassword()+"';";
        //PreparedStatement preparedStatemen = connection.prepareStatement(query);
        //preparedStatemen.setString(1, userDTO.getEmail());
        //preparedStatemen.setString(2, userDTO.getPassword());
        LOGGER.log(Level.INFO, query);
        try {
            connection = Conection.conect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            //System.out.println("resultSet " + resultSet.getString("NombreUsuario"));
            if (resultSet.next()) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error App", e.getMessage());
        } finally {

            if (resultSet != null && resultSet.isClosed() == false) resultSet.close();
            resultSet = null;
            if (statement != null && statement.isClosed() == false) statement.close();
            statement = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;

        }
        return user;
    }

    public void register(User userInfo) throws SQLException {
        System.out.println(userInfo.getUserId()+" "+userInfo.getCellphone()+" "+userInfo.getEmail()+" "+userInfo.getAddress()+" "+userInfo.getPassword());
        Statement statement = null;
        Connection connection = null;
        boolean resultSet = false;
        String query = "INSERT INTO USUARIO(IdUsuario, NombreUsuario, Celular,CorreoElectronico,Direccion, Contraseña) " +
                "VALUES('"+userInfo.getUserId()+"','"+userInfo.getUserName()+"', '"+userInfo.getCellphone()+"','"+
                userInfo.getEmail()+"', '"+userInfo.getAddress()+"','"+userInfo.getPassword()+"')";
        LOGGER.log(Level.INFO, query);
        try {
           /* PreparedStatement preparedStatement = connection.prepareStatement(query);
        System.out.println("El prepare statement");
            preparedStatement.setString(1, );
            preparedStatement.setString(2, );
            preparedStatement.setString(3, );
            preparedStatement.setString(4, );
            preparedStatement.setString(5, );
            preparedStatement.setString(6, );
            */
            connection = Conection.conect();
            statement = connection.createStatement();
            resultSet = statement.execute(query);

            //System.out.println("resultSet " + resultSet.getString("NombreUsuario"));
        /*
            if (resultSet.next() == true) {
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña"));

            }*/

            System.out.println("Despues del try register");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "{0}Error", e.getMessage());
        } finally {

            if (statement != null && statement.isClosed() == false) statement.close();
            statement = null;
            if (connection != null && connection.isClosed() == false) connection.close();
            connection = null;
        }
    }
}