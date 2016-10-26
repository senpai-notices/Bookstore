package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BookstoreUser")
public class User implements Serializable {
    
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String username;
    
    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^([a-zA-Z]+)( [a-zA-Z]+)*$")
    private String fullname;
    
    @NotNull
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    
    @NotNull
    @Pattern(regexp = "^[^ ]+@[^ ]+\\.[^ ]+$")
    private String email;
    
    private Role role;

    @Id
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
