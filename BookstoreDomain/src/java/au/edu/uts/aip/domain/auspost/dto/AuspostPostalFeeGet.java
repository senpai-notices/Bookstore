package au.edu.uts.aip.domain.auspost.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AuspostPostalFeeGet class is a DTO class The class is used to retrieve the postage related
 * information
 *
 * It has properties: fromPostagecode, topPostcode, length, width, height, weight and service code
 * All the attributes are private and need get and set methods to access them
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class AuspostPostalFeeGet {

    /**
     * The origin postcode. Should be four digits. Required field. E.g. 2000, 0800.
     */
    private String fromPostcode;
    /**
     * The destination postcode. Should be four digits. Required field. E.g. 2000, 0800.
     */
    private String toPostcode;
    /**
     * The length of the parcel in centimetres. Required field. E.g. 34
     */
    private int length;
    /**
     * The width of the parcel in centimetres. Required field. E.g. 42
     */
    private int width;
    /**
     * The height of the parcel in centimetres. Required field. E.g. 33
     */
    private int height;
    /**
     * The weight of the parcel in kilograms. Current usage is to 1 decimal-place. Required field.
     * E.g. 12.5
     */
    private double weight;
    /**
     * The service code of the postage which defines the postage speed - regular or express.
     * Required field. Value must be either "AUS_PARCEL_REGULAR" or "AUS_PARCEL_EXPRESS".
     */
    private String serviceCode;

    /**
     * {@link AuspostPostalFeeGet#fromPostcode}
     *
     * @return The origin postcode.
     */
    @XmlElement(required = true, name = "from_postcode")
    public String getFromPostcode() {
        return fromPostcode;
    }

    public void setFromPostcode(String fromPostcode) {
        this.fromPostcode = fromPostcode;
    }

    /**
     * {@link AuspostPostalFeeGet#toPostcode}
     *
     * @return The destination postcode.
     */
    @XmlElement(required = true, name = "to_postcode")
    public String getToPostcode() {
        return toPostcode;
    }

    public void setToPostcode(String toPostcode) {
        this.toPostcode = toPostcode;
    }

    /**
     * {@link AuspostPostalFeeGet#length}
     *
     * @return The length of the parcel in centimetres.
     */
    @XmlElement(required = true)
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * {@link AuspostPostalFeeGet#width}
     *
     * @return The width of the parcel in centimetres.
     */
    @XmlElement(required = true)
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * {@link AuspostPostalFeeGet#height}
     *
     * @return The height of the parcel in centimetres.
     */
    @XmlElement(required = true)
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * {@link AuspostPostalFeeGet#weight}
     *
     * @return The weight of the parcel in kilograms.
     */
    @XmlElement(required = true)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * {@link AuspostPostalFeeGet#serviceCode}
     *
     * @return The serviceCode of either regular or express postage service.
     */
    @XmlElement(required = true, name = "service_code")
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
