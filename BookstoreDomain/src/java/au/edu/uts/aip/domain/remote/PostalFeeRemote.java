package au.edu.uts.aip.domain.remote;

import javax.ejb.Remote;

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
public interface PostalFeeRemote {

    /**
     * the API info
     */
    static final String BASE_URL = "https://digitalapi.auspost.com.au";
    static final String API_KEY = "***REMOVED***";
    /**
     * the parcel info
     */
    static final int BOOK_LENGTH = 25;
    static final int BOOK_WIDTH = 20;
    static final int BOOK_HEIGHT = 3;
    static final int PARCEL_PACKAGING_BUFFER = 7;
    static final int PARCEL_LENGTH = BOOK_LENGTH + PARCEL_PACKAGING_BUFFER;
    static final int PARCEL_WIDTH = BOOK_WIDTH + PARCEL_PACKAGING_BUFFER;
    static final double BOOK_WEIGHT = 0.5;
    static final double PARCEL_WEIGHT_BUFFER = 0.4;

    /**
     *
     * @param quantity
     * @param fromPostcode
     * @param toPostcode
     * @param serviceCode
     * @return
     */
    double calculatePostageCost(int quantity, int fromPostcode, int toPostcode, String serviceCode);

}
