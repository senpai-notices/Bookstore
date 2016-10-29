package au.edu.uts.aip.domain.entity;

/*import the needed libraries*/
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * A Data model class to access data in the BOOKSTOREUSER table.
 * User describes the a bookstore web site user
 * It has properties Username, Fullname, Password, Email and Role
 * All the attributes of this model class are private attributes 
 * and all of them have relative get and set functions in order to access the attributes
 * 
 * @author 
 */
@Entity
@Table(name = "BookstoreUser")
public class User implements Serializable {
    
    
    /**
     * The definition of the attributes of User Model class
     * The pattern is governed by regular expression
     * And the size are defined
    */
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
     /**
     * The username of the web site user in Bookstoreuser table
     * Not null value
     * the length of the string of username is >=6 && <=32
     * @return a string
     */
    @Id
    public String getUsername() {
        return username;
    }
    
     
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The full name of the web site user in Bookstoreuser table
     * the length of the string of fullname is less than or equal to 255
     * Not null value
     * @return a string
     */
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * The password of the web site user in Bookstoreuser table
     * the minimum length of the password is 6
     * Not null value
     * @return a String 
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    /**
     * The role of the web site user in Bookstoreuser table
     * Not null value
     * @return a Role object
     */
    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * The email of the web site user in Bookstoreuser table
     * Not null value. The format of value should abide by the format of emails. i.e. aaa@gmail.com
     * @return a string
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
