package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.CheckoutDTO;
import javax.ejb.Remote;


@Remote
public interface BookOrderRemote {
    void checkout(CheckoutDTO checkoutDTO, String username);
}
