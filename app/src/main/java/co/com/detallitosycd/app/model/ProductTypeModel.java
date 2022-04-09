package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.ProductType;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductTypeModel  {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.productType");

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

    public void createTypeProduct(ProductType productType) throws SQLException {
        String query = "INSERT INTO TIPO_PRODUCTO(id_tipo, nombre_tipo, esta_en_venta) VALUES(?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, UUID.randomUUID().toString());
        this.preparedStatement.setString(2, productType.getTypeName());
        this.preparedStatement.setString(3, productType.getIsForSale());
        processQuery("create");
        finishProcess();
    }

    public void updateTypeProduct(ProductType productType) throws SQLException {
        String query = "UPDATE TIPO_PRODUCTO SET nombre_tipo = ?, esta_en_venta = ? WHERE id_tipo = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, productType.getTypeName());
        this.preparedStatement.setString(2, productType.getIsForSale());
        this.preparedStatement.setString(3, productType.getIdProductType());
        processQuery("update");
        finishProcess();
    }

    public List<ProductType> findAllProductType() throws SQLException{
        String query = "SELECT * FROM TIPO_PRODUCTO";
        List<ProductType> productTypeList = new ArrayList<>();
        prepareBD(query);
        processQuery("query");
        while(this.resultSet.next()){
            ProductType productType = new ProductType(this.resultSet.getString("id_tipo"),
                    this.resultSet.getString("nombre_tipo"), this.resultSet.getString("esta_en_venta"));
            productTypeList.add(productType);
        }
        finishProcess();
        return productTypeList;
    }

    public List<ProductType> findProductTypeByForSale(String isForSale) throws SQLException {
        String query = "SELECT * FROM TIPO_PRODUCTO WHERE esta_en_venta = ?";
        List<ProductType> productTypeList = new ArrayList<>();
        prepareBD(query);
        this.preparedStatement.setString(1, isForSale);
        processQuery("query");
        while(this.resultSet.next()){
            ProductType productType = new ProductType(this.resultSet.getString("id_tipo"),
                    this.resultSet.getString("nombre_tipo"), this.resultSet.getString("esta_en_venta"));
            productTypeList.add(productType);
        }
        finishProcess();
        return  productTypeList;
    }
 }
