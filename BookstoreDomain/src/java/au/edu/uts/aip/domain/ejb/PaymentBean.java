package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.pin.dto.PinCardPost;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.pin.filter.BasicAuthFilter;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class PaymentBean implements PaymentRemote {

    private static final String BASE_URL = "https://test-api.pin.net.au/1";
    private static final String API_KEY_SECRET = "***REMOVED***";
    private static final String PASSWORD = "";

    @Override
    public Response createCard(PinCardPost pinCardPost) {

        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));

        Response response = client.target(BASE_URL + "/cards")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCardPost, MediaType.APPLICATION_JSON_TYPE));

        client.close();
        return response;
    }

    @Override
    public Response createCustomer(PinCustomerPost pinCustomerPost) {

        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));

        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerPost, MediaType.APPLICATION_JSON_TYPE));

        client.close();
        return response;
    }

    @Override
    public Response charge(PinChargePost pinChargePost) {

        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));

        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinChargePost, MediaType.APPLICATION_JSON_TYPE));

        client.close();
        return response;
    }

    @Override
    public Response createRecipient(PinRecipientPost pinRecipientPost) {
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));

        Response response = client.target(BASE_URL + "/recipients")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinRecipientPost, MediaType.APPLICATION_JSON_TYPE));

        client.close();
        return response;
    }

    @Override
    public Response transfer(PinTransferPost pinTransferPost) {
        Client client = ClientBuilder.newClient()
                .register(new BasicAuthFilter(API_KEY_SECRET, PASSWORD));

        Response response = client.target(BASE_URL + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinTransferPost, MediaType.APPLICATION_JSON_TYPE));

        client.close();
        return response;
    }
}
