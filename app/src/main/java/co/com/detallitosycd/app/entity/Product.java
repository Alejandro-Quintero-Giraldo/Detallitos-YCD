package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "producto", uniqueConstraints = @UniqueConstraint(columnNames = {"id_producto", "nombre_producto" }))
public class Product {

    @Id
    @Column(name = "id_producto", length = 40, nullable = false)
    private String productId;

    @Column(name = "nombre_producto", length = 50, nullable = false)
    private String productName;

    @Column(name = "tipo_producto", length = 10, nullable = false)
    private String idProductType;

    @Column(name = "precio_producto", nullable = false)
    private Integer productPrice;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "imagen",length = 100, nullable = false)
    private String image;

    @Column(name = "esta_visible", length = 2, nullable = false)
    private String isVisible;

    public Product(String productId, String productName, String productType, Integer productPrice, String description, String isVisible, String image) {
        this.productId = productId;
        this.productName = productName;
        this.idProductType = productType;
        this.productPrice = productPrice;
        this.description = description;
        this.isVisible = isVisible;
        this.image = image;
    }

    public Product(String productId, String productName, String productType, Integer productPrice, String description, String isVisible) {
        this.productId = productId;
        this.productName = productName;
        this.idProductType = productType;
        this.productPrice = productPrice;
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

    public String getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(String idProductType) {
        this.idProductType = idProductType;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
