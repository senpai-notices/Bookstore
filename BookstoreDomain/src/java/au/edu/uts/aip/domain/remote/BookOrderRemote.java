package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface BookOrderRemote {
    void checkout(CheckoutDTO checkoutDTO, String username);
    List<BookOrderDTO> getBuyOrder(String username);
}
