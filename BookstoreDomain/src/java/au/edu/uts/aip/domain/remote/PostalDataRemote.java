package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.response.SerialResponse;
import javax.ejb.Remote;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * PostalFeeBean is a JavaBean that is used to handle the postage related operations
 *
 * The functions conclude calculating the postage fee, calculating the parcel height and weight,
 * getting stateName according to postcode and searching suburb details
 *
 * It has 6 methods: calculatePostageCost(): used to calculate the postage fee
 * calculateParcelHeight(): used to calculate the height of the parcel calculateParcelWeight(): used
 * to calculate the weight of the parcel getStateName(): get the state name based on the postcode
 * searchPostcodes(): Get possible postcodes for a given suburb name searchSuburbDetail(): Get
 * possible suburb detail for a given suburb name
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface PostalDataRemote {

    /**
     *
     */
    static final JsonObject JSON_STATE_NSW = Json.createObjectBuilder().add("name", "NSW").add("name_long", "New South Wales").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_VIC = Json.createObjectBuilder().add("name", "VIC").add("name_long", "Victoria").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_QLD = Json.createObjectBuilder().add("name", "QLD").add("name_long", "Queensland").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_ACT = Json.createObjectBuilder().add("name", "ACT").add("name_long", "Australian Capital Territory").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_TAS = Json.createObjectBuilder().add("name", "TAS").add("name_long", "Tasmania").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_NT = Json.createObjectBuilder().add("name", "NT").add("name_long", "Northern Territory").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_SA = Json.createObjectBuilder().add("name", "SA").add("name_long", "South Australia").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_WA = Json.createObjectBuilder().add("name", "WA").add("name_long", "Western Australia").build();

    /**
     *
     */
    static final JsonObject JSON_STATE_NOT_FOUND = Json.createObjectBuilder().add("error", "Postcode not found").build();

    /**
     *
     */
    static final JsonObject JSON_SUBURB_NOT_FOUND = Json.createObjectBuilder().add("error", "Suburb not found").build();

    /**
     * Get the state that the postcode belongs to. Returns a SerialResponse (JSON body and HTTP
     * status code). The body contains the name of the state, if it exists.
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
    SerialResponse getStateName(int postcode);

    /**
     * Get possible postcodes for a given suburb name.
     *
     * @param suburb The name of the suburb. It is case-sensitive.
     * @return
     */
    SerialResponse searchPostcodes(String suburb);

    /**
     * Get possible details of suburb for a given suburb name.
     *
     * @param suburb The name of the suburb. It is case-sensitive.
     * @return
     */
    SerialResponse searchSuburbDetail(String suburb);

}
