package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.remote.PostalFeeRemote;
import au.edu.uts.aip.domain.auspost.filter.AuspostAuthFilter;
import au.edu.uts.aip.domain.util.ApiResponseUtil;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * @inheritDoc
 */
@Stateless
public class PostalFeeBean implements PostalFeeRemote {

    @PersistenceContext
    private EntityManager em;

    /**
     * @inheritDoc
     */
    @Override
    public double calculatePostageCost(int quantity, int fromPostcode, int toPostcode, String serviceCode) {

        int parcelHeight = calculateParcelHeight(quantity);
        double parcelWeight = calculateParcelWeight(quantity);

        Client client = ClientBuilder.newClient()
                .register(new AuspostAuthFilter(API_KEY));

        String response = client.target(BASE_URL + "/postage/parcel/domestic/calculate.json")
                .queryParam("from_postcode", String.format("%04d", fromPostcode))
                .queryParam("to_postcode", String.format("%04d", toPostcode))
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
            System.out.println("error");
            return -1.0;
        }
    }

    /**
     * Get parcel height based on the quantity of books
     *
     * @param quantity
     * @return
     */
    private int calculateParcelHeight(int quantity) {
        return (quantity * BOOK_HEIGHT) + PARCEL_PACKAGING_BUFFER;
    }

    /**
     * Get parcel weight based on the quantity of books
     *
     * @param quantity
     * @return
     */
    private double calculateParcelWeight(int quantity) {
        return (quantity * BOOK_WEIGHT) + PARCEL_WEIGHT_BUFFER;
    }
}
