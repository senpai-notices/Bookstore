package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.ejb.Remote;
import javax.json.JsonObject;

@Remote
public interface PaymentRemote {

    ValidationResult createCustomer(PinCustomerPost pinCustomerPost);
    
    SerialResponse createCustomer2(PinCustomerPost pinCustomerPost);

    ValidationResult charge(PinChargePost pinChargePost);
    SerialResponse charge2(PinChargePost pinChargePost);

    ValidationResult createRecipient(PinRecipientPost pinRecipientPost);
    SerialResponse createRecipient2(PinRecipientPost pinRecipientPost);

    ValidationResult transfer(PinTransferPost pinTransferPost);
    SerialResponse transfer2(PinTransferPost pinTransferPost);

    /**
     * Returns the details of a recipient. Note: A bank account is inside a recipient
     *
     * @param recipientToken
     * @return
     */
    JsonObject fetchRecipient(String recipientToken);

    /**
     * Updates the given details of a recipient and returns its details. Note: A bank account is
     * inside a recipient
     *
     * @param recipientToken
     * @param pinRecipientPut
     * @return
     */
    ValidationResult editRecipient(String recipientToken, PinRecipientPut pinRecipientPut);
    SerialResponse editRecipient2(String recipientToken, PinRecipientPut pinRecipientPut);
}
