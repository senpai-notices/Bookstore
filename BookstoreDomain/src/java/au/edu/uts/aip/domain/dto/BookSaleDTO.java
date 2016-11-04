package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookSales;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookSaleDTO implements Serializable {

    private String sellerName;
    private long bookId;
    private String bookCondition;
    private double price;
    private int quantity;

    public BookSaleDTO(){
        
    }
    
    public BookSaleDTO(BookSales bookSaleEntity){
        this.sellerName = bookSaleEntity.getSeller().getUsername();
        this.bookId = bookSaleEntity.getBook().getId();
        this.bookCondition = bookSaleEntity.getCondition();
        this.price = bookSaleEntity.getPrice();
        this.quantity = bookSaleEntity.getQuantity();
    }
    
    @XmlElement
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @XmlElement
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @XmlElement
    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    @XmlElement
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
