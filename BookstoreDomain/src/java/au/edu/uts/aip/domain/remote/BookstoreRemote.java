package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.BookDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
import au.edu.uts.aip.domain.entity.Book;
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
public interface BookstoreRemote {

    /**
     * getLatestBooks function is used to get the list of books in the order of time return a list
     * of Book objects
     * @param offset
     * @param limit
     * @return 
     */
    List<BookDTO> getLatestBooks(int offset, int limit);

    /**
     * createBook function is used to create a book record return a Book object
     * @param bookDTO
     * @return 
     */
    public Book createBook(BookDTO bookDTO);

    /**
     * getSingleBook function is used to get the information of the a specific book return BookDTO
     * object
     * @param isbn10
     * @param isbn13
     * @param title
     * @return 
     */
    BookDTO getSingleBook(String isbn10, String isbn13, String title);

    /**
     * updateSale is used to update the sale information of the book return a BookDTO object
     * @param username
     * @param salesData
     * @return 
     */
    BookDTO updateSale(String username, BookDTO salesData);

    /**
     * getSales function is used to get the list of the sales in the order of the time return a list
     * of BookSaleDTO objects
     * @param saleIds
     * @return 
     */
    List<BookSaleDTO> getSales(List<Long> saleIds);
}
