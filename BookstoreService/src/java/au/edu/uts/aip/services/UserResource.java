package au.edu.uts.aip.services;

import au.edu.uts.aip.DTO.User;
import au.edu.uts.aip.Domain.UserRemote;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author sondang
 */
@Path("/user")
@RequestScoped
public class UserResource {
    
    @EJB
    private UserRemote userBean;
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login( @FormParam("username") String username, @FormParam("password") String password ){
        // For testing
        if ("username1".equals(username) && "1234561".equals(password)){
            User user = userBean.getUser(username, password);
            user.setID(1);
            user.setUsername(username);
            user.setFullname("normal user 1");
            user.setPassword(password);
            user.setRole("user");
            
            return Response.status(200).entity(user).build();
        }
        
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
