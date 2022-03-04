package co.com.detallitosycd.app.entity;


import javax.persistence.*;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario","correo_electronico"}))
public class User {

    @Id
    @Column(name = "id_usuario")
    private String userId;

    @Column(name="nombre_usuario")
    private String userName;

    @Column(name = "celular")
    private String cellphone;

    @Column(name = "direccion")
    private String address;

    @Column(name = "correo_electronico")
    private String email;

    @Column(name = "contraseña")
    private String password;

    public User(String userId, String userName, String cellphone, String address, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.cellphone = cellphone;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
