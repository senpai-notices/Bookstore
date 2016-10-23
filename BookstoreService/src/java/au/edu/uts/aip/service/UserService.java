package au.edu.uts.aip.service;

import au.edu.uts.aip.entity.User;
import au.edu.uts.aip.domain.UserRemote;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/user")
@RequestScoped
@Named
public class UserService {

    @EJB
    private UserRemote userBean;

    @Context
    private HttpServletRequest request;
    /**
     *
     * @param username
     * @param password
     * @return HTTP status code ACCEPTED and user detail if login credentials
     * are correct Otherwise, return HTTP status code BAD_REQUEST
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            // login using container authentication
            request.login(username, password);
            
            // retrive user detail
            User user = userBean.getUser(username);
            
            // do not send password back
            user.setPassword("");
            
            return Response.status(Response.Status.ACCEPTED).entity(user).build();
        } catch (ServletException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
