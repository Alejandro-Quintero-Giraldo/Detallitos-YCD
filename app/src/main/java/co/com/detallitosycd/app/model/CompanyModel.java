package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Company;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyModel extends Conection {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.company");


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery() throws SQLException {
        this.resultSet = this.preparedStatement.executeQuery();
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

    public Company findCompanyByNit(String nit) throws SQLException {
        String query = "SELECT * FROM EMPRESA WHERE nit = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, nit);
        processQuery();
        Company company = null;
        if(this.resultSet.next()){
            company = new Company(this.resultSet.getString("nit"), this.resultSet.getString("nombre_empresa"),
                    this.resultSet.getString("celular"), this.resultSet.getString("correo_electronico"));
        }
        finishProcess();
        return company;
    }

}
