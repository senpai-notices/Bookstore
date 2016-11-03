package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinCustomerPost implements Serializable {
    private String email;
    private PinCard card;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PinCard getCard() {
        return card;
    }
    
    public void setCard(PinCard card) {
        this.card = card;
    }   
}
