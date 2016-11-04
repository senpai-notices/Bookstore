package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A Data model class to access data in the BOOKSTOREUSER table. User describes the a bookstore web
 * site user It has properties Username, Fullname, Password, Email and Role All the attributes of
 * this model class are private attributes and all of them have relative get and set functions in
 * order to access the attributes
 *
 * @author
 */
@Entity
@Table(name = "Bookstore_User")
@NamedQueries({
    @NamedQuery(name = "User.find", query = "SELECT u from User u where u.username=:username")
    ,
    @NamedQuery(name = "User.findUsers", query = "SELECT u from User u where "
            + "u.role IN :roles AND lower(u.email) like lower(:email) "
            + "AND lower(u.username) like lower(:username) "
            + "AND lower(u.fullname) like lower(:fullname)")
})
public class User implements Serializable {

    private long Id;

    /**
     * The definition of the attributes of User Model class The pattern is governed by regular
     * expression And the size are defined
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

    private List<BookSales> sellingBooks = new ArrayList<>();

    private Role role;

    private String idVerificationPath;

    private String residentialVerificationPath;

    @Id
    @GeneratedValue
    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    /**
     * The username of the web site user in Bookstoreuser table Not null value the length of the
     * string of username is >=6 && <=32
     *
     * @return a string
     */
    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The full name of the web site user in Bookstoreuser table the length of the string of
     * fullname is less than or equal to 255 Not null value
     *
     * @return a string
     */
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * The password of the web site user in Bookstoreuser table the minimum length of the password
     * is 6 Not null value
     *
     * @return a String
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The role of the web site user in Bookstoreuser table Not null value
     *
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
     * The email of the web site user in Bookstoreuser table Not null value. The format of value
     * should abide by the format of emails. i.e. aaa@gmail.com
     *
     * @return a string
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "seller")
    public List<BookSales> getSellingBooks() {
        return sellingBooks;
    }

    public void setSellingBooks(List<BookSales> sellingBooks) {
        this.sellingBooks = sellingBooks;
    }

    public String getIdVerificationPath() {
        return idVerificationPath;
    }

    public void setIdVerificationPath(String idVerificationPath) {
        this.idVerificationPath = idVerificationPath;
    }

    public String getResidentialVerificationPath() {
        return residentialVerificationPath;
    }

    public void setResidentialVerificationPath(String residentialVerificationPath) {
        this.residentialVerificationPath = residentialVerificationPath;
    }
}
