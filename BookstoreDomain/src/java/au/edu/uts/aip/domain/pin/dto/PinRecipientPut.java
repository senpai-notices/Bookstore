package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinRecipientPut implements Serializable {

    private PinBankAccount bankAccount;

    @XmlElement(required = true, name = "bank_account")
    public PinBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(PinBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
