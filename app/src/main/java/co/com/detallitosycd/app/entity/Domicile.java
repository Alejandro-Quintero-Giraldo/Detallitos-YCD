package co.com.detallitosycd.app.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "domicilio", uniqueConstraints = @UniqueConstraint(columnNames = "id_domicilio"))
public class Domicile {

    @Id
    @Column(name = "id_domicilio", length = 40, nullable = false)
    private String domicileId;

    @Column(name = "direccion_entrega", length = 100, nullable = false)
    private String deliveryAddress;

    @Column(name = "encargado_entrega", length = 12)
    private String deliveryPerson;

    @Column(name = "hora_llegada")
    private LocalDateTime arrivalTime;

    public Domicile(String domicileId, String deliveryAddress, String deliveryPerson, LocalDateTime arrivalTime) {
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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
