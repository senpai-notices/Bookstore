package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import au.edu.uts.aip.service.dto.BookDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("book")
public class BookResource {
    @EJB
    private BookstoreRemote bookstoreBean;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/latest")
    public Response getLatestBooks(@QueryParam("offset") int offset,
            @QueryParam("limit") int limit){
        
        List<Book> booksEntity = bookstoreBean.getLatestBooks(offset, limit);
        ArrayList<BookDTO> booksDTO = new ArrayList<>();
        for(Book bookEntity: booksEntity){
            booksDTO.add(new BookDTO(bookEntity));
        }
        
        return Response.status(Response.Status.OK).entity(booksDTO.toArray(new BookDTO[0])).build();
    }
}
