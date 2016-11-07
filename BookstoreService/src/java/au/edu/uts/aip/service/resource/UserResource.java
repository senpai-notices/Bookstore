package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.AddressDTO;
import au.edu.uts.aip.domain.dto.RegistrationDTO;
import au.edu.uts.aip.domain.dto.ResetPasswordDTO;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.remote.AdminRemote;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * REST endpoint for user accounts and administration
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("user")
@RequestScoped
public class UserResource {

    @EJB
    private UserRemote userBean;

    @EJB
    private AdminRemote adminBean;

    @Context
    private HttpServletRequest request;

    @Context
    private SecurityContext securityContext;

    /**
     * Retrieve the current authenticated user Banned accounts or not logged in user will not be
     * able to retrieve their account detail
     *
     * @return HTTP status code OK and user detail, without password attached
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "ADMIN", "VERIFYING USER", "VERIFIED USER", "INACTIVATED"})
    public Response get() {
        UserDTO user = userBean.getUser(request.getUserPrincipal().getName());
        return Response.status(Response.Status.OK).entity(user).build();
    }

    /**
     * Create a new user account
     *
     * @param registrationDTO
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(@Valid RegistrationDTO registrationDTO) {
        userBean.createUser(registrationDTO);
        return Response.ok().build();
    }

    /**
     *
     * @param addressDTO
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("address")
    @RolesAllowed({"USER", "VERIFYING USER"})
    public Response updateAddress(AddressDTO addressDTO) {
        String username = securityContext.getUserPrincipal().getName();
        userBean.updateAddress(addressDTO, username);
        return Response.ok().build();
    }

    /**
     * Activate user's account
     *
     * @param token
     * @param username
     * @return
     */
    @POST
    @Path("activate")
    public Response activateAccount(@FormParam("token") String token,
            @FormParam("username") String username) {
        try {
            userBean.activateAccount(token, username);
            return Response.ok().build();
        } catch (ActivationException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    /**
     * Reset user's password
     *
     * @param resetPasswordDTO
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reset")
    public Response resetPassword(@Valid ResetPasswordDTO resetPasswordDTO) {
        userBean.resetPassword(resetPasswordDTO);
        return Response.ok().build();
    }

    /**
     * Assign a Pin recipient token to a user. The token uniquely identifies the user's bank account
     * details.
     *
     * @param token
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("recipientToken")
    @RolesAllowed({"USER", "VERIFIED USER", "ADMIN"})
    public Response setRecipientToken(@FormParam("token") String token) {
        String username = securityContext.getUserPrincipal().getName();
        userBean.updateRecipientToken(username, token);
        return Response.ok().build();
    }

    /**
     *
     * @return
     */
    @GET
    @Path("orders")
    public Response getOrders() {

        return Response.ok().build();
    }

    /**
     * Retrieve a list of user accounts with filter Only administrators can access this resource.
     *
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
            @QueryParam("limit") int limit) {
        String[] rolesName = roles.split(",");
        List<UserDTO> usersDTO = userBean.findUsers(rolesName, username, fullname, email, offset, limit);

        return Response.status(Response.Status.OK).entity(usersDTO.toArray(new UserDTO[0]))
                .build();
    }

    /**
     * Ban a user account. Banned accounts cannot log in.
     * @param username
     * @return
     */
    @POST
    @Path("ban/{username}")
    @RolesAllowed({"ADMIN"})
    public Response banAccount(@PathParam("username") String username) {
        adminBean.banAccount(username);
        return Response.ok().build();
    }

    /**
     * Unban a user account.
     * @param username
     * @return
     */
    @POST
    @Path("unban/{username}")
    @RolesAllowed({"ADMIN"})
    public Response unbanAccount(@PathParam("username") String username) {
        adminBean.unbanAccount(username);
        return Response.ok().build();
    }
}
