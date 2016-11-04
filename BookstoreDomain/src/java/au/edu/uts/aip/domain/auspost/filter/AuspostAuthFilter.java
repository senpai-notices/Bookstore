package au.edu.uts.aip.domain.auspost.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

@Priority(value = 1)
public class AuspostAuthFilter implements ClientRequestFilter {

    private String apiKey;
    private static final String KEY_APIKEY = "AUTH-KEY";

    public AuspostAuthFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        headers.add(KEY_APIKEY, apiKey);
    }
}
