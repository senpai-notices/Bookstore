package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.InvalidTokenException;
import au.edu.uts.aip.domain.validation.ValidationResult;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface UserRemote {

    User getUser(String username);

    ValidationResult createUser(User user);

    String generateActivationToken(User user);

    void activateAccount(String token, String username) throws ActivationException, InvalidTokenException;

    List<User> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit);

    void updateVerificationDocuments(String username, String documentType, String filePath);

    void banAccount(String username);

    void unbanAccount(String username);
}
