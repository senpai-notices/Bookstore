package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.validation.ValidationResult;
import au.edu.uts.aip.service.util.ResourceUtil;
import javax.annotation.security.RolesAllowed;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("payment")
public class PaymentResource {

    @EJB
    private PaymentRemote paymentBean;

    @EJB
    private UserRemote userBean;
    
    @Context
    private SecurityContext securityContext;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create2")
    public Response createCustomer2(PinCustomerPost pinCustomerPost) {

        SerialResponse response = paymentBean.createCustomer2(pinCustomerPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge2")
    public Response charge2(PinChargePost pinChargePost) {

        SerialResponse response = paymentBean.charge2(pinChargePost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "VERIFIED USER", "ADMIN"})
    @Path("recipient/create2")
    public Response createRecipient2(PinRecipientPost pinRecipientPost) {

        String username = securityContext.getUserPrincipal().getName();
        UserDTO userDTO = userBean.getUser(username);
        pinRecipientPost.setEmail(userDTO.getEmail());

        SerialResponse response = paymentBean.createRecipient2(pinRecipientPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
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
    @Path("recipient2/{recipient-token}")
    public Response editRecipient2(@PathParam("recipient-token") String recipientToken,
            PinRecipientPut pinRecipientPut) {

        SerialResponse response
                = paymentBean.editRecipient2(recipientToken, pinRecipientPut);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("transfer2")
    public Response transfer2(PinTransferPost pinTransferPost) {

        SerialResponse response = paymentBean.transfer2(pinTransferPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create")
    public Response createCustomer(PinCustomerPost pinCustomerPost) {

        ValidationResult validationResult = paymentBean.createCustomer(pinCustomerPost);

        return ResourceUtil.generate201Response(validationResult);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge")
    public Response charge(PinChargePost pinChargePost) {

        ValidationResult validationResult = paymentBean.charge(pinChargePost);

        return ResourceUtil.generate201Response(validationResult);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/create")
    public Response createRecipient(PinRecipientPost pinRecipientPost) {

        ValidationResult validationResult = paymentBean.createRecipient(pinRecipientPost);

        return ResourceUtil.generate201Response(validationResult);
    }

    /**
     * Only for testing, unless there is a need to manually perform transfers from the frontend.
     *
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

        return ResourceUtil.generate201Response(validationResult);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/{recipient-token}")
    public Response editRecipient(@PathParam("recipient-token") String recipientToken,
            PinRecipientPut pinRecipientPut) {

        ValidationResult validationResult
                = paymentBean.editRecipient(recipientToken, pinRecipientPut);

        return ResourceUtil.generate200Response(validationResult);
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
