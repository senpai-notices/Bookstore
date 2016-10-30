package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.validation.ValidationResult;
import au.edu.uts.aip.service.dto.UserDTO;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/user")
@RequestScoped
public class UserResource {

    @EJB
    private UserRemote userBean;

    @Context
    private HttpServletRequest request;

    /**
     * Retrieve the current authenticated user
     *
     * @return HTTP status code OK and user detail, without password attached
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN", "VERIFIED USER", "INACTIVATED"})
    public Response get() {
        User userEntity = userBean.getUser(request.getUserPrincipal().getName());
        UserDTO userDTO = new UserDTO(userEntity);

        return Response.status(Response.Status.OK).entity(userDTO).build();
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
}
