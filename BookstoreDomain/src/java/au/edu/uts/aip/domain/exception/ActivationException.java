package au.edu.uts.aip.domain.exception;

/**
 * The ActivatinException class is used to activate the exception
 * @author tanman
 */
public class ActivationException extends Exception {

    /**
     *the constructor of the class
     */
    public ActivationException() {

    }

    /**
     * activate the exception
     * @param message
     */
    public ActivationException(String message) {
        super(message);
    }
}
