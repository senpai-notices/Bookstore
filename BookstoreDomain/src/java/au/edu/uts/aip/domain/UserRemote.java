package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.User;
import au.edu.uts.aip.validation.ValidationResult;
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
