package co.com.detallitosycd.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "Administrador", uniqueConstraints = @UniqueConstraint(columnNames = {"id_administrador", "correo_electronico"}))
public class Administrator {

    @Id
    @Column(name = "id_administrador")
    private String idAdministrator;

    @Column(name = "nombre_administrador")
    private String nameAdministrator;

    @Column(name = "celular")
    private String cellphone;

    @Column(name = "direccion")
    private String address;

    @Column(name = "correo_electronico")
    private String email;

    @Column(name = "empresa_nit")
    private String companyNIT;

    public Administrator(String idAdministrator, String nameAdministrator, String cellphone, String address, String email, String companyNIT) {
        this.idAdministrator = idAdministrator;
        this.nameAdministrator = nameAdministrator;
        this.cellphone = cellphone;
        this.address = address;
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

    public String getNameAdministrator() {
        return nameAdministrator;
    }

    public void setNameAdministrator(String nameAdministrator) {
        this.nameAdministrator = nameAdministrator;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
