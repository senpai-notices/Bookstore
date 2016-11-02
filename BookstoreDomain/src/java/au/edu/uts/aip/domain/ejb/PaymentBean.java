package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.pin.dto.PinCardCreate;
import au.edu.uts.aip.domain.pin.dto.PinCharge;
import au.edu.uts.aip.domain.pin.dto.PinCustomerCreate;
import au.edu.uts.aip.domain.pin.filter.BasicAuthFilter;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class PaymentBean implements PaymentRemote {
    
    private static final String BASE_URL = "https://test-api.pin.net.au/1";
    private static final String API_KEY_SECRET = "***REMOVED***";
    private static final String PASSWORD = "";
        
    @Override
    public Response createCard(PinCardCreate pinCardCreate) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/cards")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCardCreate, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }

    @Override
    public Response createCustomer(PinCustomerCreate pinCustomerCreate) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerCreate, MediaType.APPLICATION_JSON_TYPE));               
        
        client.close();
        return response;
    }
    
    @Override
    public Response charge(PinCharge pinCharge) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCharge, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
}
