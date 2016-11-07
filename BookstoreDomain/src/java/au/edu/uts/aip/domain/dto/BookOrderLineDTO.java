package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookOrderLine;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * BookOrderLineDTO is a DTO class that is used to retrieve all the information related to a single book sale record
 * It is a data transfer object
 * It will call related model class to retrieve info
 *
 * It has properties: book: BookDTO object, seller: UserDTO object, unitPrice: double value, quantity: Integer value, totalPrice: double value
 * value 
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class BookOrderLineDTO implements Serializable {
    
    /**
     * the BookDTO object, the book that is selling
     */
    private BookDTO book;
    /**
     * the seller of the book
     */
    private UserDTO seller;
    /**
     * the price of a single book
     */
    private double unitPrice;
    /**
     * the quantity of the books
     */
    private int quantity;
    /**
     * the total price of the book
     */
    private double totalPrice;
    
    /**
     * The constructor of the BookOrderLineDTO class without parameters
     */
    public BookOrderLineDTO(){
        
    }
    
    /**
     * The constructor of the BookOrderLineDTO class with one parameter
     * @param orderLineEntity
     */
    public BookOrderLineDTO(BookOrderLine orderLineEntity){
        this.book = new BookDTO(orderLineEntity.getBook());
        this.seller = new UserDTO(orderLineEntity.getSeller());
        this.unitPrice = orderLineEntity.getUnitPrice();
        this.quantity = orderLineEntity.getQuantity();
        this.totalPrice = unitPrice * quantity;
    }

    /**
    * {@link BookOrderLineDTO#book}
    * @return book
    */
    @XmlElement
    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    /**
    * {@link BookOrderLineDTO#seller}
    * @return seller
    */
    @XmlElement
    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    /**
    * {@link BookOrderLineDTO#unitPrice}
    * @return unitPrice
    */
    @XmlElement
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
    * {@link BookOrderLineDTO#quantity}
    * @return quantity
    */
    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
    * {@link BookOrderLineDTO#totalPrice}
    * @return totalPrice
    */
    @XmlElement
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
