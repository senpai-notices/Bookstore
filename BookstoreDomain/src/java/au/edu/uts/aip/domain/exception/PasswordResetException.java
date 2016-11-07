package au.edu.uts.aip.domain.exception;

/**
 *
 * @author tanman
 */
public class PasswordResetException extends Exception {

    /**
     *
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
