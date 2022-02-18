
package co.com.detallitosycd.app.domain.model.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class Conection {
	
    private static final Logger  LOGGER = Logger.getLogger("co.com.detallitosycd.app.domain.model.conection");
    
    
    public static Connection conect(){
        String dataBase = "detallitosycd";
        String server = "jdbc:mysql://localhost/"+dataBase;
        String userDataBase = "root";
        String pass = "";
        
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(server,userDataBase, pass);
        } catch(Exception e){
            LOGGER.warning("The conection to BD has failed "+ e.getMessage());
        }
        return connection;
    }
}
