package co.com.detallitosycd.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "producto_catalogo", uniqueConstraints = @UniqueConstraint(columnNames = {}))
public class ProductCatalogue implements Serializable {

    @Id
    @Column(name = "producto_id", length = 40, nullable = false)
    private String idProduct;

    @Id
    @Column(name = "catalogo_id", length = 12, nullable = false)
    private String idCatalogue;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCatalogue() {
        return idCatalogue;
    }

    public void setIdCatalogue(String idCatalogue) {
        this.idCatalogue = idCatalogue;
    }

    public ProductCatalogue(String idProduct, String idCatalogue) {
        this.idProduct = idProduct;
        this.idCatalogue = idCatalogue;
    }
}
