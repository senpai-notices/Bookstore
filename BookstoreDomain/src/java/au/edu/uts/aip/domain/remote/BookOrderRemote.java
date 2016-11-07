package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 * Contains methods for book orders.
 *
 * @author x
 */
@Remote
public interface BookOrderRemote {

    /**
     * Creates orders from each sale, then charges customer's card
     * @param checkoutDTO
     * @param username
     */
    void checkout(CheckoutDTO checkoutDTO, String username);

    /**
     * Gets user's current orders
     * @param username
     * @return
     */
    List<BookOrderDTO> getBuyOrder(String username);
}
