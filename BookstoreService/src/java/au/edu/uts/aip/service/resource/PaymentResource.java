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
 * REST endpoint for payments. Uses the Pin Payments API
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("payment")
public class PaymentResource {

    @EJB
    private PaymentRemote paymentBean;

    @EJB
    private UserRemote userBean;

    @Context
    private SecurityContext securityContext;

    // TODO: remove '2' from methods
    /**
     * Creates a new Pin customer and returns its details.
     * @param pinCustomerPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("customer/create2")
    public Response createCustomer(PinCustomerPost pinCustomerPost) {

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
     * Creates a new charge and returns its details. This may be a long-running request.
     * @param pinChargePost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("charge2")
    public Response charge(PinChargePost pinChargePost) {

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
     * Creates a new Pin recipient and returns its details.
     * @param pinRecipientPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "VERIFIED USER", "ADMIN"})
    @Path("recipient/create2")
    public Response createRecipient(PinRecipientPost pinRecipientPost) {

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
     * Returns the details of a Pin recipient.
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
     * Updates the given details of a Pin recipient and returns its details.
     * @param recipientToken
     * @param pinRecipientPut
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("recipient2/{recipient-token}")
    public Response editRecipient(@PathParam("recipient-token") String recipientToken,
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
     * Creates a new Pin transfer and returns its details.
     * @param pinTransferPost
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"USER", "ADMIN"})
    @Path("transfer2")
    public Response transfer(PinTransferPost pinTransferPost) {

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
