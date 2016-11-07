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
     * Constructor
     * @param message
     */
    public ActivationException(String message) {
        super(message);
    }
}
