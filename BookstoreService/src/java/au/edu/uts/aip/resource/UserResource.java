package au.edu.uts.aip.resource;

import au.edu.uts.aip.entity.User;
import au.edu.uts.aip.domain.UserRemote;
import au.edu.uts.aip.validation.ValidationResult;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
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
     * Retrieve the current authenticated user
     * @return HTTP status code OK and user detail, without password attached
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN"})
    public Response get() {
        User user = userBean.getUser(request.getUserPrincipal().getName());
        user.setPassword("");
        return Response.status(Response.Status.OK).entity(user).build();
    }
    
    /**
     * Create a new user
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
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullname(fullname);
        
        try{
            ValidationResult result = userBean.createUser(user);
            if (result == null){
                return Response.status(Response.Status.ACCEPTED).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity(result.toJson()).build();
            }
        } catch (Exception ex){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
    }
}
