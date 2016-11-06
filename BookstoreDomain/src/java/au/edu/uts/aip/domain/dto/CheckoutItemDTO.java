package au.edu.uts.aip.domain.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckoutItemDTO {
    private long saleId;
    private int buyQuantity;

    @XmlElement
    public long getSaleId() {
        return saleId;
    }
    
    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    @XmlElement
    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
}
