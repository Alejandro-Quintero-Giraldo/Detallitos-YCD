package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "producto", uniqueConstraints = @UniqueConstraint(columnNames = {"id_producto", "nombre_producto" }))
public class Product {

    @Id
    @Column(name = "id_producto")
    private String productId;

    @Column(name = "nombre_producto")
    private String productName;

    @Column(name = "tipo_producto")
    private String productType;

    @Column(name = "precio_producto")
    private Integer productPrice;

    @Column(name = "cantidad_existencias")
    private Integer amountStock;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "imagen")
    private String image;

    @Column(name = "esta_visible")
    private String isVisible;

    public Product(String productId, String productName, String productType, Integer productPrice, Integer amountStock, String description, String isVisible, String image) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.amountStock = amountStock;
        this.description = description;
        this.isVisible = isVisible;
        this.image = image;
    }

    public Product(String productId, String productName, String productType, Integer productPrice, Integer amountStock, String description, String isVisible) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.amountStock = amountStock;
        this.description = description;
        this.isVisible = isVisible;
    }

    public Product() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getAmountStock() {
        return amountStock;
    }

    public void setAmountStock(Integer amountStock) {
        this.amountStock = amountStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
