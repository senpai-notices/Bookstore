package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.InvalidTokenException;
import au.edu.uts.aip.domain.utility.SendEmail;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("activation")
public class ActivationTokenResource {
    
    @EJB
    private UserRemote userBean;

    @Context
    private SecurityContext securityContext;
    
    @Context
    private ServletContext servletContext;
    
    /**
     * Send an email to activate user's account
     * @return 
     */
    @GET
    @RolesAllowed({"INACTIVATED"})
    public Response get(){
        try {
            User user = userBean.getUser(securityContext.getUserPrincipal().getName());
            String token = userBean.generateActivationToken(user);
            SendEmail.SendActivationEmail(user, token, servletContext.getInitParameter("clientURL"));
            
            return Response.ok().build();
        } catch (MessagingException ex) {
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
    public Response post(@FormParam("token") String token, @FormParam("username") String username) {
        try {
            userBean.activateAccount(token, username);
            return Response.status(Response.Status.OK).build();
        } catch (InvalidTokenException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build();
        } catch (ActivationException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity("Cannot activate account").build();
        }
    }
}
