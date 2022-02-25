package co.com.detallitosycd.app.entity;

public class Product {

    private String productId;
    private String productName;
    private String productType;
    private Integer productPrice;
    private Integer amountStock;
    private String description;

    public Product(String productId, String productName, String productType, Integer productPrice, Integer amountStock, String description) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.amountStock = amountStock;
        this.description = description;
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
