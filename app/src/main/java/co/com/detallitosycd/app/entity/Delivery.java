package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "entrega", uniqueConstraints = @UniqueConstraint(columnNames = "id_entrega"))
public class Delivery {

    @Id
    @Column(name = "id_entrega", length = 40, nullable = false)
    private String deliveryId;

    @Column(name =  "tipo_entrega", length = 10, nullable = false)
    private String deliveryType;

    @Column(name = "domicilio_id", length = 40)
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
