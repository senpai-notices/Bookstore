package au.edu.uts.aip.domain.exception;

/**
 * The TokenGenerationException is used to handle token exception 
 * @author tanman
 */
public class TokenGenerationException extends Exception {

    /**
     * the constructor of the class
     */
    public TokenGenerationException() {

    }

    /**
     * Constructor
     * @param message
     */
    public TokenGenerationException(String message) {
        super(message);
    }
}
