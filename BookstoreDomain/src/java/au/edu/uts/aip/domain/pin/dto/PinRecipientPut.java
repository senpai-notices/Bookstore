package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PinRecipientPut class is used to set bank account info
 * 
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class PinRecipientPut implements Serializable {

     /**
     * the Bank account of the user
     */
    private PinBankAccount bankAccount;

    /**
    * {@link PinRecipientPut#bankAccount}
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
