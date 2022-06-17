package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "catalogo", uniqueConstraints = @UniqueConstraint(columnNames = "id_catalogo"))
public class Catalogue {

    @Id
    @Column(name = "id_catalogo", length = 40, nullable = false)
    private String catalogueId;

    @Column(name = "nombre_catalogo", length = 50, nullable = false)
    private String catalogueName;

    @Column(name = "descripcion", nullable = false)
    private String description;

    public Catalogue(String catalogueId, String catalogueName, String description) {
        this.catalogueId = catalogueId;
        this.catalogueName = catalogueName;
        this.description = description;
    }

    public String getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(String catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
