package au.edu.uts.aip.domain.remote;

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

    ValidationResult charge(PinChargePost pinChargePost);

    ValidationResult createRecipient(PinRecipientPost pinRecipientPost);

    ValidationResult transfer(PinTransferPost pinTransferPost);

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
}
