package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "estado", uniqueConstraints = @UniqueConstraint(columnNames = "id_estado"))
public class State {

    @Id
    @Column(name = "id_estado", length = 12, nullable = false)
    private String stateId;

    @Column(name =  "nombre_estado", length = 20, nullable = false)
    private String stateName;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public State() {
    }

    public State(String stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }
}
