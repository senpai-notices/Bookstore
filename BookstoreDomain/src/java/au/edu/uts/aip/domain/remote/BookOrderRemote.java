package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.BookOrderLineDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 * Contains methods for book orders.
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface BookOrderRemote {

    /**
     * Creates orders from each sale, then charges customer's card
     * @param checkoutDTO
     * @param username
     */
    long checkout(CheckoutDTO checkoutDTO, String username);

    /**
     * Gets user's current orders
     * @param username
     * @return
     */
    List<BookOrderDTO> getBuyOrder(String username);
    
    /**
     * Get order based on it's id
     * @param orderId
     * @return 
     */
    BookOrderDTO getOrder(long orderId);
    
    /**
     * Get order lines belong to a seller
     * @param username the seller account name
     * @return 
     */
    List<BookOrderLineDTO> getSoldBooks(String username);
    
    /**
     * Get a list of book sales belong to a seller
     * @param username the seller account name
     * @return 
     */
    List<BookSaleDTO> getSellingBooks(String username);
}
