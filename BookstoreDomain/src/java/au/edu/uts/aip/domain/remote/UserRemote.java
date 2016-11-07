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

    /**
     * the method is used to get the role object
     * @param roleName
     * @return 
     */
    public Role getRole(String roleName);

    /**
     * the method is used to get the information of the user return a UserDTO object
     * @param username
     * @return 
     */
    UserDTO getUser(String username);

    /**
     * the method is used to get a relevant User object
     * @param username
     * @return 
     */
    public User getUserEntity(String username);

    /**
     * the method is used to find all the users
     * @param rolesName
     * @param username
     * @param email
     * @param fullname
     * @param offset
     * @param limit
     * @return 
     */
    List<UserDTO> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit
    );

    /**
     *
     * @param registraiontionDTO
     */
    void createUser(@Valid RegistrationDTO registraiontionDTO);

    /**
     * the method is used to generate the activation Token return a String value
     * @param username
     * @return 
     * @throws au.edu.uts.aip.domain.exception.TokenGenerationException
     */
    String generateActivationToken(String username) throws TokenGenerationException;

    /**
     * the method is used to generate the reset password token return a String value
     * @param username
     * @param email
     * @return 
     * @throws au.edu.uts.aip.domain.exception.TokenGenerationException 
     */
    String generateResetPasswordToken(String username, String email) throws TokenGenerationException;

    /**
     * the method is used to activate a user account return none
     * @param token
     * @param username
     * @throws au.edu.uts.aip.domain.exception.ActivationException
     */
    void activateAccount(String token, String username) throws ActivationException;

    /**
     * the method is used to reset the password return none
     * @param resetPasswordDTO
     */
    void resetPassword(@Valid ResetPasswordDTO resetPasswordDTO);

    /**
     * the method is used to update the verification documents
     * @param username
     * @param documentType
     * @param filePath
     */
    void updateVerificationDocuments(String username, String documentType, String filePath);

    /**
     * the method is used to get the path of the documents return a DocumentsDTO object
     * @param username
     * @return 
     */
    DocumentsDTO getDocumentPath(String username);

    /**
     * the method is used to update the address information return none
     * @param addressDTO
     * @param username
     */
    void updateAddress(AddressDTO addressDTO, String username);

    /**
     *
     * @param username
     * @param token
     */
    void updateRecipientToken(String username, String token);
}
