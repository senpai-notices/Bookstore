package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.pin.dto.PinCard;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckoutDTO {
    private List<CheckoutItemDTO> items;
    private PinCard card;
    private double shippingCost;

    @XmlElement
    public List<CheckoutItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemDTO> items) {
        this.items = items;
    }

    @XmlElement
    public PinCard getCard() {
        return card;
    }

    public void setCard(PinCard card) {
        this.card = card;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
}
