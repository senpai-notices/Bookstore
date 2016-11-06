package au.edu.uts.aip.domain.ejb;

/**
 *The needed libraries
 */
import au.edu.uts.aip.domain.auspost.dto.AuspostPostageGet;
import au.edu.uts.aip.domain.auspost.filter.AuspostAuthFilter;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.entity.Suburb;
import au.edu.uts.aip.domain.util.ApiResponseUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * PostalBean is a JavaBean that is used to handle the postage related operations
 * 
 * The functions conclude calculating the postage fee, calculating the parcel height and weight,
 * getting stateName according to postcode and searching suburb details
 * 
 * It has 6 methods:
 * calculatePostageCost(): used to calculate the postage fee
 * calculateParcelHeight(): used to calculate the height of the parcel
 * calculateParcelWeight(): used to calculate the weight of the parcel
 * getStateName(): get the state name based on the postcode
 * searchPostcodes(): Get possible postcodes for a given suburb name
 * searchSuburbDetail(): Get possible suburb detail for a given suburb name
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Stateless
public class PostalBean {

    @PersistenceContext
    private EntityManager em;

    /**
     *the API info
     */
    private static final String BASE_URL = "https://digitalapi.auspost.com.au";
    private static final String API_KEY = "***REMOVED***";
    /**
     * the parcel info
     */
    private static final int BOOK_LENGTH = 25;
    private static final int BOOK_WIDTH = 20;
    private static final int BOOK_HEIGHT = 3;
    private static final int PARCEL_PACKAGING_BUFFER = 7;
    private static final int PARCEL_LENGTH = BOOK_LENGTH + PARCEL_PACKAGING_BUFFER;
    private static final int PARCEL_WIDTH = BOOK_WIDTH + PARCEL_PACKAGING_BUFFER;
    private static final double BOOK_WEIGHT = 0.5;
    private static final double PARCEL_WEIGHT_BUFFER = 0.4;

    private static final JsonObject JSON_STATE_NSW = Json.createObjectBuilder().add("name", "NSW").add("name_long", "New South Wales").build();
    private static final JsonObject JSON_STATE_VIC = Json.createObjectBuilder().add("name", "VIC").add("name_long", "Victoria").build();
    private static final JsonObject JSON_STATE_QLD = Json.createObjectBuilder().add("name", "QLD").add("name_long", "Queensland").build();
    private static final JsonObject JSON_STATE_ACT = Json.createObjectBuilder().add("name", "ACT").add("name_long", "Australian Capital Territory").build();
    private static final JsonObject JSON_STATE_TAS = Json.createObjectBuilder().add("name", "TAS").add("name_long", "Tasmania").build();
    private static final JsonObject JSON_STATE_NT = Json.createObjectBuilder().add("name", "NT").add("name_long", "Northern Territory").build();
    private static final JsonObject JSON_STATE_SA = Json.createObjectBuilder().add("name", "SA").add("name_long", "South Australia").build();
    private static final JsonObject JSON_STATE_WA = Json.createObjectBuilder().add("name", "WA").add("name_long", "Western Australia").build();
    private static final JsonObject JSON_STATE_NOT_FOUND = Json.createObjectBuilder().add("error", "Postcode not found").build();

    private static final JsonObject JSON_SUBURB_NOT_FOUND = Json.createObjectBuilder().add("error", "Suburb not found").build();

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
    public double calculatePostageCost(int quantity, int fromPostcode, int toPostcode, String serviceCode) {

        // Qty cannot exceed 30
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

    private int calculateParcelHeight(int quantity) {
        return (quantity * BOOK_HEIGHT) + PARCEL_PACKAGING_BUFFER;
    }

    private double calculateParcelWeight(int quantity) {
        return (quantity * BOOK_WEIGHT) + PARCEL_WEIGHT_BUFFER;
    }

    /**
     * Get the state that the postcode belongs to. Returns a SerialResponse (JSON body and HTTP
     * status code). The body contains the name of the state.
     *
     * Example 1: input 3000 -> returns SerialResponse containing the strings "VIC" and "Victoria"
     * and also status code 200
     *
     * Example 2: input 0 -> returns SerialResponse containing an error message and status code 404
     *
     * @param postcode The postcode that is queried
     * @return If postcode is found, a SerialResponse containing the name of the state that the
     * postcode is in will be returned with status code 200. Otherwise, return a SerialResponse
     * containing an error message with status 404.
     */
    public SerialResponse getStateName(int postcode) {
        if ((postcode >= 1000 && postcode <= 2599)
                || (postcode >= 2620 && postcode <= 2899)
                || (postcode >= 2921 && postcode <= 2999)) {
            return new SerialResponse(JSON_STATE_NSW, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 200 && postcode <= 299)
                || (postcode >= 2600 && postcode <= 2619)
                || (postcode >= 2900 && postcode <= 2920)) {
            return new SerialResponse(JSON_STATE_ACT, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 3000 && postcode <= 3999)
                || (postcode >= 8000 && postcode <= 8999)) {
            return new SerialResponse(JSON_STATE_VIC, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 4000 && postcode <= 4999)
                || (postcode >= 9000 && postcode <= 9999)) {
            return new SerialResponse(JSON_STATE_QLD, Response.Status.OK.getStatusCode());
        } else if (postcode >= 5000 && postcode <= 5999) {
            return new SerialResponse(JSON_STATE_SA, Response.Status.OK.getStatusCode());
        } else if ((postcode >= 6000 && postcode <= 6797)
                || (postcode >= 6800 && postcode <= 6999)) {
            return new SerialResponse(JSON_STATE_WA, Response.Status.OK.getStatusCode());
        } else if (postcode >= 7000 && postcode <= 7999) {
            return new SerialResponse(JSON_STATE_TAS, Response.Status.OK.getStatusCode());
        } else if (postcode >= 800 && postcode <= 999) {
            return new SerialResponse(JSON_STATE_NT, Response.Status.OK.getStatusCode());
        } else {
            return new SerialResponse(JSON_STATE_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Get possible postcodes for a given suburb name.
     *
     * @param suburb The name of the suburb. It is case-sensitive.
     * @return
     */
    public SerialResponse searchPostcodes(String suburb) {
        // TODO: remove if trimming in frontend.
        suburb = suburb.trim();

        // Build criteria query returning Suburbs      
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Suburb> cq = cb.createQuery(Suburb.class);

        // Set up the query       
        Root<Suburb> root = cq.from(Suburb.class);
        cq.where(cb.like(root.<String>get("name"), cb.parameter(String.class, "param")));
        TypedQuery<Suburb> tq = em.createQuery(cq);
        tq.setParameter("param", "%" + suburb + "%");

        List<Suburb> result = tq.getResultList();

        if (!result.isEmpty()) {
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            for (Suburb s : result) {
                jsonArrayBuilder.add(s.getPostcode());
            }
            JsonObject resultJson = jsonBuilder.add("suburbs", jsonArrayBuilder).build();

            return new SerialResponse(resultJson, Response.Status.OK.getStatusCode());
        }
        return new SerialResponse(JSON_SUBURB_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Get possible details of suburb for a given suburb name.
     *
     * @param suburb The name of the suburb. It is case-sensitive.
     * @return
     */
    public SerialResponse searchSuburbDetail(String suburb) {
        // TODO: remove if trimming in frontend.
        suburb = suburb.trim();

        // Build criteria query returning Suburbs      
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Suburb> cq = cb.createQuery(Suburb.class);

        // Set up the query       
        Root<Suburb> root = cq.from(Suburb.class);
        cq.where(cb.like(root.<String>get("name"), cb.parameter(String.class, "param")));
        TypedQuery<Suburb> tq = em.createQuery(cq);
        tq.setParameter("param", "%" + suburb + "%");

        List<Suburb> result = tq.getResultList();

        if (!result.isEmpty()) {
            JsonObjectBuilder outerJsonBuilder = Json.createObjectBuilder();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            for (Suburb s : result) {
                JsonObjectBuilder innerJsonBuilder = Json.createObjectBuilder()
                        .add("name", s.getName())
                        .add("state_name", s.getStateName())
                        .add("postcode", s.getPostcode());
                jsonArrayBuilder.add(innerJsonBuilder);
            }
            JsonObject resultJson = outerJsonBuilder.add("suburb_details", jsonArrayBuilder).build();

            return new SerialResponse(resultJson, Response.Status.OK.getStatusCode());
        }
        return new SerialResponse(JSON_SUBURB_NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode());
    }
}
