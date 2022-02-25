package co.com.detallitosycd.app;

import co.com.detallitosycd.app.model.conection.Conection;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppApplication {
        
        private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app");
        
	public static void main(String[] args) {
            Conection conection = new Conection();
            if(conection.conect() != null){
                LOGGER.info("The conection to BD has successfull");
                SpringApplication.run(AppApplication.class, args);
            } else {
                LOGGER.info("The conection to BD has failed!");
            }
            
	}

}
