package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinChargePost implements Serializable {

    private String email;
    private String description;
    private String customerToken;
    private String amount;

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true, name = "customer_token")
    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    /**
     *
     * @return
     */
    @XmlElement(required = true)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
