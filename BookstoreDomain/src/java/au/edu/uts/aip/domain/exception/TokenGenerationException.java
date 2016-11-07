package au.edu.uts.aip.domain.exception;

/**
 *
 * @author tanman
 */
public class TokenGenerationException extends Exception {

    /**
     *
     */
    public TokenGenerationException() {

    }

    /**
     *
     * @param message
     */
    public TokenGenerationException(String message) {
        super(message);
    }
}
