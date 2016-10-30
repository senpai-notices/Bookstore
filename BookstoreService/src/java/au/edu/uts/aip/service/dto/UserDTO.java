package au.edu.uts.aip.service.dto;

import au.edu.uts.aip.domain.entity.User;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO implements Serializable {
    @XmlElement
    private String username;
    @XmlElement
    private String fullname;
    @XmlElement
    private String email;
    @XmlElement
    private String role;
    
    public UserDTO(){
        
    }
    
    public UserDTO(User userEntity){
        this.username = userEntity.getUsername();
        this.fullname = userEntity.getFullname();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole().getRoleName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
