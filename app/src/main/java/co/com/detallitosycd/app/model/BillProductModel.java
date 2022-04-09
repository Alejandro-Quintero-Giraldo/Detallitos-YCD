package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.BillProduct;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.conection.Conection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillProductModel {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.model.billproduct");

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ProductModel productModel;


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

    public void createBillProduct(BillProduct billProduct) throws SQLException {
        Product product = productModel.findById(billProduct.getIdProduct());
        if(product == null){
            throw new IllegalArgumentException("El producto ingresado no existe en la base de datos");
        }
        String query = "INSERT INTO FACTURA_PRODUCTO(id_factura, id_producto,cantidad_comprada,subtotal," +
                "especificaciones) VALUES(?,?,?,?,?)";
        prepareBD(query);
        this.preparedStatement.setString(1, billProduct.getIdBill());
        this.preparedStatement.setString(2, billProduct.getIdProduct());
        this.preparedStatement.setInt(3, billProduct.getAmountPurchased());
        this.preparedStatement.setInt(4, billProduct.getAmountPurchased() * product.getProductPrice());
        this.preparedStatement.setString(5, billProduct.getEspecifications());
        processQuery("create");
        finishProcess();
    }

    public List<BillProduct> findBillProductsByBillId(String billId) throws SQLException {
        List<BillProduct> billProducts = new ArrayList<>();
        String query = "SELECT * FROM FACTURA_PRODUCTO WHERE id_factura = ?";
        prepareBD(query);
        this.preparedStatement.setString(1,billId);
        processQuery("query");
        while (this.resultSet.next()){
            BillProduct billProduct = new BillProduct(this.resultSet.getString("id_factura"),
                    this.resultSet.getString("id_producto"), this.resultSet.getInt("cantidad_comprada"),
                    this.resultSet.getInt("subtotal"), this.resultSet.getString("especificaciones"));
            billProducts.add(billProduct);
        }
        finishProcess();
        return billProducts;
    }

    public void updateBillProduct(BillProduct billProduct) throws SQLException {
        Product product = productModel.findById(billProduct.getIdProduct());
        if(product == null){
            throw new IllegalArgumentException("El producto ingresado no existe en la base de datos");
        }
        String query = "UPDATE FACTURA_PRODUCTO SET  cantidad_comprada = ?," +
                "subtotal = ?, especificaciones = ? WHERE id_factura = ? AND id_producto = ?";
        prepareBD(query);
        this.preparedStatement.setInt(1, billProduct.getAmountPurchased());
        this.preparedStatement.setInt(2, billProduct.getAmountPurchased() * product.getProductPrice());
        this.preparedStatement.setString(3, billProduct.getEspecifications());
        this.preparedStatement.setString(4, billProduct.getIdBill());
        this.preparedStatement.setString(5, billProduct.getIdProduct());
        processQuery("update");
        finishProcess();
    }

    public void deleteBillProduct(String idBill, String idProduct) throws SQLException {
        String query = "DELETE FROM FACTURA_PRODUCTO WHERE id_factura = ? AND id_producto = ?";
        prepareBD(query);
        this.preparedStatement.setString(1, idBill);
        this.preparedStatement.setString(2, idProduct);
        processQuery("delete");
        finishProcess();
    }
}
