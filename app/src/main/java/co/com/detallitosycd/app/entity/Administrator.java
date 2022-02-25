package co.com.detallitosycd.app.entity;

public class Administrator {

    private String idAdministrator;
    private String nameAdministrator;
    private String cellphone;
    private String address;
    private String email;
    private String companyNIT;

    public Administrator(String idAdministrator, String nameAdministrator, String cellphone, String address, String email, String companyNIT) {
        this.idAdministrator = idAdministrator;
        this.nameAdministrator = nameAdministrator;
        this.cellphone = cellphone;
        this.address = address;
        this.email = email;
        this.companyNIT = companyNIT;
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
