package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinTransferPost class is used to transfer the money 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinTransferPost implements Serializable {

    /**
    * the description of the transfer
    */
    private String description;
    /**
    * the amount of the transfer
    */
    private String amount;
    /**
    * the currency of the transfer
    */
    private String currency;
    /**
    * the recipient of the transfer
    */
    private String recipient;

    /**
    * {@link PinTransferPost#description}
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
    * {@link PinTransferPost#amount}
    * @return amount
    */
    @XmlElement(required = true)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
    * {@link PinTransferPost#currency}
    * @return currency
    */
    @XmlElement(required = true)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
    * {@link PinTransferPost#recipient}
    * @return recipient
    */
    @XmlElement(required = true)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
