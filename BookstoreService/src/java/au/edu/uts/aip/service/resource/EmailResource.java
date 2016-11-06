package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.TokenGenerationException;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.util.SendEmail;
import au.edu.uts.aip.service.util.EmailBodyComposer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
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

@Path("email")
public class EmailResource {

    @EJB
    private UserRemote userBean;

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
    @Path("/activation")
    public Response activationToken() {
        try {
            String username = securityContext.getUserPrincipal().getName();
            UserDTO user = userBean.getUser(username);
            String token = userBean.generateActivationToken(username);
            String body = EmailBodyComposer.onAccountActivation(
                    user.getFullname(),
                    servletContext.getInitParameter("clientURL") + "/?token=" + token
                    + "&username=" + user.getUsername() + "&action=activation");
            SendEmail.SendEmail(user.getEmail(), "Account activation", body);

            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email cannot be sent").build();
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
            String body = EmailBodyComposer.onPasswordReset(
                    user.getFullname(),
                    servletContext.getInitParameter("clientURL") + "/?token=" + token
                    + "&username=" + user.getUsername() + "&action=reset");
            SendEmail.SendEmail(user.getEmail(), "Account activation", body);

            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email cannot be sent").build();
        } catch (TokenGenerationException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/reject")
    public Response rejectVerification(@FormParam("username") String username,
            @FormParam("reason") String reason) {

        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onVerificationReject(user.getFullname(), reason);
            SendEmail.SendEmail(user.getEmail(), "Account verification status", body);

            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/approve/{username}")
    public Response approveVerification(@PathParam("username") String username) {

        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onVerificationApprove(user.getFullname());
            SendEmail.SendEmail(user.getEmail(), "Account verification status", body);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/ban/{username}")
    public Response banAccount(@PathParam("username") String username) {
        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onAccountBan(user.getFullname());
            SendEmail.SendEmail(user.getEmail(), "Account banned", body);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/unban/{username}")
    public Response unbanAccount(@PathParam("username") String username) {
        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onAccountUnban(user.getFullname());
            SendEmail.SendEmail(user.getEmail(), "Ban lifted", body);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"VERIFIED"})
    @Path("/order/{order-id}/fail")
    public Response orderFail(@PathParam("order-id") String orderId, String username) {
        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onOrderFail(user.getFullname(), orderId);
            SendEmail.SendEmail(user.getEmail(), "Order failed", body);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"VERIFIED"})
    @Path("/order/{order-id}/pending")
    public Response orderPending(@PathParam("order-id") String orderId, String username) {
        try {
            UserDTO user = userBean.getUser(username);
            String body = EmailBodyComposer.onOrderPending(user.getFullname(), orderId);
            SendEmail.SendEmail(user.getEmail(), "Order pending", body);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed({"VERIFIED"})
    @Path("/order/{order-id}/complete")
    public Response orderComplete(@PathParam("order-id") String orderId, String usernameBuyer, String usernameSeller) {
        try {
            // Email the buyer
            UserDTO buyer = userBean.getUser(usernameBuyer);
            String buyerEmailBody = EmailBodyComposer.onOrderCompleteBuyer(buyer.getFullname(), orderId);
            SendEmail.SendEmail(buyer.getEmail(), "Order complete", buyerEmailBody);
            
            // Email the seller
            UserDTO seller = userBean.getUser(usernameSeller);
            String sellerEmailBody = EmailBodyComposer.onOrderCompleteSeller(seller.getFullname(), orderId);
            SendEmail.SendEmail(buyer.getEmail(), "Order complete", sellerEmailBody);
            
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.serverError().build();
        }
    }
}
