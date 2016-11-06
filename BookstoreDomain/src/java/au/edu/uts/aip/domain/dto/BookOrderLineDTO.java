package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookOrderLine;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookOrderLineDTO implements Serializable {
    private BookDTO book;
    private UserDTO seller;
    private double unitPrice;
    private int quantity;
    private double totalPrice;
    
    public BookOrderLineDTO(){
        
    }
    
    public BookOrderLineDTO(BookOrderLine orderLineEntity){
        this.book = new BookDTO(orderLineEntity.getBook());
        this.seller = new UserDTO(orderLineEntity.getSeller());
        this.unitPrice = orderLineEntity.getUnitPrice();
        this.quantity = orderLineEntity.getQuantity();
        this.totalPrice = unitPrice * quantity;
    }

    @XmlElement
    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    @XmlElement
    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    @XmlElement
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
