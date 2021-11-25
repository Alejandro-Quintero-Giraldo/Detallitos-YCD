package co.com.detallitosycd.app.domain.entity;

public class Company {

    private String NIT;
    private String nameCompany;
    private String cellphone;
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
