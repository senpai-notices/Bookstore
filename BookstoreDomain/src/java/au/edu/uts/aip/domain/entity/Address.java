package au.edu.uts.aip.domain.entity;

/**
 *The needed libraries
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * A data entity class to access data in the Address table. 
 * A Address object describes the address detail of the buyer
 *
 * Address entity has properties id, addressLine1, addressLine2, addressCity, addressPostcode, addressState and addressCountry. 
 * All the attributes of this model class are private attributes and all of them have relative get and set functions in
 * order to access the attributes
 *
 * @author
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {
     
    /**
     * the address record id
     */
    private Long id;
    /**
     * the address of the buyer line 1
     */
    private String addressLine1;
    /**
     * the address of the buyer line 2
     */
    private String addressLine2;
    /**
     * the city in the address
     */
    private String addressCity;
    /**
     * the postcode of the address
     */
    private int addressPostcode;
    /**
     * the state of the address
     */
    private String addressState;
    /**
     * the country of the buyer
     */
    private String addressCountry;

    
    /**
     * {@link Address#id}
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@link Address#addressLine1}
     */
    @Column(name = "address_line1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    
    /**
     * {@link Address#addressLine2}
     */
    @Column(name = "address_line2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * {@link Address#addressCity}
     */
    @Column(name = "address_city")
    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    
    /**
     * {@link Address#addressPostcode}
     * the size must be >=800 and <=9999
     */
    @Min(value = 800, message = "Please enter a valid postcode")
    @Max(value = 9999, message = "Please enter a valid postcode")
    @Column(name = "address_postcode")
    public int getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(int addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    /**
     * {@link Address#addressState}
     */
    @Column(name = "address_state")
    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    /**
     * {@link Address#addressCountry}
     */
    @Column(name = "address_country")
    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    @Override
    public String toString() {
        String address2 = this.addressLine2 == null ? "" : this.addressLine2;
        return this.addressLine1 + ", " + address2 + ", "
                + this.addressCity + ", " + this.addressState + ", "
                + this.addressCountry + ", " + this.addressPostcode;
    } 
}
