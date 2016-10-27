package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.service.filter.BasicAuthFilter;
import au.edu.uts.aip.service.dto.PinCardCreate;
import au.edu.uts.aip.service.dto.PinCharge;
import au.edu.uts.aip.service.dto.PinCustomerCreate;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/cards")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCardCreate, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create")
    public Response createCustomer(PinCustomerCreate pinCustomerCreate) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerCreate, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge")
    public Response charge(PinCharge pinCharge) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCharge, MediaType.APPLICATION_JSON_TYPE));               
    
        client.close();
        return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge/{charge_token}")
    public Response fetchChargeDetail(@PathParam("charge_token") String chargeToken) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/charges")
                .path("{charge_token}")
                .resolveTemplate("charge_token", chargeToken)
                .request()
                .get();               
    
        client.close();
        return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/{customer_token}")
    public Response fetchCustomerDetails(@PathParam("customer_token") String customerToken) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .path("{customer_token}")
                .resolveTemplate("customer_token", customerToken)
                .request()
                .get();               
    
        client.close();
        return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/{customer_token}/charges")
    public Response listCharges(@PathParam("customer_token") String customerToken) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .path("{customer_token}/charges")
                .resolveTemplate("customer_token", customerToken)
                .request()
                .get();               
    
        client.close();
        return response;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/{customer_token}/cards")
    public Response listCards(@PathParam("customer_token") String customerToken) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .path("{customer_token}/cards")
                .resolveTemplate("customer_token", customerToken)
                .request()
                .get();               
    
        client.close();
        return response;
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"ADMIN"})
    @Path("customer/{customer_token}")
    public Response removeCustomer(@PathParam("customer_token") String customerToken) {
        
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));
               
        Response response = client.target(BASE_URL + "/customers")
                .path("{customer_token}")
                .resolveTemplate("customer_token", customerToken)
                .request()
                .delete();               
    
        client.close();
        return response;
    }
}
