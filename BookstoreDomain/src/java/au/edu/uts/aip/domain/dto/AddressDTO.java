package au.edu.uts.aip.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressDTO {
    private String addressLine1;
    private String addressLine2;
    private String addressCity;
    private String addressCountry;
    private int addressPostCode;
    private String addressState;

    @XmlElement(name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @XmlElement(name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @XmlElement(name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
    
    @XmlElement(name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    @XmlElement(name = "address_postcode")
    public int getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(int addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    @XmlElement(name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
}
