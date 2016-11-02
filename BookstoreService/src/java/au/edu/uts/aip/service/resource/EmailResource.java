package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.utility.SendEmail;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
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
     * @return 
     */
    @POST
    @RolesAllowed({"INACTIVATED"})
    @Path("/activation")
    public Response activationToken(){
        try {
            User user = userBean.getUser(securityContext.getUserPrincipal().getName());
            String token = userBean.generateActivationToken(user);
            
            String body = servletContext.getInitParameter("clientURL") + "/?token=" + token;
            body += "&username=" + user.getUsername();
            SendEmail.SendEmail(user.getEmail(), "Account activation", body);
            
            return Response.ok().build();
        } catch (MessagingException ex){
            return Response.serverError().build();
        }
    }
    
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/reject")
    public Response rejectVerification(@FormParam("username") String username,
            @FormParam("reason") String reason){
        
        try{
            User user = userBean.getUser(username);
            String body = "Dear " + user.getFullname() + ", we are sorry that your "
                    + "account verification request cannot be proceeded.\n\n"
                    + "Reason: " + reason;
            
            SendEmail.SendEmail(user.getEmail(), "Account verification status", body);
            
            return Response.ok().build();
        } catch (MessagingException ex){
            return Response.serverError().build();
        }
    }
    
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/approve/{username}")
    public Response approveVerification(@PathParam("username") String username){
        
        try {
            User user = userBean.getUser(username);
            String body = "Dear " + user.getFullname() + ", your verification request has been approved\n\n";
            
            SendEmail.SendEmail(user.getEmail(), "Account verification status", body);
            return Response.ok().build();
        } catch (MessagingException ex){
            return Response.serverError().build();
        }
    }
    
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/ban/{username}")
    public Response banAccount(@PathParam("username") String username){
        try {
            User user = userBean.getUser(username);
            String body = "Dear " + user.getFullname() + ", your account has been banned\n\n";
            SendEmail.SendEmail(user.getEmail(), "Account banned", body);
            return Response.ok().build();
        } catch (MessagingException ex){
            return Response.serverError().build();
        }
    }
    
    @POST
    @RolesAllowed({"ADMIN"})
    @Path("/unban/{username}")
    public Response unbanAccount(@PathParam("username")String username){
        try {
            User user = userBean.getUser(username);
            String body = "Dear " + user.getFullname()+ ", your account has been unbanned\n\n";
            SendEmail.SendEmail(user.getEmail(), "Ban lifted", body);
            return Response.ok().build();
        } catch (MessagingException ex){
            return Response.serverError().build();
        }
    }
}
