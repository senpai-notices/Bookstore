package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.DocumentsDTO;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.validation.ValidationResult;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface UserRemote {

    UserDTO getUser(String username);

    ValidationResult createUser(UserDTO user, String password);
    
    DocumentsDTO getDocumentPath(String username);

    String generateActivationToken(String username);

    void activateAccount(String token, String username) throws ActivationException;

    List<UserDTO> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit);

    void updateVerificationDocuments(String username, String documentType, String filePath);

    void banAccount(String username);

    void unbanAccount(String username);
}
