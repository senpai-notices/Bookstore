package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinRecipientPost implements Serializable {
    private String email;
    private PinBankAccount bankAccount;

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name="bank_account")
    public PinBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(PinBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
