package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "empresa", uniqueConstraints = @UniqueConstraint(columnNames = "nit"))
public class Company {

    @Id
    @Column(name = "nit", length = 11, nullable = false)
    private String NIT;

    @Column(name  = "nombre_empresa", length = 20, nullable = false)
    private String nameCompany;

    @Column(name = "celular", length = 12, nullable = false)
    private String cellphone;

    @Column(name = "correo_electronico", length = 50, nullable = false)
    private String email;

    public Company(String NIT, String nameCompany, String cellphone, String email) {
        this.NIT = NIT;
        this.nameCompany = nameCompany;
        this.cellphone = cellphone;
        this.email = email;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
