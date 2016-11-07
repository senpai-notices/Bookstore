package au.edu.uts.aip.domain.remote;

import au.edu.uts.aip.domain.dto.AddressDTO;
import au.edu.uts.aip.domain.dto.DocumentsDTO;
import au.edu.uts.aip.domain.dto.RegistrationDTO;
import au.edu.uts.aip.domain.dto.ResetPasswordDTO;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.TokenGenerationException;
import java.util.List;
import javax.ejb.Remote;
import javax.validation.Valid;

/**
 * UserBean is a JavaBean that is used to handle the user related operations
 *
 * It has 12 methods: getRole(): used to get the role object getUser(): used to get the user info
 * getUserEntity(): used to get a User Object createUser(): used to create a user getDocumentPath():
 * used to get the path of the document generateActivationToken(): used to generate the activation
 * Token generateResetPasswordToken(): used to get the reset password token resetPassword(): used to
 * reset the password activateAccount(): used to activate the account findUsers(): used to find all
 * the users updateVerificationDocuments(): used to update the verification documents
 * updateAddress(): used to update the address
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Remote
public interface UserRemote {

    public Role getRole(String roleName);

    UserDTO getUser(String username);

    public User getUserEntity(String username);

    List<UserDTO> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit
    );

    void createUser(@Valid RegistrationDTO registraiontionDTO);

    String generateActivationToken(String username) throws TokenGenerationException;

    String generateResetPasswordToken(String username, String email) throws TokenGenerationException;

    void activateAccount(String token, String username) throws ActivationException;

    void resetPassword(@Valid ResetPasswordDTO resetPasswordDTO);

    void updateVerificationDocuments(String username, String documentType, String filePath);

    DocumentsDTO getDocumentPath(String username);

    void updateAddress(AddressDTO addressDTO, String username);

    void updateRecipientToken(String username, String token);
}
