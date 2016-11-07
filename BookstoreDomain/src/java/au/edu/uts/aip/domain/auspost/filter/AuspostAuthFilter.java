package au.edu.uts.aip.domain.auspost.filter;

/**
 * the needed libraries
 */
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Add authorisation header to Auspost API requests.
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Priority(value = 1)
public class AuspostAuthFilter implements ClientRequestFilter {

    /**
     * the api attributes info
     */
    private String apiKey;
    private static final String KEY_APIKEY = "AUTH-KEY";

    /**
     * The constructor of the AuspostAuthFilter class
     * @param apiKey
     */
    public AuspostAuthFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        headers.add(KEY_APIKEY, apiKey);
    }
}
