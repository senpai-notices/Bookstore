package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookSale;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * BookSaleDTO is a DTO class that is used to retrieve all the information that is related to the
 * book sale record It is a data transfer object for retrieving the sale information of the book
 * describes the detail of the sale It has properties: id, sellerId, bookCondition, price, quantity,
 * book and seller
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class BookSaleDTO implements Serializable {

    /**
     * the id of the sale record
     */
    private long id;
    /**
     * the id of the seller user
     */
    private String sellerId;
    /**
     * the id of the book that is selling
     */
    private long bookId;
    /**
     * the condition of the book that is selling e.g. "Used" or "New"
     */
    private String bookCondition;
    /**
     * the price of the selling book
     */
    private double price;
    /**
     * the number of the books that are selling in the sale ad
     */
    private int quantity;
    /**
     * the information of the selling book it is a BookDTO object the object are used to retrieve
     * all the book info
     */
    private BookDTO book;
    /**
     * the information of seller user it is a UserDTO object the object are used to retrieve all the
     * related user info
     */
    private UserDTO seller;

    /**
     * Default constructor
     */
    public BookSaleDTO() {

    }

    /**
     * constructor of the BookSaleDTO class with parameters the parameter passed to the constructor
 is a BookSale object
     *
     * @param bookSaleEntity
     */
    public BookSaleDTO(BookSale bookSaleEntity) {
        this.id = bookSaleEntity.getSalesId();
        this.sellerId = bookSaleEntity.getSeller().getUsername();
        this.seller = new UserDTO(bookSaleEntity.getSeller());
        this.bookId = bookSaleEntity.getBook().getId();
        this.book = new BookDTO(bookSaleEntity.getBook());
        this.bookCondition = bookSaleEntity.getCondition();
        this.price = bookSaleEntity.getPrice();
        this.quantity = bookSaleEntity.getQuantity();
    }

    /**
     * {@link BookSaleDTO#id} data type: long return a long value
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * {@link BookSaleDTO#sellerId}
     *
     * @return sellerId
     */
    @XmlElement
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * {@link BookSaleDTO#bookId} data type: long return a long value
     *
     * @return bookId
     */
    @XmlElement
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * {@link BookSaleDTO#bookCondition}
     *
     * @return bookCondition
     */
    @XmlElement
    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    /**
     * {@link BookSaleDTO#price} data type: double return a double value
     *
     * @return price
     */
    @XmlElement
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * {@link BookSaleDTO#quantity} data type: Integer return an int value
     *
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
     * {@link BookSaleDTO#book} data type: BookDTO return a BookDTO object
     *
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
     * {@link BookSaleDTO#seller} data type: UserDTO return a UserDTO object
     *
     * @return seller
     */
    @XmlElement
    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

}
