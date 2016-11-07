package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class is used to access bank account info in bank_account table in the database
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement(name = "bank_account")
public class PinBankAccount implements Serializable {

    /**
     * the name of the bank account holder
     */
    private String name;
    /**
     * the bsb number of the bank account
     */
    private String bsb;
    /**
     * the account number
     */
    private String number;

    /**
    * {@link PinBankAccount#name}
    * @return name
    */
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    * {@link PinBankAccount#bsb}
    * @return bsb
    */
    @XmlElement(required = true)
    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

   /**
    * {@link PinBankAccount#number}
    * @return number
    */
    @XmlElement(required = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
