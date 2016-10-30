package au.edu.uts.aip.service.resource;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("book")
public class BookResource {
    @EJB
    private BookstoreRemote bookstoreBean;
    
    @GET
    @Path("/latest")
    public Response getLatestBooks(@QueryParam("offset") int offset,
            @QueryParam("limit") int limit){
        
        List<Book> books = bookstoreBean.getLatestBooks(offset, limit);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Book book: books){
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", book.getId());
            objectBuilder.add("title", book.getTitle());
            objectBuilder.add("author", book.getName());
            objectBuilder.add("year", book.getPublishYear());
            objectBuilder.add("publisher", book.getPublisher());
            objectBuilder.add("imgPath", book.getImgPath());
            JsonObject jsonBook = objectBuilder.build();
            arrayBuilder.add(jsonBook);
        }
        
        return Response.status(Response.Status.OK).entity(arrayBuilder.build()).build();
    }
}
