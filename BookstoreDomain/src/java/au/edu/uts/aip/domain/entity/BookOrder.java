package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Note: 'Order' is a SQL-reserved keyword.
 * @author tanman
 */
@Entity
public class BookOrder implements Serializable {

    private Long id;
    private List<BookOrderLine> orderLines;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @OneToMany
    public List<BookOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderLines(List<BookOrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
