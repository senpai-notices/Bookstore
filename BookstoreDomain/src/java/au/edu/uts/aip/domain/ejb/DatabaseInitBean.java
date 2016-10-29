package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.utility.SHA;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Singleton
@Startup
public class DatabaseInitBean {
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    protected void init(){
        System.out.println("Init database");
        
        System.out.println("Setting up roles");
        RoleType[] allRoles = RoleType.class.getEnumConstants();
        HashMap<RoleType, Role> roleMap = new HashMap<>();
        for(RoleType roleType: allRoles){
            Role role = new Role();
            role.setRoleName(roleType.toString());
            em.persist(role);
            roleMap.put(roleType, role);
        }
        
        System.out.println("Setting up roles...Done");
        
        System.out.println("Setting up views");
        Query q = em.createNativeQuery("create view jdbcrealm_user (username, password) as select username, password from BookstoreUser");
        q.executeUpdate();
        q = em.createNativeQuery("create view jdbcrealm_group (username, groupname)" + 
                "as select u.username, r.roleName from BookstoreUser u left join BookstoreRole r on u.role_id = r.id");
        q.executeUpdate();
        System.out.println("Setting up views...Done");
        
        System.out.println("Creating admin account");
        
        User adminUser = new User();
        adminUser.setFullname("Administrator");
        adminUser.setUsername("admin1");
        adminUser.setPassword(SHA.hash256("qwerty"));
        adminUser.setEmail("admin@test.com");
        adminUser.setRole(roleMap.get(RoleType.ADMIN));
        em.persist(adminUser);
        System.out.println("Creating admin account...Done");
        
        System.out.println("Init database...Done");
    }
    
    @PreDestroy
    protected void cleanup(){
        System.out.println("Cleaning database");
        
        Query q = em.createNativeQuery("drop view jdbcrealm_group");
        q.executeUpdate();
        q = em.createNativeQuery("drop view jdbcrealm_user");
        q.executeUpdate();
        q = em.createNativeQuery("delete from BookstoreUser");
        q.executeUpdate();
        q = em.createNativeQuery("delete from BookstoreRole");
        q.executeUpdate();
        
        System.out.println("Cleaning database...Done");
    }
}

