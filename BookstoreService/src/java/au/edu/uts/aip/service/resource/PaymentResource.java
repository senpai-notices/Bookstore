package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.pin.dto.PinCardCreate;
import au.edu.uts.aip.domain.pin.dto.PinCharge;
import au.edu.uts.aip.domain.pin.dto.PinCustomerCreate;
import au.edu.uts.aip.domain.remote.PaymentRemote;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payment")
public class PaymentResource {

    @EJB
    private PaymentRemote paymentBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("card/create")
    public Response createCard(PinCardCreate pinCardCreate) {

        Response response = paymentBean.createCard(pinCardCreate);

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create")
    public Response createCustomer(PinCustomerCreate pinCustomerCreate) {

        Response response = paymentBean.createCustomer(pinCustomerCreate);
        // TODO: convert response to String
        // TODO: check response status code.
        // If != 201, then check if error array exists. If so, add it to the validation message stack.
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge")
    public Response charge(PinCharge pinCharge) {

        Response response = paymentBean.charge(pinCharge);

        return response;
    }

    /*
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
     */
}
