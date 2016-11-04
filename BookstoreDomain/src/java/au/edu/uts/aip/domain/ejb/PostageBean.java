package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.auspost.dto.AuspostPostageGet;
import au.edu.uts.aip.domain.auspost.filter.AuspostAuthFilter;
import au.edu.uts.aip.domain.utility.ApiResponseUtil;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class PostageBean {

    private static final String BASE_URL = "https://digitalapi.auspost.com.au";
    private static final String API_KEY = "***REMOVED***";
    private static final int BOOK_LENGTH = 25;
    private static final int BOOK_WIDTH = 20;
    private static final int BOOK_HEIGHT = 3;
    private static final int PARCEL_PACKAGING_BUFFER = 7;
    private static final int PARCEL_LENGTH = BOOK_LENGTH + PARCEL_PACKAGING_BUFFER;
    private static final int PARCEL_WIDTH = BOOK_WIDTH + PARCEL_PACKAGING_BUFFER;
    private static final double BOOK_WEIGHT = 0.5;
    private static final double PARCEL_WEIGHT_BUFFER = 0.4;

    @Deprecated
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
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        System.out.println(responseJson.toString());
        //return PinResponseUtility.validate(statusCode, responseJson);
        return responseJson;
    }

    // TODO: find a way to get the seller's postcode and the buyer's postcode.
    public double calculatePostageCost(int quantity, String fromPostcode, String toPostcode, String serviceCode) {

        int parcelHeight = calculateParcelHeight(quantity);
        double parcelWeight = calculateParcelWeight(quantity);

        Client client = ClientBuilder.newClient()
                .register(new AuspostAuthFilter(API_KEY));

        String response = client.target(BASE_URL + "/postage/parcel/domestic/calculate.json")
                .queryParam("from_postcode", fromPostcode)
                .queryParam("to_postcode", toPostcode)
                .queryParam("length", PARCEL_LENGTH)
                .queryParam("width", PARCEL_WIDTH)
                .queryParam("height", parcelHeight)
                .queryParam("weight", parcelWeight)
                .queryParam("service_code", serviceCode)
                .request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(String.class);
        client.close();
        JsonObject responseJson = ApiResponseUtil.toJson(response);
        
        if (responseJson.containsKey("postage_result")) {
            String totalPostageCostString = responseJson.getJsonObject("postage_result").getString("total_cost");
            double totalPostageCost = Double.parseDouble(totalPostageCostString);
            System.out.println(totalPostageCost);
            return totalPostageCost;
        } else { 
            return -1.0;
        }
    }

    private int calculateParcelHeight(int quantity) {
        return (quantity * BOOK_HEIGHT) + PARCEL_PACKAGING_BUFFER;
    }

    private double calculateParcelWeight(int quantity) {
        return (quantity * BOOK_WEIGHT) + PARCEL_WEIGHT_BUFFER;
    }
}
