package co.com.detallitosycd.app.model;

import co.com.detallitosycd.app.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.SQLException;

@SpringBootTest
class ProductModelTest {

    private ProductModel productModel;

    @BeforeEach
    void setUp(){
        productModel = new ProductModel();
    }

    @Test
    void shouldCreateAProduct() throws SQLException {
        Product productMock = new Product("1234567890", "randomName","1",35000,
                "randomDescription","SI","randomImage.extension");
        productModel.createProduct(productMock);
        Product productFounded =  productModel.findById(productMock.getProductId());
        Assertions.assertEquals(productMock.getProductId(), productFounded.getProductId());
        productModel.deleteProductById(productFounded.getProductId());
    }

}