package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import au.edu.uts.aip.domain.pin.dto.BookDTO;
import java.util.ArrayList;
import java.util.List;
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

@Path("book")
public class BookResource {

    @EJB
    private BookstoreRemote bookstoreBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("latest")
    public Response getLatestBooks(@QueryParam("offset") int offset,
            @QueryParam("limit") int limit) {

        List<Book> booksEntity = bookstoreBean.getLatestBooks(offset, limit);
        ArrayList<BookDTO> booksDTO = new ArrayList<>();
        for (Book bookEntity : booksEntity) {
            booksDTO.add(new BookDTO(bookEntity));
        }

        return Response.status(Response.Status.OK).entity(booksDTO.toArray(new BookDTO[0])).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@QueryParam("isbn10") String isbn10,
            @QueryParam("isbn13") String isbn13,
            @QueryParam("title") String title) {
        Book bookEntity = bookstoreBean.getSingleBook(isbn10, isbn13, title);
        BookDTO bookDTO = new BookDTO(bookEntity, bookEntity.getSales());

        return Response.ok(bookDTO).build();
    }

    @POST
    @Path("sales")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSales(BookDTO salesData) {
        bookstoreBean.updateSale("admin1", salesData);
        return "TEST";
    }
}
