package co.com.detallitosycd.app.entity;

public class Delivery {

    private String deliveryId;
    private String deliveryType;
    private String domicileId;

    public Delivery(String deliveryId, String deliveryType, String domicileId) {
        this.deliveryId = deliveryId;
        this.deliveryType = deliveryType;
        this.domicileId = domicileId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDomicileId() {
        return domicileId;
    }

    public void setDomicileId(String domicileId) {
        this.domicileId = domicileId;
    }
}
