package au.edu.uts.aip.resource;

import au.edu.uts.aip.filter.BasicAuthFilter;
import au.edu.uts.aip.dto.PinCardCreate;
import au.edu.uts.aip.dto.PinCharge;
import au.edu.uts.aip.dto.PinCustomerCreate;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pin")
public class PinResource {
    
    private static final String BASE_URL = "https://test-api.pin.net.au/1";
    private static final String API_KEY_SECRET = "***REMOVED***";
    private static final String PASSWORD = "";
        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("card/create")
    public Response createCard(PinCardCreate pinCardCreate) {
        
        Client client = ClientBuilder.newClient().register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/cards")
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(pinCardCreate, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create")
    public Response createCustomer(PinCustomerCreate pinCustomerCreate) {
        
        Client client = ClientBuilder.newClient().register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(pinCustomerCreate, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge")
    public Response charge(PinCharge pinCharge) {
        
        Client client = ClientBuilder.newClient().register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON).post(Entity.entity(pinCharge, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
}
