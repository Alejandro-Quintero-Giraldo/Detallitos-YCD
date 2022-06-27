package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.ProductCatalogue;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductCatalogueModel {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.catalogue");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ProductModel productModel;
    private CatalogueModel catalogueModel;

    private void prepareBD(String query) throws SQLException {
        this.connection = Conection.conect();
        this.preparedStatement = connection.prepareStatement(query);
    }

    private void processQuery(String action) throws SQLException {
        if(action.equals("query")){
            this.resultSet = this.preparedStatement.executeQuery();
        } else if(action.equals("create") || action.equals("update") || action.equals("delete")){
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

    public boolean createProductCatalogue(ProductCatalogue productCatalogue) throws SQLException {
        Product product = productModel.findById(productCatalogue.getIdProduct());
        Catalogue catalogue = catalogueModel.findCatalogueById(productCatalogue.getIdCatalogue());
        if(product == null || catalogue == null){
            return false;
        }
        String query = "INSERT INTO PRODUCTO_CATALOGO(producto_id, catalogo_id) VALUES (?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, productCatalogue.getIdProduct());
        this.preparedStatement.setString(2, productCatalogue.getIdCatalogue());
        processQuery("create");
        finishProcess();
        return true;
    }

    public List<ProductCatalogue> findProductCatalogueByCatalogueId(String id) throws SQLException {
        String query = "SELECT * FROM PRODUCTO_CATALOGO WHERE catalogo_id = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, id);
        List<ProductCatalogue> productCatalogueList = new ArrayList<>();
        processQuery("query");
        while(this.resultSet.next()){
            ProductCatalogue productCatalogue = new ProductCatalogue(this.resultSet.getString("producto_id"),
                    this.resultSet.getString("catalogo_id"));
            productCatalogueList.add(productCatalogue);
        }
        finishProcess();
        return productCatalogueList;
    }

    public List<ProductCatalogue> findProductCatalogueByProductId(String id) throws SQLException {
        String query = "SELECT * FROM PRODUCTO_CATALOGO WHERE producto_id = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, id);
        List<ProductCatalogue> productCatalogueList = new ArrayList<>();
        processQuery("query");
        while(this.resultSet.next()){
            ProductCatalogue productCatalogue = new ProductCatalogue(this.resultSet.getString("producto_id"),
                    this.resultSet.getString("catalogo_id"));
            productCatalogueList.add(productCatalogue);
        }
        finishProcess();
        return productCatalogueList;
    }

    public boolean deleteProductCatalogue(ProductCatalogue productCatalogue) throws SQLException {
        productModel = new ProductModel();
        catalogueModel = new CatalogueModel();
        Product product = productModel.findById(productCatalogue.getIdProduct());
        Catalogue catalogue = catalogueModel.findCatalogueById(productCatalogue.getIdCatalogue());
        if(product == null || catalogue == null){
            return false;
        }
        String query = "DELETE FROM PRODUCTO_CATALOGO WHERE producto_id = ? AND catalogo_id = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, productCatalogue.getIdProduct());
        this.preparedStatement.setString(2, productCatalogue.getIdCatalogue());
        processQuery("delete");
        finishProcess();
        return true;
    }

}
