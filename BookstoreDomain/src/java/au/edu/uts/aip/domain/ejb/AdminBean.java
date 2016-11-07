package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.remote.AdminRemote;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @inheritDoc
 */
@Stateless
public class AdminBean implements AdminRemote {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserBean userBean;

    /**
     * @inheritDoc
     */
    @Override
    public void rejectVerificationRequest(String username) {
        User user = userBean.getUserEntity(username);

        if (!Role.RoleType.VERIFYING.toString().equals(user.getRole().getRoleName())) {
            throw new RuntimeException("The user does not have any verification request");
        }

        user.setIdVerificationPath(null);
        user.setResidentialVerificationPath(null);
        Role normalUserRole = userBean.getRole(Role.RoleType.USER.toString());
        user.setRole(normalUserRole);

        em.persist(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void approveVerificationRequest(String username) {
        User user = userBean.getUserEntity(username);

        Role verfiedUserRole = userBean.getRole(Role.RoleType.VERIFIED.toString());
        user.setRole(verfiedUserRole);

        em.persist(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void banAccount(String username) {
        User user = userBean.getUserEntity(username);

        if (user.getRole().getRoleName().equals(Role.RoleType.ADMIN.toString())) {
            throw new RuntimeException("Cannot ban administrator account");
        }

        Role bannedRole = userBean.getRole(Role.RoleType.BANNED.toString());
        user.setRole(bannedRole);

        em.persist(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unbanAccount(String username) {
        User user = userBean.getUserEntity(username);

        if (!user.getRole().getRoleName().equals(Role.RoleType.BANNED.toString())) {
            throw new RuntimeException("The account is no banned");
        }

        Role userRole = userBean.getRole(Role.RoleType.USER.toString());
        user.setRole(userRole);

        em.persist(user);
    }
}
