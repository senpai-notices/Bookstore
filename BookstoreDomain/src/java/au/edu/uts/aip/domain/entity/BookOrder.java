package au.edu.uts.aip.domain.entity;

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
 * Note: 'Order' is a SQL-reserved keyword.
 */
@Entity
public class BookOrder implements Serializable {

    private Long id;
    private List<BookOrderLine> orderLines;
    private double postageCost;
    private Date orderTimestamp;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany
    public List<BookOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<BookOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public double getPostageCost() {
        return postageCost;
    }

    public void setPostageCost(double postageCost) {
        this.postageCost = postageCost;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
