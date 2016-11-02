package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.pin.dto.PinCardPost;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import javax.ws.rs.core.Response;

public interface PaymentRemote {

    Response createCard(PinCardPost pinCardPost);

    Response createCustomer(PinCustomerPost pinCustomerPost);

    Response charge(PinChargePost pinChargePost);
    
    Response createRecipient(PinRecipientPost pinRecipientPost);
    
    Response transfer(PinTransferPost pinTransferPost);
}
