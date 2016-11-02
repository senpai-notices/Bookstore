package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.pin.dto.PinCardPost;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import javax.ws.rs.core.Response;

public interface PaymentRemote {

    Response createCard(PinCardPost pinCardPost);

    Response createCustomer(PinCustomerPost pinCustomerPost);

    Response charge(PinChargePost pinChargePost);
    
    Response createRecipient(PinRecipientPost pinRecipientPost);
    
    Response transfer(PinTransferPost pinTransferPost);
    
    /**
     * Returns the details of a recipient.
     * Note: A bank account is inside a recipient
     * @param recipientToken
     * @return 
     */
    Response fetchRecipient(String recipientToken);
    
    /**
     * Updates the given details of a recipient and returns its details.
     * Note: A bank account is inside a recipient
     * @param recipientToken
     * @param pinRecipientPut
     * @return 
     */
    Response editRecipient(String recipientToken, PinRecipientPut pinRecipientPut);
}
