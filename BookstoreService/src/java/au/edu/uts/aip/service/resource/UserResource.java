package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.ejb.UserRemote;
import au.edu.uts.aip.domain.validation.ValidationResult;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/user")
@RequestScoped
public class UserResource {

    @EJB
    private UserRemote userBean;

    @Context
    private HttpServletRequest request;

    @Context
    private SecurityContext securityContext;

    /**
     * Retrieve the current authenticated user
     *
     * @return HTTP status code OK and user detail, without password attached
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN"})
    public Response get() {
        User user = userBean.getUser(request.getUserPrincipal().getName());
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("username", user.getUsername());
        jsonBuilder.add("fullname", user.getFullname());
        jsonBuilder.add("email", user.getEmail());
        jsonBuilder.add("role", user.getRole().getRoleName());

        return Response.status(Response.Status.OK).entity(jsonBuilder.build()).build();
    }

    /**
     * Create a new user account
     *
     * @param username
     * @param password
     * @param email
     * @param fullname
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email,
            @FormParam("fullname") String fullname) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFullname(fullname);

            ValidationResult result = userBean.createUser(user);
            if (result == null) {
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result.toJson()).build();
            }
        }
        catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    /**
     * Send an email to activate user's account
     * @param username
     * @return 
     */
    @POST
    @Path("activate")
    @RolesAllowed({"ADMIN", "INACTIVATED"})
    public Response activateAccount(@FormParam("username") String username){
        try {
            userBean.sendActivateEmail(username);
            return Response.ok().build();
        } catch (MessagingException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
