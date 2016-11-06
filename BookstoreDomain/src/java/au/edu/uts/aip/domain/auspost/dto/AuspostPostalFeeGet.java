package au.edu.uts.aip.domain.auspost.dto;

/**
 *The needed libraries
 */
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AuspostPostalFeeGet class is a DTO class 
 The class is used to retrieve the postage related information

 It has properties: fromPostagecode, topPostcode, length, width, height, weight and service code
 All the attributes are private and need get and set methods to access them
 * 
 * @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class AuspostPostalFeeGet {

    /**
     * the postcode
     */
    private String fromPostcode;
    private String toPostcode;
    /**
     * the length of the postage
     */
    private int length;
    /**
     * the width of the postage
     */
    private int width;
    /**
     * the height of the postage
     */
    private int height;
    /**
     * the weight of the postage
     */
    private double weight;
    /**
     * the service code of the postage
     */
    private String serviceCode;
    private int quantity;

    /**
    * {@link AuspostPostalFeeGet#fromPostcode}
    * data type: String
    * return a String value
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
    * data type: String
    * return a String value
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
    * data type: Integer
    * return an int value
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
    * data type: Integer
    * return an int value
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
    * data type: Integer
    * return an int value
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
    * data type: Double
    * return a double value
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
    * data type: String
    * return a String value
    */
    @XmlElement(required = true, name = "service_code")
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
