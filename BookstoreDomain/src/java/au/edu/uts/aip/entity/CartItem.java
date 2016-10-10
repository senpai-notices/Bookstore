package au.edu.uts.aip.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartItem implements Serializable {
    
    private Long id;
    private Book book;
    private int quantity;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
