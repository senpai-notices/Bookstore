package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.User;
import javax.ejb.Remote;

/**
 *
 * @author sondang
 */
@Remote
public interface UserRemote {
    public User getUser(String username, String password);
}
