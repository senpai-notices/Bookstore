package au.edu.uts.aip.resource;

import au.edu.uts.aip.entity.User;
import au.edu.uts.aip.domain.UserRemote;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/user")
@RequestScoped
@Named
public class UserResource {

    @EJB
    private UserRemote userBean;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    /**
     * Login to the system using basic HTTP authentication
     * @return HTTP status code ACCEPTED and user detail if authorization header
     * is correct Otherwise, return HTTP status code UNAUTHORIZED
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN"})
    public Response login() {
        User user = userBean.getUser(request.getUserPrincipal().getName());
        user.setPassword("");
        return Response.status(Response.Status.ACCEPTED).entity(user).build();
    }
}
