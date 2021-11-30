
package co.com.detallitosycd.app.domain.model.user;

import co.com.detallitosycd.app.domain.dto.UserDTO;
import co.com.detallitosycd.app.domain.entity.User;
import co.com.detallitosycd.app.domain.model.conection.Conection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel extends Conection {
    
    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.domain.model.user");
    
    public User login(UserDTO userDTO) throws Exception{
        Conection conection;
        User user = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM USUARIO WHERE CorreoElectronico = "+userDTO.getEmail()
                + " AND Contraseña = "+userDTO.getPassword();
        
        conection = new Conection();
        try{
            connection = conection.conect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next() == true){
                user = new User(resultSet.getString("IdUsuario"),
                        resultSet.getString("NombreUsuario"), resultSet.getString("Celular"),
                        resultSet.getString("Direccion"), resultSet.getString("CorreoElectronico"),
                        resultSet.getString("Contraseña")); 
                
            }
        } catch(SQLException e){
            LOGGER.log(Level.INFO, "{0}Error ", e.getMessage());
        } finally {
            
            if(resultSet != null && resultSet.isClosed() == false) resultSet.close();
            resultSet = null;
            if(statement != null && statement.isClosed() == false) statement.close();
            statement = null;
            if(connection != null && connection.isClosed() == false) connection.close();
            connection = null;
            
        }
     return user;
    }
    
}