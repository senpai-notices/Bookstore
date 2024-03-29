package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.User;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * UserDTO is a DTO class that is used to retrieve all the information related to the website user
 * It is a data transfer object
 *
 * It has properties: username: String value fullname: String value email: String value role: String
 * value address: String value The attributes are not null and accessed by get and set methods
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class UserDTO implements Serializable {

    /**
     * the account name of the user
     */
    private String username;
    /**
     * the full name of the user
     */
    private String fullname;
    /**
     * the email info of the user
     */
    private String email;
    /**
     * the role of the user
     */
    private String role;
    /**
     * the address of the user
     */
    private String address;

    private int postcode;

    /**
     * Parameterless constructor for serializable interface
     */
    public UserDTO() {

    }

    /**
     * Constructor that copy data from user entity model
     *
     * @param userEntity
     */
    public UserDTO(User userEntity) {
        this.username = userEntity.getUsername();
        this.fullname = userEntity.getFullname();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole().getRoleName();

        if (userEntity.getAddress() != null) {
            this.address = userEntity.getAddress().toString();
            this.postcode = userEntity.getAddress().getAddressPostcode();
        }
    }

    /**
     * {@link UserDTO#username}
     *
     * @return username
     */
    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * {@link UserDTO#fullname}
     *
     * @return fullname
     */
    @XmlElement
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * {@link UserDTO#email}
     *
     * @return email
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * {@link UserDTO#role}
     *
     * @return role
     */
    @XmlElement
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * {@link UserDTO#address}
     *
     * @return address
     */
    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * {@link UserDTO#postcode}
     *
     * @return postcode
     */

    @XmlElement
    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
