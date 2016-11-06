package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrationDTO implements Serializable{
    private String username;
    private String fullname;
    private String email;
    private String password;
    private String confirmPassword;
    
    @Size(min = 6, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_]$", message = "Username contains invalid character. "
            + "Available characters are English alphaet (a-z, A-Z), "
            + "number (0-9) and underscore(_)")
    @XmlElement(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Size(max = 255)
    @Pattern(regexp = "^([a-zA-Z]+)( [a-zA-Z]+)+$", 
            message = "Fullname can only contain English alphabet chracters, and must contain at least 2 words")
    @XmlElement
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    @Pattern(regexp = "^[^ ]+@[^ ]+\\.[^ ]+$", message = "Email address is not valid")
    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @XmlElement
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    @AssertTrue(message = "Password and confirm password do not match")
    public boolean isPasswordMatch(){
        return this.password.equals(this.confirmPassword);
    }
}
