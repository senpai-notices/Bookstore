package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.auspost.dto.AuspostPostageGet;
import au.edu.uts.aip.domain.auspost.filter.AuspostAuthFilter;
import au.edu.uts.aip.domain.pin.utility.PinResponseUtility;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class PostageBean {
    private static final String BASE_URL = "https://digitalapi.auspost.com.au";
    private static final String API_KEY = "***REMOVED***";
    
    // return a double.
        public JsonObject calculatePostageCostJson(AuspostPostageGet auspostPostageGet) {

        Client client = ClientBuilder.newClient()
                .register(new AuspostAuthFilter(API_KEY));
        // TODO: add logging filter?

        Response response = client.target(BASE_URL + "/postage/parcel/domestic/calculate.json")
                .queryParam("from_postcode", auspostPostageGet.getFromPostcode())
                .queryParam("to_postcode", auspostPostageGet.getToPostcode())
                .queryParam("length", auspostPostageGet.getLength())
                .queryParam("width", auspostPostageGet.getWidth())
                .queryParam("height", auspostPostageGet.getHeight())
                .queryParam("weight", auspostPostageGet.getWeight())
                .queryParam("service_code", auspostPostageGet.getServiceCode())
                .request(MediaType.APPLICATION_JSON)
                .get();

        int statusCode = response.getStatus();
        JsonObject responseJson = PinResponseUtility.toJson(response.readEntity(String.class));
        client.close();

        System.out.println(responseJson.toString());
        //return PinResponseUtility.validate(statusCode, responseJson);
        return responseJson;
    }
}
