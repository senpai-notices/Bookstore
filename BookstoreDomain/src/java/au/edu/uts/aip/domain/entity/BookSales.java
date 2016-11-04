package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Book_sales", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"book", "seller", "price"})
})
@NamedQueries({
    @NamedQuery(name="BookSales.findSales", query="SELECT bs FROM BookSales bs where bs.book=:book AND bs.seller=:seller")
})
public class BookSales implements Serializable {
    
    private long salesId;
    private Book book;
    private User seller;
    
    @NotNull
    @Min(value = (long) 0.01, message = "Please set a price for the book")
    private double price;
    
    @NotNull
    @Size(min = 3, message = "Please enter book condition with at least 3 characters")
    private String condition;
    
    @NotNull
    @Min(value = 1, message =  "Please enter a positive value for quantity")
    private int quantity;

    @Id
    @GeneratedValue
    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }
    
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @JoinColumn(name = "price_id", referencedColumnName = "id")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
