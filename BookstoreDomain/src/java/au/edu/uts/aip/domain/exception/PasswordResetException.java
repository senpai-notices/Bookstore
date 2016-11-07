package au.edu.uts.aip.domain.exception;

/**
 * The PasswordResetException is used to handle the password reset exception
 * @author tanman
 */
public class PasswordResetException extends Exception {

    /**
     *the constructor of the class
     */
    public PasswordResetException() {

    }

    /**
     *
     * @param message
     */
    public PasswordResetException(String message) {
        super(message);
    }
}
