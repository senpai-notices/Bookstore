package au.edu.uts.aip.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * CheckoutItemDTO is a DTO class that is used to retrieve info for a item on checkout
 *
 * It has properties: saleId: long value, buyQuantity: Integer value
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class CheckoutItemDTO {

    /**
     * the id of the book sale
     */
    private long saleId;
    /**
     * the number of selling books
     */
    private int buyQuantity;

    /**
     * {@link CheckoutItemDTO#saleId}
     *
     * @return saleId
     */
    @XmlElement
    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    /**
     * {@link CheckoutItemDTO#buyQuantity}
     *
     * @return buyQuantity
     */
    @XmlElement
    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
}
