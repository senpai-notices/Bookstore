package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The Class is used to register new accounts
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
public class RegistrationDTO implements Serializable {

    /**
     * the username of the new account
     */
    private String username;
    /**
     * the fullname of the user
     */
    private String fullname;
    /**
     * the email of the account
     */
    private String email;
    /**
     * the password of the account
     */
    private String password;
    /**
     * the password confirmation
     */
    private String confirmPassword;

    /**
     * {@link RegistrationDTO#username}
     *
     * @return
     */
    @Size(min = 6, max = 32)
    @Pattern(regexp = "^([a-zA-Z0-9_])+$", message = "Username contains invalid character. "
            + "Available characters are English alphaet (a-z, A-Z), "
            + "number (0-9) and underscore(_)")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * {@link RegistrationDTO#fullname}
     *
     * @return
     */
    @Size(max = 255)
    @Pattern(regexp = "^([a-zA-Z]+)( [a-zA-Z]+)+$",
            message = "Fullname can only contain English alphabet chracters, and must contain at least 2 words")
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

        /**
     * {@link RegistrationDTO#email}
     *
     * @return
     */
    @Pattern(regexp = "^[^ ]+@[^ ]+\\.[^ ]+$", message = "Email address is not valid")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

        /**
     * {@link RegistrationDTO#password}
     *
     * @return
     */
    @Size(min = 6, message = "Password must be at least 6 characters long")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * {@link RegistrationDTO#confirmPassword}
     *
     * @return
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    /**
     * Check if submitted password and its confirmation is the same.
     * True if matches.
     * @return
     */
    @AssertTrue(message = "Password and confirm password do not match")
    public boolean isPasswordMatch() {
        return this.password.equals(this.confirmPassword);
    }
}
