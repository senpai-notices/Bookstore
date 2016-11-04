package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentsDTO implements Serializable {
    private String idVerificationPath;
    private String residentialVerificationPath;

    @XmlElement
    public String getIdVerificationPath() {
        return idVerificationPath;
    }
    
    public void setIdVerificationPath(String idVerificationPath) {
        this.idVerificationPath = idVerificationPath;
    }

    @XmlElement
    public String getResidentialVerificationPath() {
        return residentialVerificationPath;
    }

    public void setResidentialVerificationPath(String residentalVerificationPath) {
        this.residentialVerificationPath = residentalVerificationPath;
    }
}
