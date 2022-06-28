package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.entity.ProductCatalogue;
import co.com.detallitosycd.app.model.conection.Conection;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogueModel {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.catalogue");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ProductCatalogueModel productCatalogueModel;

    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery(String action) throws SQLException {
        if(action.equals("query")){
            this.resultSet = this.preparedStatement.executeQuery();
        } else if(action.equals("create") || action.equals("update") || action.equals("delete")){
            this.preparedStatement.execute();
        } else if(action.equals("products")) {
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

    public void createCatalogue(Catalogue catalogue) throws SQLException {
        String query = "INSERT INTO CATALOGO(id_catalogo, nombre_catalogo, descripcion) VALUES (?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, UUID.randomUUID().toString());
        this.preparedStatement.setString(2, catalogue.getCatalogueName());
        this.preparedStatement.setString(3, catalogue.getDescription());
        processQuery("create");
        finishProcess();
    }

    public Catalogue findCatalogueById(String id) throws SQLException {
        String query = "SELECT * FROM CATALOGO WHERE id_catalogo = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, id);
        processQuery("query");
        Catalogue catalogue = null;
        if(this.resultSet.next()){
            catalogue = new Catalogue(this.resultSet.getString("id_catalogo"),
                    this.resultSet.getString("nombre_catalogo"), this.resultSet.getString("descripcion"));
        }
        finishProcess();
        return catalogue;
    }

    public List<Catalogue> findAllCatalogues() throws SQLException {
        String query = "SELECT * FROM CATALOGO";
        prepareBD(query);
        processQuery("query");
        List<Catalogue> catalogueList = new ArrayList<>();
        while(this.resultSet.next()){
            Catalogue catalogue = new Catalogue(this.resultSet.getString("id_catalogo"),
                    this.resultSet.getString("nombre_catalogo"), this.resultSet.getString("descripcion"));
            catalogueList.add(catalogue);
        }
        finishProcess();
        catalogueList.stream().sorted();
        return catalogueList;
    }

    public List<Catalogue> findAllCataloguesVisibles() throws SQLException {
        productCatalogueModel = new ProductCatalogueModel();
        String query = "SELECT * FROM CATALOGO";
        prepareBD(query);
        processQuery("query");
        List<Catalogue> catalogueList = new ArrayList<>();
        while(this.resultSet.next()){
            Catalogue catalogue = new Catalogue(this.resultSet.getString("id_catalogo"),
                    this.resultSet.getString("nombre_catalogo"), this.resultSet.getString("descripcion"));
           // if(productCatalogueModel.findProductCatalogueByCatalogueId(catalogue.getCatalogueId()).size() > 0)
                catalogueList.add(catalogue);
        }
        finishProcess();
        catalogueList.stream().sorted();
        return catalogueList;
    }

    public void updateCatalogue(Catalogue catalogue) throws SQLException {
        String query = "UPDATE CATALOGO SET nombre_catalogo = ?, descripcion = ? WHERE id_catalogo = ?";
        prepareBD(query);
        this.preparedStatement.setString(1,catalogue.getCatalogueName());
        this.preparedStatement.setString(2, catalogue.getDescription());
        this.preparedStatement.setString(3, catalogue.getCatalogueId());
        processQuery("update");
        finishProcess();
    }

    public void deleteCatalogue(String id) throws SQLException {
        String query = "DELETE FROM CATALOGO WHERE id_catalogo = ?";
        prepareBD(query);
        this.preparedStatement.setString(1,id);
        processQuery("delete");
        finishProcess();
    }
}
