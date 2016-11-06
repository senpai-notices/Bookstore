package au.edu.uts.aip.domain.entity;

/**
 *The needed libraries
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Suburb is an Entity that is used to access the data in Suburb table in the database
 * It is a model class
 * 
 * It has properties: 
 * id: Long value
 * name: String value
 * stateName: String value
 * postcode: Integer value
 * The attributes are not null and accessed by get and set methods
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Entity
@Table(name = "suburb")
public class Suburb implements Serializable {
    /**
     * the id of the suburb record
     */
    private Long id;
    /**
     * the name of the suburb
     */
    private String name;
    /**
     * the state name of the suburb
     */
    private String stateName;
    /**
     * the postcode of the suburb
     */
    private int postcode;

    /**
    * {@link Suburb#id}
    * data type: Long
    * return an long Value
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
    * {@link Suburb#name}
    * data type: String
    * return an String Value
    */
    @Column(nullable = false, name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    * {@link Suburb#stateName}
    * data type: String
    * return an String Value
    */
    @Column(nullable = false, name = "state_name")
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
    * {@link Suburb#postcode}
    * data type: Integer
    * return an int Value
    */
    @Column(nullable = false, name = "postcode")
    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

}
