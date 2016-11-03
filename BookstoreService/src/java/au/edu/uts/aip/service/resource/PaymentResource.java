package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.validation.ValidationResult;
import au.edu.uts.aip.service.utility.ResourceUtility;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("customer/create")
    public Response createCustomer(PinCustomerPost pinCustomerPost) {
        
        ValidationResult validationResult = paymentBean.createCustomer(pinCustomerPost);

        return ResourceUtility.generate201Response(validationResult);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge")
    public Response charge(PinChargePost pinChargePost) {

        ValidationResult validationResult = paymentBean.charge(pinChargePost);

        return ResourceUtility.generate201Response(validationResult);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/create")
    public Response createRecipient(PinRecipientPost pinRecipientPost) {

        ValidationResult validationResult = paymentBean.createRecipient(pinRecipientPost);

        return ResourceUtility.generate201Response(validationResult);
    }
    
    /**
     * Only for testing, unless there is a need to manually perform
     * transfers from the frontend.
     * @param pinTransferPost
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("transfer")
    public Response transfer(PinTransferPost pinTransferPost) {

        ValidationResult validationResult = paymentBean.transfer(pinTransferPost);

        return ResourceUtility.generate201Response(validationResult);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/{recipient-token}")
    public Response fetchRecipient(@PathParam("recipient-token") String recipientToken) {

        JsonObject recipient = paymentBean.fetchRecipient(recipientToken);

        return Response.ok(recipient, MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/{recipient-token}")
    public Response editRecipient(@PathParam("recipient-token") String recipientToken,
                                    PinRecipientPut pinRecipientPut) {

        ValidationResult validationResult = 
                paymentBean.editRecipient(recipientToken, pinRecipientPut);

        return ResourceUtility.generate200Response(validationResult);
    }
    
    // <editor-fold defaultstate="collapsed" desc="unused">
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
    // </editor-fold>
}
