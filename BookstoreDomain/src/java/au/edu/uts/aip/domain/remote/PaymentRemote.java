package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.CheckoutDTO;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import javax.ejb.Remote;
import javax.json.JsonObject;

@Remote
public interface PaymentRemote {

    
    SerialResponse createCustomer2(PinCustomerPost pinCustomerPost);

    SerialResponse charge2(PinChargePost pinChargePost);

    SerialResponse createRecipient2(PinRecipientPost pinRecipientPost);

    SerialResponse transfer2(PinTransferPost pinTransferPost);

    /**
     * Returns the details of a recipient. Note: A bank account is inside a recipient
     *
     * @param recipientToken
     * @return
     */
    JsonObject fetchRecipient(String recipientToken);

    SerialResponse editRecipient2(String recipientToken, PinRecipientPut pinRecipientPut);
    void checkout(CheckoutDTO checkoutDTO, String username);
}
