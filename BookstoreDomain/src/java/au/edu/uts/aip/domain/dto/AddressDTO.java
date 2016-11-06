package au.edu.uts.aip.domain.dto;

/**
 *The needed libraries
 */
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A AddressDTO is a DTO class that is used to access all the information that is related to a address
 * It is a data transfer object for retrieving the address information
 * describes the detail of the address of the user
 * It has properties: addressLine1, addressLine2, addressCity, addressCountry, addressPostcode and addressState
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class AddressDTO {
    
    /**
     * the first line of the address
     */
    private String addressLine1;
    /**
     * the second line of the address
     */
    private String addressLine2;
    /**
     * the city of the address
     */
    private String addressCity;
    /**
     * the country of the address
     */
    private String addressCountry;
    /**
     * the postcode of the address
     */
    private int addressPostCode;
    /**
     * the state of the address
     */
    private String addressState;

    
    /**
    * {@link AddressDTO#addressLine1}
    * data type: String
    * return a String value
    */
    @XmlElement(name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
    * {@link AddressDTO#addressLine2}
    * data type: String
    * return a String value
    */
    @XmlElement(name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
    * {@link AddressDTO#addressCity}
    * data type: String
    * return a String value
    */
    @XmlElement(name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
    
    /**
    * {@link AddressDTO#addressCountry}
    * data type: String
    * return a String value
    */
    @XmlElement(name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    /**
    * {@link AddressDTO#addressPostcode}
    * data type: Integer
    * return an int value
    */
    @XmlElement(name = "address_postcode")
    public int getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(int addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    /**
    * {@link AddressDTO#addressState}
    * data type: String
    * return a String value
    */
    @XmlElement(name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
}
