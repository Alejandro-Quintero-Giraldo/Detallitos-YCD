package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "Administrador", uniqueConstraints = @UniqueConstraint(columnNames = {"id_administrador", "correo_electronico"}))
public class Administrator {

    @Id
    @Column(name = "id_administrador", length = 12, nullable = false)
    private String idAdministrator;

    @Column(name = "correo_electronico", length = 50, nullable = false)
    private String email;

    @Column(name = "empresa_nit", length = 11, nullable = false)
    private String companyNIT;

    public Administrator(String idAdministrator, String email, String companyNIT) {
        this.idAdministrator = idAdministrator;
        this.email = email;
        this.companyNIT = companyNIT;
    }

    public Administrator() {
    }

    public String getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(String idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyNIT() {
        return companyNIT;
    }

    public void setCompanyNIT(String companyNIT) {
        this.companyNIT = companyNIT;
    }
}
