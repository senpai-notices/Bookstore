package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.validation.ValidationResult;
import javax.ejb.Remote;
import javax.mail.MessagingException;

/**
 *
 * @author sondang
 */
@Remote
public interface UserRemote {
    User getUser(String username);
    ValidationResult createUser(User user);
    void sendActivateEmail(String username) throws MessagingException;
}
