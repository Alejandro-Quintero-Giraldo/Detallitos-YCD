package co.com.detallitosycd.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contiene", uniqueConstraints = @UniqueConstraint(columnNames = {"id_factura", "id_producto"}))
public class Contain implements Serializable {

    @Id
    @Column(name = "id_factura")
    private String idBill;

    @Id
    @Column(name = "id_producto")
    private String idProduct;
    private Integer amountPurchased;
    private Integer subTotal;

    public Contain(String idBill, String idProduct, Integer amountPurchased, Integer subTotal) {
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.amountPurchased = amountPurchased;
        this.subTotal = subTotal;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getAmountPurchased() {
        return amountPurchased;
    }

    public void setAmountPurchased(Integer amountPurchased) {
        this.amountPurchased = amountPurchased;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }
}
