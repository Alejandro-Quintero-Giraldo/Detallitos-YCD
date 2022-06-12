package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Company;
import co.com.detallitosycd.app.entity.State;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateModel {


    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.state");

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

    public void createState(State state) throws SQLException {
        String query = "INSERT INTO ESTADO(id_estado, nombre_estado) VALUES(?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, state.getStateId());
        this.preparedStatement.setString(2, state.getStateName());
        processQuery("create");
        finishProcess();
    }

    public State findStateById(String id) throws SQLException {
        String query = "SELECT * FROM ESTADO WHERE id_estado = ?";
        prepareBD(query);
        this.preparedStatement.setString(1,id);
        processQuery("query");
        State state = null;
        if(this.resultSet.next()){
            state = new State(this.resultSet.getString("id_estado"),this.resultSet.getString("nombre_estado"));
        }
        finishProcess();
        return state;
    }

    public List<State> findAllState() throws SQLException {
        String query = "SELECT * FROM ESTADO";
        prepareBD(query);
        processQuery("query");
        List<State> stateList = new ArrayList<>();
        while(this.resultSet.next()){
            State state = new State(this.resultSet.getString("id_estado"),this.resultSet.getString("nombre_estado"));
            stateList.add(state);
        }
        finishProcess();
        return stateList;
    }

    public static void createInfoConstantInBD() throws SQLException {
        CompanyModel companyModel = new CompanyModel();
        Company company = companyModel.findCompanyByNit("123");
        if(company == null){
            StateModel stateModel = new StateModel();
            companyModel.createCompany(new Company("123", "Detallitos YCDG", "123", "detallitosycdg@gamil.com"));
            stateModel.createState(new State("111111111111", "DISPONIBLE"));
            stateModel.createState(new State("222222222222", "CERRADO"));
        }
    }

}
