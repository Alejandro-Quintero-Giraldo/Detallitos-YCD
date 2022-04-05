package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductModel extends Conection {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.product");

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
        } else if(action.equals("create") || action.equals("update")){
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

    public void createProduct(Product productInfo) throws SQLException {
        String query = "INSERT INTO PRODUCTO(id_producto, nombre_producto,tipo_producto,precio_producto," +
                "descripcion, esta_visible, imagen ) VALUES (?,?,?,?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, productInfo.getProductId());
        this.preparedStatement.setString(2, productInfo.getProductName());
        this.preparedStatement.setString(3, productInfo.getIdProductType());
        this.preparedStatement.setInt(4, productInfo.getProductPrice());
        this.preparedStatement.setString(5, productInfo.getDescription());
        this.preparedStatement.setString(6, productInfo.getIsVisible());
        this.preparedStatement.setString(7, productInfo.getImage());
        processQuery("create");
        finishProcess();
    }

    public Product findById(String id) throws SQLException {
        String query = "SELECT * FROM PRODUCTO WHERE id_producto = ?";
        prepareBD(query);
        this.preparedStatement.setString(1,id);
        processQuery("query");
        Product product = null;
        if(this.resultSet.next()){
            product = new Product(this.resultSet.getString("id_producto"),
                    this.resultSet.getString("nombre_producto"),this.resultSet.getString("tipo_producto"),
                    this.resultSet.getInt("precio_producto"),
                    this.resultSet.getString("descripcion"), this.resultSet.getString("esta_visible"),
                    this.resultSet.getString("imagen"));
        }
        finishProcess();
        return product;
    }

    public void updateProduct(Product productUpdate) throws SQLException {
        String query = "UPDATE PRODUCTO SET nombre_producto = ?, tipo_producto = ?, " +
                "descripcion = ?, esta_visible = ?, imagen = ? WHERE id_producto = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, productUpdate.getProductName());
        this.preparedStatement.setString(2, productUpdate.getIdProductType());
        this.preparedStatement.setInt(3,productUpdate.getProductPrice());
        this.preparedStatement.setString(4, productUpdate.getDescription());
        this.preparedStatement.setString(5, productUpdate.getIsVisible());
        this.preparedStatement.setString(6, productUpdate.getImage());
        this.preparedStatement.setString(7, productUpdate.getProductId());
        processQuery("update");
        finishProcess();
    }

    public List<Product> findProductsVisibles() throws SQLException {
        String query = "SELECT * FROM PRODUCTO WHERE esta_visible = ?";
        List<Product> productList = new ArrayList<>();
        prepareBD(query);
        this.preparedStatement.setString(1, "SI");
        processQuery("query");
        while(this.resultSet.next()){
            Product product = new Product(this.resultSet.getString("id_producto"),
                    this.resultSet.getString("nombre_producto"),this.resultSet.getString("tipo_producto"),
                    this.resultSet.getInt("precio_producto"),
                    this.resultSet.getString("descripcion"),this.resultSet.getString("esta_visible"),
                    this.resultSet.getString("imagen"));
            productList.add(product);
        }
        finishProcess();
        return productList;
    }

    public List<Product> findAllProducts() throws  SQLException {
        String query = "SELECT * FROM PRODUCTO";
        List<Product> productList = new ArrayList<>();
        prepareBD(query);
        processQuery("query");
        while(this.resultSet.next()){
            Product product = new Product(this.resultSet.getString("id_producto"),
                    this.resultSet.getString("nombre_producto"),this.resultSet.getString("tipo_producto"),
                    this.resultSet.getInt("precio_producto"),
                    this.resultSet.getString("descripcion"),this.resultSet.getString("esta_visible"),
                    this.resultSet.getString("imagen"));
            productList.add(product);
        }
        finishProcess();
        return productList;
    }
}