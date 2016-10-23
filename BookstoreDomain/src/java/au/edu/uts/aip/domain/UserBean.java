package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean implements UserRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUser(String username) {
        return em.find(User.class, username);
    }
}
