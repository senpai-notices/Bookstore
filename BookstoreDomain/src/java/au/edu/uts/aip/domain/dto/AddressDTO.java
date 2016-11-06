package au.edu.uts.aip.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A AddressDTO is a DTO class that is used to access all the information that is related to a
 * address It is a data transfer object for retrieving the address information describes the detail
 * of the address of the user It has properties: addressLine1, addressLine2, addressCity,
 * addressCountry, addressPostcode and addressState
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class AddressDTO {

    /**
     * The first line of the user's address. Required field. Format: [Street number] [Street name]
     * [Street type] E.g.: 123 Fake St
     */
    private String addressLine1;
    /**
     * The second line of the user's address. Optional field. E.g. Suite 34
     */
    private String addressLine2;
    /**
     * The suburb of the address. Required field E.g. North Sydney
     */
    private String addressCity;
    /**
     * The country of the address. Always equal to Australia. Required field.
     */
    private String addressCountry;
    /**
     * The postcode of the address. It must be within valid ranges, defined in
     * PostalDataBean.getStateName(). Required field.
     */
    private int addressPostCode;
    /**
     * The state of the address. Required field. E.g. NSW, VIC, QLD.
     */
    private String addressState;

    /**
     * {@link AddressDTO#addressLine1}
     * @return addressLine1
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
     * @return addressLine2
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
     * @return addressCity
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
     * @return addressCountry
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
     * @return addressPostCode
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
     * @return addressState
     */
    @XmlElement(name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
}
