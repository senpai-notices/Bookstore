/**
 * Source: http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access
 */
package au.edu.uts.aip.domain.pin.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;


/**
 * The PinAuthFilter class is used to do the authentication filter
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Priority(value = 1)
public class PinAuthFilter implements ClientRequestFilter {

    /**
     * the user name
     */
    private final String user;
    /**
     * the password of the user
     */
    private final String password;
    private static final String KEY_PREFIX = "Basic ";

    /**
     * The constructor of the class accepting two paramaters
     * @param user
     * @param password 
     */
    public PinAuthFilter(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * the method is used to finish the filter
     * @param requestContext
     * @throws IOException 
     */
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        final String basicAuthentication = getBasicAuthentication();
        headers.add("Authorization", basicAuthentication);
    }

    /**
     * the method is used to get the authentication information
     * @return 
     */
    private String getBasicAuthentication() {
        String token = this.user + ":" + this.password;
        try {
            return KEY_PREFIX + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Cannot encode with UTF-8", ex);
        }
    }
}
