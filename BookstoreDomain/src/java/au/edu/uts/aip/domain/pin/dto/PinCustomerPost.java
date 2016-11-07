package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinCustomerPost implements Serializable {

    private String email;
    private PinCard card;

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
    public PinCard getCard() {
        return card;
    }

    public void setCard(PinCard card) {
        this.card = card;
    }
}
