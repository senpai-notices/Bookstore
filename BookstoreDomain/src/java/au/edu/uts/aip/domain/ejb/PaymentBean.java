package au.edu.uts.aip.domain.ejb;

/**
 * the needed library
 */
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.pin.filter.PinAuthFilter;
import au.edu.uts.aip.domain.pin.filter.PinResponseLoggingFilter;
import au.edu.uts.aip.domain.pin.util.PinResponseUtil;
import au.edu.uts.aip.domain.util.ApiResponseUtil;
import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * PaymentBean is an EJB that is used to handle the payment related operations
 * 
 * It uses the Pin API to handle the payment 
 * Official Pin API documentation can be found here: https://pin.net.au/docs/api/
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Stateless
public class PaymentBean implements PaymentRemote {

    /**
    * API address
    */
    private static final String BASE_URL = "https://test-api.pin.net.au/1";
    /**
    * API secret key
    */
    private static final String API_KEY_SECRET = "***REMOVED***";
    private static final String PASSWORD = "";

    /**
    * createCustomer2 is used to create a customer
    * return ResponseDTO
     * @param pinCustomerPost
    */
    @Override
    public SerialResponse createCustomer2(PinCustomerPost pinCustomerPost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    
    /**
    * charge2 funtion is used to charge the money
     * @param pinChargePost
     * @return 
    */
    @Override
    public SerialResponse charge2(PinChargePost pinChargePost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinChargePost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * createRecipient2 function is used to create a receipt
     * @param pinRecipientPost
     * @return 
    */
    @Override
    public SerialResponse createRecipient2(PinRecipientPost pinRecipientPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinRecipientPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * transfer2 function is used to transfer the money
     * @param pinTransferPost
     * @return 
    */
    @Override
    public SerialResponse transfer2(PinTransferPost pinTransferPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinTransferPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * fetchRecipient function is used to fetch the requested recipient
    */
    @Override
    public JsonObject fetchRecipient(String recipientToken) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .get();

        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return responseJson;
    }

    /**
    * editRecipient2 function is used to edit the information of the recipient
     * @param recipientToken
     * @param pinRecipientPut
     * @return 
    */
    @Override
    public SerialResponse editRecipient2(String recipientToken, PinRecipientPut pinRecipientPut) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .put(Entity.entity(pinRecipientPut, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }
    // stop here. everything below here is deprecated.

    /**
    * createCustomer is used to create a customer
    */
    @Override
    public ValidationResult createCustomer(PinCustomerPost pinCustomerPost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
    /**
    * charge function is used to charge the money
    */
    @Override
    public ValidationResult charge(PinChargePost pinChargePost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinChargePost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
    /**
    * createRecipient function is used to create a recipient of the payment
    */
    @Override
    public ValidationResult createRecipient(PinRecipientPost pinRecipientPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinRecipientPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }

    
    /**
    * transfer function is used to transfer the money
    */
    @Override
    public ValidationResult transfer(PinTransferPost pinTransferPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinTransferPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }

    /**
    * editRecipient function is used to edit the information of the recipient 
    */
    @Override
    public ValidationResult editRecipient(String recipientToken, PinRecipientPut pinRecipientPut) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .put(Entity.entity(pinRecipientPut, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
}
