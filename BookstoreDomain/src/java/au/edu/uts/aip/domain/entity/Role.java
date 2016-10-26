package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "BookstoreRole")
@NamedQueries({
    @NamedQuery(name="Role.find", query="SELECT r from Role r where r.roleName=:name")
})
public class Role implements Serializable {
    private int Id;
    private String roleName;
    
    @Id
    @GeneratedValue
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
