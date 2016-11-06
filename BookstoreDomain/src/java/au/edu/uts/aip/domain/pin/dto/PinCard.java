package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author x
 */
@XmlRootElement(name = "card")
public class PinCard implements Serializable {
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cvc;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String addressCity;
    private String addressPostcode;
    private String addressState;
    private String addressCountry;

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "expiry_month")
    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "expiry_year")
    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = false, name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = false, name = "address_postcode")
    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = false, name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
    
    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }
}
