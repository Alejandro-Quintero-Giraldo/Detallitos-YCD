package co.com.detallitosycd.app.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura", uniqueConstraints = @UniqueConstraint(columnNames = "id_factura"))
public class Bill {

    @Id
    @Column(name = "id_factura")
    private String billId;

    @Column(name = "fecha_factura")
    private LocalDateTime dateBill;

    @Column(name = "precio_final")
    private Integer finalPrice;

    @Column(name = "usuario_id")
    private String userId;

    @Column(name = "empresa_nit")
    private String companyNIT;

    @Column(name = "entrega_id")
    private String deliverId;

    public Bill(String billId, LocalDateTime dateBill, Integer finalPrice, String userId, String companyNIT, String deliverId) {
        this.billId = billId;
        this.dateBill = dateBill;
        this.finalPrice = finalPrice;
        this.userId = userId;
        this.companyNIT = companyNIT;
        this.deliverId = deliverId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public LocalDateTime getDateBill() {
        return dateBill;
    }

    public void setDateBill(LocalDateTime dateBill) {
        this.dateBill = dateBill;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyNIT() {
        return companyNIT;
    }

    public void setCompanyNIT(String companyNIT) {
        this.companyNIT = companyNIT;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }
}
