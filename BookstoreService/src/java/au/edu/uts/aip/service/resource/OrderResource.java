package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import au.edu.uts.aip.domain.remote.BookOrderRemote;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("order")
public class OrderResource {
   
    @Context
    private SecurityContext securityContext;
    
    @EJB
    private BookOrderRemote bookOrderBean;
    
    @POST
    @Path("checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "VERIFYING USER", "VERIFIED USER", "ADMIN"})
    public Response checkout(CheckoutDTO checkoutDTO){
        String username = securityContext.getUserPrincipal().getName();
        bookOrderBean.checkout(checkoutDTO, username);
        return Response.ok().build();
    }
    
    @GET
    @RolesAllowed({"USER", "VERIFIYING USER", "VERIFIED USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuyOrders(){
        String username = securityContext.getUserPrincipal().getName();
        List<BookOrderDTO> result = bookOrderBean.getBuyOrder(username);
        return Response.ok(result.toArray(new BookOrderDTO[0])).build();
    }
}
