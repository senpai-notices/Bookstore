package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.User;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO implements Serializable {

    private String username;
    private String fullname;
    private String email;
    private String role;

    public UserDTO() {

    }

    public UserDTO(User userEntity) {
        this.username = userEntity.getUsername();
        this.fullname = userEntity.getFullname();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole().getRoleName();
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
