package au.edu.uts.aip.domain.dto;

/*the needed libraries*/
import au.edu.uts.aip.domain.entity.BookOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * BookOrderDTO is a DTO class that is used to retrieve all the information related to the order of books
 * It is a data transfer object
 * It will call related model class to retrieve info
 *
 * It has properties: id: long value lines:  a list of BookOrderLineDTO postageCost: double value orderTimestamp: String
 * value 
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class BookOrderDTO {
    
    /**
     * the id of the book order
     */
    private long id;
    /**
     * the order list
     */
    private List<BookOrderLineDTO> lines;
    /**
     * the cost of the postage fee
     */
    private double postageCost;
    /**
     * the time of the order
     */
    private String orderTimestamp;
    
    /**
     * The constructor of the BookOrderDTO class without parameters
     */
    public BookOrderDTO(){
        
    }
    
    /**
     * The constructor of the BookOrderDTO class with one parameter
     * @param orderEntity
     */
    public BookOrderDTO(BookOrder orderEntity){
        this.id = orderEntity.getId();
        this.postageCost = orderEntity.getPostageCost();
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        this.orderTimestamp = df.format(orderEntity.getOrderTimestamp());
        this.lines = new ArrayList<>();
        orderEntity.getOrderLines().stream().forEach((orderLineEntity) -> {
            this.lines.add(new BookOrderLineDTO(orderLineEntity));
        });
    }

    /**
    * {@link BookOrderDTO#id}
    * @return id
    */
    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
    * {@link BookOrderDTO#lines}
    * @return lines
    */
    @XmlElement
    public List<BookOrderLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<BookOrderLineDTO> lines) {
        this.lines = lines;
    }

    /**
    * {@link BookOrderDTO#postageCost}
    * @return postageCost
    */
    @XmlElement
    public double getPostageCost() {
        return postageCost;
    }

    public void setPostageCost(double postageCost) {
        this.postageCost = postageCost;
    }

    /**
    * {@link BookOrderDTO#orderTimestamp}
    * @return orderTimestamp
    */
    @XmlElement
    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(String orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
