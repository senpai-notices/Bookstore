package au.edu.uts.aip.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book implements Serializable {

    private Long id;
    private String name;
    private String title;
    private double price;

    @Id
    @GeneratedValue
    public Long getId() {
        return this.id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    @Column(nullable = false)
    public String getTitle() {
        return this.title;
    }

    @Column(nullable = false)
    public double getPrice() {
        return this.price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
}
