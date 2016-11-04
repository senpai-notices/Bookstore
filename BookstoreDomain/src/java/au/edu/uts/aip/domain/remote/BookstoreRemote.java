package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.dto.BookDTO;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BookstoreRemote {

    List<Book> getLatestBooks(int offset, int limit);

    Book getSingleBook(String isbn10, String isbn13, String title);

    BookDTO updateSale(String username, BookDTO salesData);

}
