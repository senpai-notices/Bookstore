package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinCard class is model class that is used to access the card related information
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement(name = "card")
public class PinCard implements Serializable {
    /**
     * the number of the card
     */
    private String number;
    /**
     * the expiry month of the card
     */
    private String expiryMonth;
    /**
     * the expiry year of the card
     */
    private String expiryYear;
    /**
     * the cvc of the card
     */
    private String cvc;
    /**
     * the name on the card
     */
    private String name;
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
     * the postcode of the address
     */
    private String addressPostcode;
    /**
     * the state of the address
     */
    private String addressState;
    /**
     * the country of the address
     */
    private String addressCountry;

    /**
    * {@link PinCard#number}
    * @return number
    */
    @XmlElement(required = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
    * {@link PinCard#expiryMonth}
    * @return expiryMonth
    */
    @XmlElement(required = true, name = "expiry_month")
    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
    * {@link PinCard#expiryYear}
    * @return expiryYear
    */
    @XmlElement(required = true, name = "expiry_year")
    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
    * {@link PinCard#cvc}
    * @return cvc
    */
    @XmlElement(required = true)
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    /**
    * {@link PinCard#name}
    * @return name
    */
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    * {@link PinCard#addressLine1}
    * @return addressLine1
    */
    @XmlElement(required = true, name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
    * {@link PinCard#addressLine2}
    * @return addressLine2
    */
    @XmlElement(required = false, name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    /**
    * {@link PinCard#addressCity}
    * @return addressCity
    */
    @XmlElement(required = true, name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    /**
    * {@link PinCard#addressPostcode}
    * @return addressPostcode
    */
    @XmlElement(required = false, name = "address_postcode")
    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    /**
    * {@link PinCard#addressState}
    * @return addressState
    */
    @XmlElement(required = false, name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
    
    /**
    * {@link PinCard#addressCountry}
    * @return addressCountry
    */
    @XmlElement(required = true, name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }
}
