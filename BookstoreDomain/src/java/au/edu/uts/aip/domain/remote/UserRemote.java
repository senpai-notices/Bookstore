package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.AddressDTO;
import au.edu.uts.aip.domain.dto.DocumentsDTO;
import au.edu.uts.aip.domain.dto.RegistrationDTO;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.PasswordResetException;
import au.edu.uts.aip.domain.exception.TokenGenerationException;
import java.util.List;
import javax.ejb.Remote;
import javax.validation.Valid;

@Remote
public interface UserRemote {

    UserDTO getUser(String username);
    
    List<UserDTO> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit);

    void createUser(@Valid RegistrationDTO registraiontionDTO);

    String generateActivationToken(String username) throws TokenGenerationException;

    String generateResetPasswordToken(String username, String email) throws TokenGenerationException;
    
    void activateAccount(String token, String username) throws ActivationException;
    
    void resetPassword(String token, String username, String newPassword) throws PasswordResetException;

    void updateVerificationDocuments(String username, String documentType, String filePath);
    
    DocumentsDTO getDocumentPath(String username);
    
    void updateAddress(AddressDTO addressDTO, String username);
}
