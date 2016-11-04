package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.InvalidTokenException;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.validation.ValidationResult;
import au.edu.uts.aip.domain.pin.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("user")
@RequestScoped
public class UserResource {

    @EJB
    private UserRemote userBean;

    @Context
    private HttpServletRequest request;

    /**
     * Retrieve the current authenticated user
     * Banned accounts or not logged in user will not be able to retrieve 
     * their account detail
     *
     * @return HTTP status code OK and user detail, without password attached
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN", "VERIFYING USER", "VERIFIED USER", "INACTIVATED"})
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
    
    /**
     * Activate user's account
     * @param token
     * @param username
     * @return 
     */
    @POST
    @Path("activate")
    public Response activateAccount(@FormParam("token") String token, @FormParam("username") String username) {
        try {
            userBean.activateAccount(token, username);
            return Response.status(Response.Status.OK).build();
        } catch (InvalidTokenException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build();
        } catch (ActivationException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity("Cannot activate account").build();
        }
    }
    
    /**
     * Retrieve a list of user accounts with filter
     * Only administrators can access this resource
     * @param roles
     * @param username
     * @param fullname
     * @param email
     * @param offset
     * @param limit
     * @return 
     */
    @GET
    @Path("list")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccounts(@QueryParam("roles") String roles,
                                @QueryParam("username") String username,
                                @QueryParam("fullname") String fullname,
                                @QueryParam("email") String email,
                                @QueryParam("offset") int offset,
                                @QueryParam("limit") int limit){
        String[] rolesName = roles.split(",");
        List<User> usersEntity = userBean.findUsers(rolesName, username, fullname, email, offset, limit);
        ArrayList<UserDTO> usersDTO = new ArrayList<>();
        for(User userEntity : usersEntity){
            usersDTO.add(new UserDTO(userEntity));
        }

        return Response.status(Response.Status.OK).entity(usersDTO.toArray(new UserDTO[0])).build();
    }
    
    @POST
    @Path("ban/{username}")
    @RolesAllowed({"ADMIN"})
    public Response banAccount(@PathParam("username") String username){
        userBean.banAccount(username);
        return Response.ok().build();
    }
    
    @POST
    @Path("unban/{username}")
    @RolesAllowed({"ADMIN"})
    public Response unbanAccount(@PathParam("username") String username){
        userBean.unbanAccount(username);
        return Response.ok().build();
    }
}
