package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "tipo_producto", uniqueConstraints = @UniqueConstraint(columnNames = "id_tipo"))
public class ProductType {

    @Id
    @Column(name = "id_tipo", length = 12, nullable = false)
    private String idProductType;

    @Column(name = "nombre_tipo", length = 15, nullable = false)
    private String typeName;

    public String getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(String idProductType) {
        this.idProductType = idProductType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ProductType(String idProductType, String typeName) {
        this.idProductType = idProductType;
        this.typeName = typeName;
    }
}
