
package co.com.detallitosycd.app.dto;

public class UserDTO {
    
    
    private String userId;
    private String userName;
    private String cellphone;
    private String address;
    private String email;
    private String password;

    public UserDTO(String userId, String userName, String cellphone, String address, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.cellphone = cellphone;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
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
