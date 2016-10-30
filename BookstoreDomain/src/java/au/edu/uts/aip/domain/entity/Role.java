package au.edu.uts.aip.domain.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Bookstore_Role")
@NamedQueries({
    @NamedQuery(name="Role.find", query="SELECT r from Role r where r.roleName=:name")
})
public class Role implements Serializable {
    private int Id;
    private String roleName;
    
    public enum RoleType{
        ADMIN("ADMIN"), USER("USER"), INACTIVATED("INACTIVATED"), BANNED("BANNED");
        
        private final String type;
        
        private RoleType(String type){
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
    
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
