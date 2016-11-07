package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.BookOrderLineDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
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

/**
 * REST endpoint for orders
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("order")
public class OrderResource {
   
    @Context
    private SecurityContext securityContext;
    
    @EJB
    private BookOrderRemote bookOrderBean;
    
    /**
     * Perform checkout sequence.
     * @param checkoutDTO
     * @return
     */
    @POST
    @Path("checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"USER", "VERIFYING USER", "VERIFIED USER"})
    public Response checkout(CheckoutDTO checkoutDTO){
        String username = securityContext.getUserPrincipal().getName();
        long orderId = bookOrderBean.checkout(checkoutDTO, username);
        return Response.ok(orderId).build();
    }
    
    /**
     * Gets a list of current book orders of user
     * @return
     */
    @GET
    @Path("buy")
    @RolesAllowed({"USER", "VERIFIYING USER", "VERIFIED USER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuyOrders(){
        String username = securityContext.getUserPrincipal().getName();
        List<BookOrderDTO> result = bookOrderBean.getBuyOrder(username);
        return Response.ok(result.toArray(new BookOrderDTO[0])).build();
    }
    
    @GET
    @Path("sold")
    @RolesAllowed({"VERIFIED USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellOrderLines(){
        String username = securityContext.getUserPrincipal().getName();
        List<BookOrderLineDTO> result = bookOrderBean.getSoldBooks(username);
        
        return Response.ok(result.toArray(new BookOrderLineDTO[0])).build();
    }
    
    @GET
    @Path("selling")
    @RolesAllowed({"VERIFIED USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellingBooks(){
        String username = securityContext.getUserPrincipal().getName();
        List<BookSaleDTO> result = bookOrderBean.getSellingBooks(username);
        
        return Response.ok(result.toArray(new BookSaleDTO[0])).build();
    }
}
