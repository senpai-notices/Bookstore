package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.User;
import javax.ejb.Remote;

/**
 *
 * @author sondang
 */
@Remote
public interface UserRemote {
    User getUser(String username);
}
