package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "address")
public class Address implements Serializable {

    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String addressCity;
    private int addressPostcode;
    private String addressState;
    private String addressCountry;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Column(name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Min(value = 800, message = "Please enter a valid postcode")
    @Max(value = 9999, message = "Please enter a valid postcode")
    @Column(name = "address_postcode")
    public int getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(int addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    @Column(name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    @Column(name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

}
