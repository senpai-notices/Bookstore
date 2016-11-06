package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import javax.ejb.Remote;
import javax.json.JsonObject;

/**
 * PaymentBean is an EJB that is used to handle the payment related operations
 *
 * It uses the Pin API to handle the payment Official Pin API documentation can be found here:
 * https://pin.net.au/docs/api/
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface PaymentRemote {

    /**
     * API address
     */
    static final String BASE_URL = "https://test-api.pin.net.au/1";
    /**
     * API secret key
     */
    static final String API_KEY_SECRET = "***REMOVED***";

    /**
     *
     */
    static final String PASSWORD = "";

    /**
     * createCustomer is used to create a customer return ResponseDTO
     *
     * @param pinCustomerPost
     * @return
     */
    SerialResponse createCustomer(PinCustomerPost pinCustomerPost);

    /**
     * charge function is used to charge the money
     *
     * @param pinChargePost
     * @return
     */
    SerialResponse charge(PinChargePost pinChargePost);

    /**
     * CreateRecipient function is used to create a recipient
     *
     * @param pinRecipientPost
     * @return
     */
    SerialResponse createRecipient(PinRecipientPost pinRecipientPost);

    /**
     * transfer function is used to transfer the money from API owner to bank account
     *
     * @param pinTransferPost
     * @return
     */
    SerialResponse transfer(PinTransferPost pinTransferPost);

    /**
     * Returns the details of a recipient. Note: A bank account is inside a recipient
     *
     * @param recipientToken
     * @return
     */
    JsonObject fetchRecipient(String recipientToken);

    /**
     * editRecipient function is used to edit the information of the recipient
     *
     * @param recipientToken
     * @param pinRecipientPut
     * @return
     */
    SerialResponse editRecipient(String recipientToken, PinRecipientPut pinRecipientPut);
}
