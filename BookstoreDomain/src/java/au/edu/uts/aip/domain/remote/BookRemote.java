package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.BookDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 * BookstoreBean is a JavaBean class to manage the books and sales on the website It is a bookstore
 * controller class
 *
 * It has four methods: getLatestBooks; getSingleBook; createBook; updateSale; getSales;
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface BookRemote {

    /**
     * getLatestBooks function is used to get the list of books in the order of time return a list
     * of Book objects
     *
     * @param offset
     * @param limit
     * @return
     */
    List<BookDTO> getLatestBooks(int offset, int limit);

    /**
     * getSingleBook function is used to get the information of the a specific book return BookDTO
     * object
     *
     * @param isbn10
     * @param isbn13
     * @param title
     * @return
     */
    BookDTO getSingleBook(String isbn10, String isbn13, String title);
}
