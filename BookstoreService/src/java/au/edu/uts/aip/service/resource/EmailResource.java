package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.exception.TokenGenerationException;
import au.edu.uts.aip.domain.remote.BookOrderRemote;
import au.edu.uts.aip.domain.remote.EmailBodyComposerRemote;
import au.edu.uts.aip.domain.remote.EmailRemote;
import au.edu.uts.aip.domain.remote.UserRemote;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * REST endpoint for sending emails
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("email")
public class EmailResource {

    @EJB
    private UserRemote userBean;

    @EJB
    private EmailRemote emailBean;

    @EJB
    private EmailBodyComposerRemote emailBodyComposerBean;

    @EJB
    private BookOrderRemote bookOrderBean;

    @Context
    private SecurityContext securityContext;

    @Context
    private ServletContext servletContext;

    /**
     * Send an email that include a link to activate user's account
     *
     * @return
     */
    @POST
    @RolesAllowed({"INACTIVATED"})
    @Path("activation")
    public Response activationToken() {
        try {
            String username = securityContext.getUserPrincipal().getName();
            UserDTO user = userBean.getUser(username);
            String token = userBean.generateActivationToken(username);
            String body = emailBodyComposerBean.onAccountActivation(
                    user.getFullname(),
                    servletContext.getInitParameter("clientURL") + "/?token=" + token
                    + "&username=" + user.getUsername() + "&action=activation");
            emailBean.sendEmail(user.getEmail(), "Account activation", body);

            return Response.ok().build();
        } catch (TokenGenerationException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    /**
     * Send an email that include a link to reset user's password
     *
     * @param username
     * @param email
     * @return
     */
    @POST
    @Path("reset")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response resetPasswordToken(@FormParam("username") String username,
            @FormParam("email") String email) {
        try {
            UserDTO user = userBean.getUser(username);
            String token = userBean.generateResetPasswordToken(username, email);
            String body = emailBodyComposerBean.onPasswordReset(
                    user.getFullname(),
                    servletContext.getInitParameter("clientURL") + "/?token=" + token
                    + "&username=" + user.getUsername() + "&action=reset");

            emailBean.sendEmail(user.getEmail(), "Password reset", body);

            return Response.ok().build();
        } catch (TokenGenerationException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    /**
     * Send email to notify user that verification is rejected. A rejection reason is also included.
     *
     * @param username
     * @param reason
     * @return
     */
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("reject")
    public Response rejectVerification(@FormParam("username") String username,
            @FormParam("reason") String reason) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onVerificationReject(user.getFullname(), reason);
        emailBean.sendEmail(user.getEmail(), "Account verification status", body);

        return Response.ok().build();

    }

    /**
     * Send email to notify user that verification is approved.
     *
     * @param username
     * @return
     */
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("approve/{username}")
    public Response approveVerification(@PathParam("username") String username) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onVerificationApprove(user.getFullname());
        emailBean.sendEmail(user.getEmail(), "Account verification status", body);
        return Response.ok().build();

    }

    /**
     * Send email to notify user is banned.
     *
     * @param username
     * @return
     */
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("ban/{username}")
    public Response banAccount(@PathParam("username") String username) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onAccountBan(user.getFullname());
        emailBean.sendEmail(user.getEmail(), "Account banned", body);
        return Response.ok().build();

    }

    /**
     * Send email to notify user is unbanned.
     *
     * @param username
     * @return
     */
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("unban/{username}")
    public Response unbanAccount(@PathParam("username") String username) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onAccountUnban(user.getFullname());
        emailBean.sendEmail(user.getEmail(), "Ban lifted", body);
        return Response.ok().build();

    }

    // TODO: the below methods.. use?
    /**
     *
     * @param orderId
     * @param username
     * @return
     */
    @POST
    @RolesAllowed({"VERIFIED"})
    @Path("order/{order-id}/fail")
    public Response orderFail(@PathParam("order-id") String orderId, String username) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onOrderFail(user.getFullname(), orderId);
        emailBean.sendEmail(user.getEmail(), "Order failed", body);
        return Response.ok().build();

    }

    /**
     *
     * @param orderId
     * @param username
     * @return
     */
    @POST
    @RolesAllowed({"VERIFIED"})
    @Path("order/{order-id}/pending")
    public Response orderPending(@PathParam("order-id") String orderId, String username) {

        UserDTO user = userBean.getUser(username);
        String body = emailBodyComposerBean.onOrderPending(user.getFullname(), orderId);
        emailBean.sendEmail(user.getEmail(), "Order pending", body);
        return Response.ok().build();

    }

    /**
     *
     * @param orderId
     * @return
     */
    @POST
    @RolesAllowed({"USER", "VERIFYING USER", "VERIFIED USER"})
    @Path("order/{order-id}/complete")
    public Response orderComplete(@PathParam("order-id") long orderId) {
        String usernameBuyer = this.securityContext.getUserPrincipal().getName();
        String usernameSeller = "";

        // Email the buyer
        UserDTO buyer = userBean.getUser(usernameBuyer);
        String buyerEmailBody = emailBodyComposerBean.onOrderCompleteBuyer(buyer.getFullname(), orderId + "");
        emailBean.sendEmail(buyer.getEmail(), "Order complete", buyerEmailBody);

        List<UserDTO> sellerList = new ArrayList<>();
        BookOrderDTO orderDTO = bookOrderBean.getOrder(orderId);
        orderDTO.getLines().stream().forEach(orderLineDTO -> {
            if (!sellerList.contains(orderLineDTO.getSeller())) {
                sellerList.add(orderLineDTO.getSeller());
            }
        });

        // Email the seller
        sellerList.stream().forEach(seller -> {
            String sellerEmailBody = emailBodyComposerBean.onOrderCompleteSeller(seller.getFullname(), orderId + "");
            emailBean.sendEmail(buyer.getEmail(), "Order complete", sellerEmailBody);
        });

        return Response.ok().build();

    }
}
