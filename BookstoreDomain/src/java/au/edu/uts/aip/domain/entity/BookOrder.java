package au.edu.uts.aip.domain.entity;

/**
 * the necessary library
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A Data model class to access data in the BookOrder table. BookOrder describes the order item list
 * It has properties Id, orderLines, postageCost and orderTimestamp All the attributes of this model
 * class are private attributes and all of them have relative get and set functions in order to
 * access the attributes Note: 'Order' is a reserved keyword and is therefore not used.
 */
@Entity
public class BookOrder implements Serializable {

    /**
     * id is the book order record id
     */
    private Long id;
    /**
     * orderLines is the list of the orders
     */
    private List<BookOrderLine> orderLines;
    /**
     * the cost of the postage delivery
     */
    private double postageCost;
    /**
     * the order time
     */
    private Date orderTimestamp;

    /**
     * {@link BookOrder#id}
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@link BookOrder#orderLines}
     */
    @OneToMany
    public List<BookOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<BookOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    /**
     * {@link BookOrder#postageCost}
     */
    public double getPostageCost() {
        return postageCost;
    }

    public void setPostageCost(double postageCost) {
        this.postageCost = postageCost;
    }

    /**
     * {@link BookOrder#orderTimestamp}
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
