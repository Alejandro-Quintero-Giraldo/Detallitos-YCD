package co.com.detallitosycd.app.entity;

import java.time.LocalTime;

public class Domicile {

    private String domicileId;
    private String deliveryAddress;
    private String deliveryPerson;
    private LocalTime arrivalTime;

    public Domicile(String domicileId, String deliveryAddress, String deliveryPerson, LocalTime arrivalTime) {
        this.domicileId = domicileId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPerson = deliveryPerson;
        this.arrivalTime = arrivalTime;
    }

    public String getDomicileId() {
        return domicileId;
    }

    public void setDomicileId(String domicileId) {
        this.domicileId = domicileId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
