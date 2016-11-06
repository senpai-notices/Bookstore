package au.edu.uts.aip.domain.ejb;

/**
 *The needed libraries
 */
import au.edu.uts.aip.domain.dto.AddressDTO;
import au.edu.uts.aip.domain.dto.DocumentsDTO;
import au.edu.uts.aip.domain.dto.RegistrationDTO;
import au.edu.uts.aip.domain.dto.ResetPasswordDTO;
import au.edu.uts.aip.domain.dto.UserDTO;
import au.edu.uts.aip.domain.entity.Address;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.PasswordResetException;
import au.edu.uts.aip.domain.exception.TokenGenerationException;
import au.edu.uts.aip.domain.util.SHA;
import au.edu.uts.aip.domain.validation.ValidationResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

/**
 * UserBean is a JavaBean that is used to handle the user related operations
 * 
 * It has 12 methods:
 * getRole(): used to get the role object
 * getUser(): used to get the user info
 * getUserEntity(): used to get a User Object
 * createUser(): used to create a user
 * getDocumentPath(): used to get the path of the document
 * generateActivationToken(): used to generate the activation Token
 * generateResetPasswordToken(): used to get the reset password token
 * resetPassword(): used to reset the password
 * activateAccount(): used to activate the account
 * findUsers(): used to find all the users
 * updateVerificationDocuments(): used to update the verification documents
 * updateAddress(): used to update the address
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Stateless
@LocalBean
public class UserBean implements UserRemote {

    @PersistenceContext
    private EntityManager em;

     /**
     * the method is used to get the role object
     */
    public Role getRole(String roleName) {
        TypedQuery<Role> typedQuery = em.createNamedQuery("Role.find", Role.class);
        typedQuery.setParameter("name", roleName);
        return typedQuery.getSingleResult();
    }

     /**
     * the method is used to get the information of the user
     * return a UserDTO object
     */
    @Override
    public UserDTO getUser(String username) {
            User userEntity = getUserEntity(username);
            return userEntity == null ? null : new UserDTO(userEntity);
    }
    
     /**
     * the method is used to get a relevant User object
     */
    public User getUserEntity(String username){
        try {
            TypedQuery<User> typedQuery = em.createNamedQuery("User.find", User.class);
            typedQuery.setParameter("username", username);
            return typedQuery.getSingleResult();
        } catch (Exception ex){
            return null;
        }
    }

    /**
     *
     * @param registraiontionDTO
     * @return
     */
    @Override
    public void createUser(@Valid RegistrationDTO registraiontionDTO) {
        User userEntity = getUserEntity(registraiontionDTO.getUsername());

        if (userEntity != null) {
            ValidationResult result = new ValidationResult();
            String errorMessage = "Username " + registraiontionDTO.getUsername() + " already exists";
            result.addFormError("username", errorMessage);
            throw new RuntimeException(errorMessage);
        }

        userEntity = new User();
        userEntity.setUsername(registraiontionDTO.getUsername());
        userEntity.setFullname(registraiontionDTO.getFullname());
        userEntity.setEmail(registraiontionDTO.getEmail());
        // hash password
        userEntity.setPassword(SHA.hash256(registraiontionDTO.getPassword()));

        // insert to database
        em.persist(userEntity);

        // add user to INACTIVATED group
        Role inactivatedRole = getRole(RoleType.INACTIVATED.toString());
        userEntity.setRole(inactivatedRole);

        // no validation error

    }
    
     /**
     * the method is used to get the path of the documents
     * return a DocumentsDTO object
     */
    @Override
    public DocumentsDTO getDocumentPath(String username) {
        User user = getUserEntity(username);
        DocumentsDTO documentDTO = new DocumentsDTO();
        documentDTO.setIdVerificationPath(user.getIdVerificationPath());
        documentDTO.setResidentialVerificationPath(user.getResidentialVerificationPath());
        return documentDTO;
    }

     /**
     * the method is used to generate the activation Token
     * return a String value
     */
    @Override
    public String generateActivationToken(String username) throws TokenGenerationException {
        User user = getUserEntity(username);
        if (user == null){
            throw new TokenGenerationException("Account does not exists");
        }
        Date now = new Date();
        Date expirationDate = new Date();
        expirationDate.setTime(now.getTime() + 1000 * 60 * 60 * 24);
        String activateToken = Jwts.builder().setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, user.getPassword())
                .setIssuedAt(now).setExpiration(expirationDate).compact();
        user.setActivationToken(activateToken);
        return activateToken;
    }
    
      /**
     * the method is used to generate the reset password token
     * return a String value
     */
    @Override
    public String generateResetPasswordToken(String username, String email) throws TokenGenerationException {
        User user = getUserEntity(username);
        if (user == null || !email.equals(user.getEmail())){
            throw new TokenGenerationException("Username and email do not match");
        }
        
        Date now = new Date();
        Date expirationDate = new Date();
        expirationDate.setTime(now.getTime() + 1000 * 60 * 60 * 24);
        String resetPasswordToken = Jwts.builder().setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, user.getPassword())
                .setIssuedAt(now).setExpiration(expirationDate).compact();
        user.setResetPasswordToken(resetPasswordToken);
        return resetPasswordToken;
    }

    /**
     * the method is used to activate a user account
     * return none
     */
    @Override
    public void activateAccount(String token, String username) throws ActivationException {
        User user = getUserEntity(username);

        if (user == null){
            throw new ActivationException("Account does not exists");
        }
        
        if (user.getRole().getRoleName().equals(RoleType.BANNED.toString())) {
            throw new ActivationException("Account has been banned");
        }
        
        if (!user.getRole().getRoleName().equals(RoleType.INACTIVATED.toString())){
            throw new ActivationException("Account has already activated");
        }

        if (!token.equals(user.getActivationToken())){
            throw new ActivationException("Invalid token");
        }

        try{
            Jwts.parser().setSigningKey(user.getPassword())
                    .requireSubject(user.getUsername()).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex){
            throw new ActivationException("Invalid token");
        } catch (ExpiredJwtException ex){
            throw new ActivationException("Token expired");
        }

        Role userRole = getRole(RoleType.USER.toString());
        user.setRole(userRole);
    }
    
       
    /**
     * the method is used to reset the password
     * return none
     */
    @Override
    public void resetPassword(@Valid ResetPasswordDTO resetPasswordDTO) {
        User user = getUserEntity(resetPasswordDTO.getUsername());
        
        if (user == null){
            throw new RuntimeException("Account does not exists");
        }
        
        if (user.getRole().getRoleName().equals(RoleType.BANNED.toString())) {
            throw new RuntimeException("Account has been banned");
        }
        
        if (!resetPasswordDTO.getResetToken().equals(user.getResetPasswordToken())){
            throw new RuntimeException("Invalid token");
        }
        
        try{
            Jwts.parser().setSigningKey(user.getPassword())
                    .requireSubject(user.getUsername())
                    .parseClaimsJws(resetPasswordDTO.getResetToken());
            
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex){
            throw new RuntimeException("Invalid token");
        } catch (ExpiredJwtException ex){
            throw new RuntimeException("Token expired");
        }
        
        user.setPassword(SHA.hash256(resetPasswordDTO.getNewPassword()));
    }

    /**
     * the method is used to find all the users
     */
    @Override
    public List<UserDTO> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit) {
        List<Role> roles = new ArrayList<>();
        for (String roleName : rolesName) {
            roles.add(getRole(roleName));
        }

        TypedQuery<User> typedQuery = em.createNamedQuery("User.findUsers", User.class);
        typedQuery.setParameter("roles", roles);
        typedQuery.setParameter("username", "%" + username + "%");
        typedQuery.setParameter("fullname", "%" + fullname + "%");
        typedQuery.setParameter("email", "%" + email + "%");
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        
        List<User> usersEntity = typedQuery.getResultList();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User userEntity: usersEntity){
            usersDTO.add(new UserDTO(userEntity));
        }
        return usersDTO;
    }

     /**
     * the method is used to update the verification documents
     */
    @Override
    public void updateVerificationDocuments(String username, String documentType, String filePath) {
        User user = getUserEntity(username);
        if (documentType.equals("id")) {
            user.setIdVerificationPath(filePath);
        } else if (documentType.equals("residential")) {
            user.setResidentialVerificationPath(filePath);
        } else {
            throw new RuntimeException("Invalid document type");
        }

        if (user.getRole().getRoleName().equals(RoleType.ADMIN.toString())) {
            throw new RuntimeException("Verification on administrator account");
        }

        if (user.getIdVerificationPath() != null && user.getResidentialVerificationPath() != null) {
            Role verifyingRole = getRole(RoleType.VERIFYING.toString());
            user.setRole(verifyingRole);
        }

        em.persist(user);
    }
    
      /**
     * the method is used to update the address information
     * return none
     */
    @Override
    public void updateAddress(AddressDTO addressDTO, String username) {
        User user = getUserEntity(username);
        
        Address addressEntity = new Address();
        addressEntity.setAddressLine1(addressDTO.getAddressLine1());
        addressEntity.setAddressLine2(addressDTO.getAddressLine2());
        addressEntity.setAddressCity(addressDTO.getAddressCity());
        addressEntity.setAddressCountry(addressDTO.getAddressCountry());
        addressEntity.setAddressPostcode(addressDTO.getAddressPostCode());
        addressEntity.setAddressState(addressDTO.getAddressState());
        
        em.persist(addressEntity);
        user.setAddress(addressEntity);
    }
    
    @Override
    public void updateRecipientToken(String username, String token) {
        User user = getUserEntity(username);
        user.setRecipientToken(token);
    }
}
