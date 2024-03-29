package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class is used to reset the password of the user account
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class ResetPasswordDTO implements Serializable {

    /**
     * Password reset token
     */
    private String resetToken;
    /**
     * Username
     */
    private String username;
    /**
     * New password
     */
    private String newPassword;
    /**
     * Confirm new password
     */
    private String confirmNewPassword;

    /**
     * {@link ResetPasswordDTO#resetToken}
     *
     * @return resetToken
     */
    @XmlElement
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    /**
     * {@link ResetPasswordDTO#username}
     *
     * @return username
     */
    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * {@link ResetPasswordDTO#newPassword}
     *
     * @return newPassword
     */
    @XmlElement
    @Size(min = 6, message = "Password must be at least 6 characters long")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * {@link ResetPasswordDTO#confirmNewPassword}
     *
     * @return confirmNewPassword
     */
    @XmlElement
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    /**
     * Check if submitted password and its confirmation is the same. True if matches.
     *
     * @return true if both passwords match.
     */
    @AssertTrue(message = "Password and confirm password do not match")
    public boolean isPasswordMatch() {
        return (this.newPassword.equals(this.confirmNewPassword));
    }
}
