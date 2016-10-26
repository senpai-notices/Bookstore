package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart implements Serializable {

    private Long id;
    private List<CartItem> cartItems;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @OneToMany
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

