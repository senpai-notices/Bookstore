package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinCustomerPost class is used to access the card info of the user
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinCustomerPost implements Serializable {

    /**
    * the email of the user
    */
    private String email;
    /**
    * the card of the user
    */
    private PinCard card;

    /**
    * {@link PinCustomerPost#email}
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
    * {@link PinCustomerPost#card}
    * @return card
    */
    @XmlElement(required = true)
    public PinCard getCard() {
        return card;
    }

    public void setCard(PinCard card) {
        this.card = card;
    }
}
