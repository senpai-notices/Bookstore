package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.BookOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookOrderDTO {
    private long id;
    private List<BookOrderLineDTO> lines;
    private double postageCost;
    private String orderTimestamp;
    
    public BookOrderDTO(){
        
    }
    
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

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public List<BookOrderLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<BookOrderLineDTO> lines) {
        this.lines = lines;
    }

    @XmlElement
    public double getPostageCost() {
        return postageCost;
    }

    public void setPostageCost(double postageCost) {
        this.postageCost = postageCost;
    }

    @XmlElement
    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(String orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
