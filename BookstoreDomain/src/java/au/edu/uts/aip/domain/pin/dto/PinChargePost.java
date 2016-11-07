package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinChargePost class is used to post the charge to the user
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinChargePost implements Serializable {

    /**
     * the email of the user
     */
    private String email;
    /**
     * the description on email
     */
    private String description;
    /**
     * the token of the customer
     */
    private String customerToken;
    /**
     * the amount of the charge
     */
    private String amount;

    /**
    * {@link PinChargePost#email}
    * @return email
    */
    @XmlElement(required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * {@link PinChargePost#description}
    * @return description
    */
    @XmlElement(required = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
    * {@link PinChargePost#customerToken}
    * @return customerToken
    */
    @XmlElement(required = true, name = "customer_token")
    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    /**
    * {@link PinChargePost#amount}
    * @return amount
    */
    @XmlElement(required = true)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
