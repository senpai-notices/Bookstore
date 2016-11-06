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
 * @inheritDoc
 */
@Stateless
public class PaymentBean implements PaymentRemote {

    /**
     * @inheritDoc
     */
    @Override
    public SerialResponse createCustomer(PinCustomerPost pinCustomerPost) {

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
     * @inheritDoc
     */
    @Override
    public SerialResponse charge(PinChargePost pinChargePost) {

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
     * @inheritDoc
     */
    @Override
    public SerialResponse createRecipient(PinRecipientPost pinRecipientPost) {
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
     * @inheritDoc
     */
    @Override
    public SerialResponse transfer(PinTransferPost pinTransferPost) {
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
     * @inheritDoc
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
     * @inheritDoc
     */
    @Override
    public SerialResponse editRecipient(String recipientToken, PinRecipientPut pinRecipientPut) {
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
}
