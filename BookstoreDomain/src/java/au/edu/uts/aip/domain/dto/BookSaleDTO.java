package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookSales;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookSaleDTO implements Serializable {

    private long id;
    private String sellerId;
    private long bookId;
    private String bookCondition;
    private double price;
    private int quantity;
    private BookDTO book;
    private UserDTO seller;
    
    public BookSaleDTO(){
        
    }
    
    public BookSaleDTO(BookSales bookSaleEntity){
        this.id = bookSaleEntity.getSalesId();
        this.sellerId = bookSaleEntity.getSeller().getUsername();
        this.seller = new UserDTO(bookSaleEntity.getSeller());
        this.bookId = bookSaleEntity.getBook().getId();
        this.book = new BookDTO(bookSaleEntity.getBook());
        this.bookCondition = bookSaleEntity.getCondition();
        this.price = bookSaleEntity.getPrice();
        this.quantity = bookSaleEntity.getQuantity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @XmlElement
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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
    
    
}
