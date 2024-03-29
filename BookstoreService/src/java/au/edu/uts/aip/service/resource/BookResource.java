package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.dto.BookDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import au.edu.uts.aip.domain.remote.BookRemote;
import au.edu.uts.aip.domain.remote.BookSaleRemote;

/**
 * REST endpoint for book methods
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Path("book")
public class BookResource {

    @EJB
    private BookRemote bookBean;

    @EJB
    private BookSaleRemote bookSaleBean;

    @Context
    private SecurityContext securityContext;

    /**
     * Get latest books
     *
     * @param offset
     * @param limit
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("latest")
    public Response getLatestBooks(@QueryParam("offset") int offset,
            @QueryParam("limit") int limit) {

        List<BookDTO> booksDTO = bookBean.getLatestBooks(offset, limit);
        return Response.status(Response.Status.OK).entity(booksDTO.toArray(new BookDTO[0])).build();
    }

    /**
     * Get books by ISBN10, ISBN13 and/or title
     *
     * @param isbn10
     * @param isbn13
     * @param title
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@QueryParam("isbn10") String isbn10,
            @QueryParam("isbn13") String isbn13,
            @QueryParam("title") String title) {
        BookDTO bookDTO = bookBean.getSingleBook(isbn10, isbn13, title);

        return Response.ok(bookDTO).build();
    }

    /**
     * Update a sale with its newer object
     *
     * @param salesData
     * @return
     */
    @POST
    @Path("sales")
    @RolesAllowed({"ADMIN", "VERIFIED USER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSales(BookDTO salesData) {
        BookDTO result = bookSaleBean.updateSale(securityContext.getUserPrincipal().getName(), salesData);
        return Response.ok().entity(result).build();
    }

    /**
     * Get particular sales of books by its ID/s
     *
     * @param saleIds
     * @return
     */
    @GET
    @Path("sales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSales(@QueryParam("saleIds") String saleIds) {
        List<BookSaleDTO> result = bookSaleBean.getSales(Arrays.asList(saleIds.split(",")).stream().map(Long::parseLong).collect(Collectors.toList()));
        return Response.ok().entity(result.toArray(new BookSaleDTO[0])).build();
    }
}
