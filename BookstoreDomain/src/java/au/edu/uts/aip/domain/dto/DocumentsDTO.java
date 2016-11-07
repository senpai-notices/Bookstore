package au.edu.uts.aip.domain.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DocumentsDTO is a DTO class that is used to verify the identification and residential information
 * It is a data transfer object Used to retrieve the idVerificationPath and
 * residentialVerificationPath It has properties: idVerificationPath, residentialVerificationPath
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class DocumentsDTO implements Serializable {

    /**
     * the directory path for id verification
     */
    private String idVerificationPath;
    /**
     * the path for residential verification
     */
    private String residentialVerificationPath;

    /**
     * {@link DocumentsDTO#idVerificationPath} data type: String return a String value
     *
     * @return idVerificationPath
     */
    @XmlElement
    public String getIdVerificationPath() {
        return idVerificationPath;
    }

    public void setIdVerificationPath(String idVerificationPath) {
        this.idVerificationPath = idVerificationPath;
    }

    /**
     * {@link DocumentsDTO#residentialVerificationPath} data type: String return a String value
     *
     * @return residentialVerificationPath
     */
    @XmlElement
    public String getResidentialVerificationPath() {
        return residentialVerificationPath;
    }

    public void setResidentialVerificationPath(String residentalVerificationPath) {
        this.residentialVerificationPath = residentalVerificationPath;
    }
}
