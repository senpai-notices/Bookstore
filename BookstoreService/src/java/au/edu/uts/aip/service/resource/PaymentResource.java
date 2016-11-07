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

/**
 * REST endpoint for payments
 * @author x
 */
@Path("payment")
public class PaymentResource {

    @EJB
    private PaymentRemote paymentBean;

    @EJB
    private UserRemote userBean;
    
    @Context
    private SecurityContext securityContext;
    
    /**
     *
     * @param pinCustomerPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create2")
    public Response createCustomer2(PinCustomerPost pinCustomerPost) {

        SerialResponse response = paymentBean.createCustomer(pinCustomerPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(500).build();
        }
    }

    /**
     *
     * @param pinChargePost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge2")
    public Response charge2(PinChargePost pinChargePost) {

        SerialResponse response = paymentBean.charge(pinChargePost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    /**
     *
     * @param pinRecipientPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "VERIFIED USER", "ADMIN"})
    @Path("recipient/create2")
    public Response createRecipient2(PinRecipientPost pinRecipientPost) {

        String username = securityContext.getUserPrincipal().getName();
        UserDTO userDTO = userBean.getUser(username);
        pinRecipientPost.setEmail(userDTO.getEmail());

        SerialResponse response = paymentBean.createRecipient(pinRecipientPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    /**
     *
     * @param recipientToken
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient/{recipient-token}")
    public Response fetchRecipient(@PathParam("recipient-token") String recipientToken) {

        JsonObject recipient = paymentBean.fetchRecipient(recipientToken);

        return Response.ok(recipient, MediaType.APPLICATION_JSON).build();
    }

    /**
     *
     * @param recipientToken
     * @param pinRecipientPut
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient2/{recipient-token}")
    public Response editRecipient2(@PathParam("recipient-token") String recipientToken,
            PinRecipientPut pinRecipientPut) {

        SerialResponse response
                = paymentBean.editRecipient(recipientToken, pinRecipientPut);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }

    /**
     *
     * @param pinTransferPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("transfer2")
    public Response transfer2(PinTransferPost pinTransferPost) {

        SerialResponse response = paymentBean.transfer(pinTransferPost);

        switch (response.getStatusCode()) {
            case 201:
                return Response.ok(response.getBody(), MediaType.APPLICATION_JSON).build();
            case 422:
                return Response.status(422).entity(response.getBody()).build();
            default:
                return Response.status(400).build();
        }
    }
}
