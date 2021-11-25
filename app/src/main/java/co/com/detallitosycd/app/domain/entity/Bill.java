package co.com.detallitosycd.app.domain.entity;

import java.time.LocalDateTime;

public class Bill {

    private String billId;
    private LocalDateTime dateBill;
    private Integer finalPrice;
    private String userId;
    private String companyNIT;
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
