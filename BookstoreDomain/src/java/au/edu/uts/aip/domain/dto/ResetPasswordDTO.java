package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResetPasswordDTO implements Serializable {
    private String resetToken;
    private String username;
    private String newPassword;
    private String confirmNewPassword;

    @XmlElement
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    @Size(min = 6, message = "Password must be at least 6 characters long")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @XmlElement
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
    
    @AssertTrue(message = "Password and confirm password do not match")
    public boolean isPasswordMatch(){
        return (this.newPassword.equals(this.confirmNewPassword));
    }
}
