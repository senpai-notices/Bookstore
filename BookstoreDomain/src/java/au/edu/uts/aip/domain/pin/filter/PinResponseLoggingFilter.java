/**
 * Source: https://nacho4d-nacho4d.blogspot.com.au/2016/04/registering-client-filters-for-jax-rs.html
 */
package au.edu.uts.aip.domain.pin.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Response;

@Priority(value = 2)
public class PinResponseLoggingFilter implements ClientResponseFilter {
    private static final Logger LOG = Logger.getLogger(PinResponseLoggingFilter.class.getName());
            
    @Override
    public void filter(ClientRequestContext requestContext, 
            ClientResponseContext responseContext) throws IOException {
        
        LOG.log(Level.INFO, "<< {0} {1}", new Object[]{requestContext.getMethod(), requestContext.getUri()});
        LOG.log(Level.INFO, "<< (status) {0}", responseContext.getStatus());
        LOG.log(Level.INFO, "<< (headers) {0}", responseContext.getHeaders().toString());

        InputStream entityStream = responseContext.getEntityStream();
        if (entityStream != null) {
            LOG.log(Level.FINER, "<< (body){0}", entityStream.toString());
        }
        
        if (responseContext.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("From the filter... successful");
        } else {
            System.out.println("From the filter... not successful");
        }
    }
    
}
