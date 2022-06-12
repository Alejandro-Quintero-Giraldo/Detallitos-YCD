package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Company;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyModel {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.company");


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery(String type) throws SQLException {
        if(type.equals("create")){
            this.preparedStatement.execute();
        } else if(type.equals("query")) {
            this.resultSet = this.preparedStatement.executeQuery();
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

    public void createCompany(Company company) throws SQLException {
        String query = "INSERT INTO EMPRESA(nit, celular, correo_electronico, nombre_empresa) VALUES(?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, company.getNIT());
        this.preparedStatement.setString(2, company.getCellphone());
        this.preparedStatement.setString(3, company.getEmail());
        this.preparedStatement.setString(4, company.getNameCompany());
        processQuery("create");
        finishProcess();
    }

    public Company findCompanyByNit(String nit) throws SQLException {
        String query = "SELECT * FROM EMPRESA WHERE nit = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, nit);
        processQuery("query");
        Company company = null;
        if(this.resultSet.next()){
            company = new Company(this.resultSet.getString("nit"), this.resultSet.getString("nombre_empresa"),
                    this.resultSet.getString("celular"), this.resultSet.getString("correo_electronico"));
        }
        finishProcess();
        return company;
    }

}
