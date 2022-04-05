package co.com.detallitosycd.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "factura_producto", uniqueConstraints = @UniqueConstraint(columnNames = {"id_factura", "id_producto"}))
public class BillProduct implements Serializable {

    @Id
    @Column(name = "id_factura", length = 15, nullable = false)
    private String idBill;

    @Id
    @Column(name = "id_producto", length = 12, nullable = false)
    private String idProduct;

    @Column(name = "cantidad_comprada", nullable = false)
    private Integer amountPurchased;

    @Column(name = "subtotal", nullable = false)
    private Integer subTotal;

    @Column(name = "especificaciones")
    private  String especifications;

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

    public String getEspecifications() {
        return especifications;
    }

    public void setEspecifications(String especifications) {
        this.especifications = especifications;
    }

    public BillProduct(String idBill, String idProduct, Integer amountPurchased, Integer subTotal, String especifications) {
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.amountPurchased = amountPurchased;
        this.subTotal = subTotal;
        this.especifications = especifications;
    }
}
