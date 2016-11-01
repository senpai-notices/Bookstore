package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.remote.AdminRemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdminBean implements AdminRemote {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private UserBean userBean;
    
    @Override
    public void rejectVerificationRequest(String username){
        User user = userBean.getUser(username);
        
        user.setIdVerificationPath(null);
        user.setResidentialVerificationPath(null);
        Role normalUserRole = userBean.getRole(Role.RoleType.USER.toString());
        user.setRole(normalUserRole);
        
        em.persist(user);
    }

    @Override
    public void approveVerificationRequest(String username) {
        User user = userBean.getUser(username);
        
        Role verfiedUserRole = userBean.getRole(Role.RoleType.VERIFIED.toString());
        user.setRole(verfiedUserRole);
        
        em.persist(user);
    }
    
}
