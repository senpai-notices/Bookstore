package au.edu.uts.aip.domain.entity;

/**
 *The needed libraries
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A data model class to access data in the Bank_account table in the database. 
 * A bank_account object describes information of the bank account of the user
 *
 * BankAccount entity has properties id, name, bsb and number
 * All the attributes of this model class are private attributes and all of them have relative get and set functions in
 * order to access the attributes
 *
 * @author
 */
@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    /**
     * the id of the bank_account record 
     */
    private Long id;
    /**
     * the name of the account user
     */
    private String name;
    /**
     * the bsb code of the bank account of the user
     */
    private String bsb;
    /**
     * the account number of the bank account of the user
     */
    private String number;

    
    /**
     * {@link BankAccount#id}
     * data type: long
     * return a long value
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
     * {@link BankAccount#name}
     * data type: String
     * return a String value
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

      /**
     * {@link BankAccount#bsb}
     * data type: String
     * return a String value
     */
    @Column(name = "bsb")
    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

     /**
     * {@link BankAccount#number}
     * data type: String
     * return a String value
     */
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
