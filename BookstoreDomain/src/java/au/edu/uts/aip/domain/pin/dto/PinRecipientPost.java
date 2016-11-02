package au.edu.uts.aip.domain.pin.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PinRecipientPost implements Serializable {
    private String email;
    private PinBankAccountPost bankAccount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name="bank_account")
    public PinBankAccountPost getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(PinBankAccountPost bankAccount) {
        this.bankAccount = bankAccount;
    }
}
