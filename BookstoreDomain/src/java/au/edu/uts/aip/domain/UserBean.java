package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.User;
import javax.ejb.Stateless;

/**
 *
 * @author sondang
 */
@Stateless
public class UserBean implements UserRemote {

    @Override
    public User getUser(String username, String password) {
        return new User();
    }
    
}
