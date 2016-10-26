package au.edu.uts.aip.service.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinCharge implements Serializable {
    private String email;
    private String description;
    private String customerToken;
    private String amount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    @XmlElement(name="customer_token")
    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
}
