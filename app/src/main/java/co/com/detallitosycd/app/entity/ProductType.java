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

    @Column(name =  "esta_en_venta", length = 2, nullable = false)
    private String isForSale;

    public ProductType(String idProductType, String typeName, String isForSale) {
        this.idProductType = idProductType;
        this.typeName = typeName;
        this.isForSale = isForSale;
    }

    public String getIdProductType() {
        return idProductType;
    }

    public ProductType(String typeName, String isForSale) {
        this.typeName = typeName;
        this.isForSale = isForSale;
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

    public String getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(String isForSale) {
        this.isForSale = isForSale;
    }
}
