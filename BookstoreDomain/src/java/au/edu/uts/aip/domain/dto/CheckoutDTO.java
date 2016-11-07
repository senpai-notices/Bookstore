package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.pin.dto.PinCard;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CheckoutDTO is a DTO class that is used to retrieve all the information needed for checkout of the purchase
 *
 * It has properties: items: a list of the CheckoutItemDTO, card: PinCard object, shippingCost: double value 
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class CheckoutDTO {
    
    /**
     * the list of the checkout items
     */
    private List<CheckoutItemDTO> items;
    /**
     * the card info for payment
     */
    private PinCard card;
    /**
     * the fee of the shipping
     */
    private double shippingCost;

   /**
    * {@link CheckoutDTO#items}
    * @return items
    */
    @XmlElement
    public List<CheckoutItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemDTO> items) {
        this.items = items;
    }

    /**
    * {@link CheckoutDTO#card}
    * @return card
    */
    @XmlElement
    public PinCard getCard() {
        return card;
    }

    public void setCard(PinCard card) {
        this.card = card;
    }

    /**
    * {@link CheckoutDTO#shippingCost}
    * @return shippingCost
    */
    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
}
