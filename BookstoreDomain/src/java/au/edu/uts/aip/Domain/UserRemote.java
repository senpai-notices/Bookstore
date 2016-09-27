package au.edu.uts.aip.Domain;

import au.edu.uts.aip.DTO.User;
import javax.ejb.Remote;

/**
 *
 * @author sondang
 */
@Remote
public interface UserRemote {
    public User getUser(String username, String password);
}
