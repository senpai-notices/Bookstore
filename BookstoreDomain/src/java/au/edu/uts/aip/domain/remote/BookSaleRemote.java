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
public interface BookSaleRemote {

    /**
     * createBook function is used to create a book record return a Book object
     *
     * @param bookDTO
     * @return
     */
    public Book createBook(BookDTO bookDTO);

    /**
     * updateSale is used to update the sale information of the book return a BookDTO object
     *
     * @param username
     * @param salesData
     * @return
     */
    BookDTO updateSale(String username, BookDTO salesData);

    /**
     * getSales function is used to get the list of the sales in the order of the time return a list
     * of BookSaleDTO objects
     *
     * @param saleIds
     * @return
     */
    List<BookSaleDTO> getSales(List<Long> saleIds);
}
