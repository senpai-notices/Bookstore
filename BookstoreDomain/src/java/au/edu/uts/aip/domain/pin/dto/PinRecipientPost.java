package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinReceiptPost class is used to access the info of the bank account of user's
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinRecipientPost implements Serializable {
    /**
     * the email of the user
     */
    private String email;
    /**
     * the bank account of the user
     */
    private PinBankAccount bankAccount;

   /**
    * {@link PinRecipientPost#email}
    * @return email
    */
    @XmlElement(required = true, name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * {@link PinRecipientPost#bankAccount}
    * @return bankAccount
    */
    @XmlElement(required = true, name = "bank_account")
    public PinBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(PinBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
