package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.remote.UserRemote;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.InvalidTokenException;
import au.edu.uts.aip.domain.utility.SHA;
import au.edu.uts.aip.domain.validation.ValidationResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
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

@Stateless
@LocalBean
public class UserBean implements UserRemote {

    @PersistenceContext
    private EntityManager em;

    public Role getRole(String roleName) {
        TypedQuery<Role> typedQuery = em.createNamedQuery("Role.find", Role.class);
        typedQuery.setParameter("name", roleName);
        return typedQuery.getSingleResult();
    }

    @Override
    public User getUser(String username) {
        try {
            TypedQuery<User> typedQuery = em.createNamedQuery("User.find", User.class);
            typedQuery.setParameter("username", username);
            return typedQuery.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public ValidationResult createUser(User user) {
        try {
            User existingUser = getUser(user.getUsername());

            if (existingUser != null) {
                ValidationResult result = new ValidationResult();
                String errorMessage = "Username " + user.getUsername() + " already exists";
                result.addFormError("username", errorMessage);
                return result;
            }

            em.persist(user);

            // hash password
            String unhashedPassword = user.getPassword();
            String hashsedPassword = SHA.hash256(unhashedPassword);
            user.setPassword(hashsedPassword);

            // add user to INACTIVATED group
            Role inactivatedRole = getRole(RoleType.INACTIVATED.toString());
            user.setRole(inactivatedRole);

            // no validation error
            return null;
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

            ValidationResult result = new ValidationResult();
            Set<ConstraintViolation<?>> constraints = ex.getConstraintViolations();
            for (ConstraintViolation constraint : constraints) {
                result.addFormError(constraint.getPropertyPath().toString(), constraint.getMessage());
            }
            return result;
        }
    }

    @Override
    public String generateActivationToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date();
        expirationDate.setTime(now.getTime() + 1000 * 60 * 60 * 24);
        String activateToken = Jwts.builder().setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, user.getPassword())
                .setIssuedAt(now).setExpiration(expirationDate).compact();
        return activateToken;
    }

    @Override
    public void activateAccount(String token, String username) throws ActivationException, InvalidTokenException {
        try {
            User user = getUser(username);

            if (!user.getRole().getRoleName().equals("INACTIVATED")) {
                throw new ActivationException();
            }

            Jwts.parser().setSigningKey(user.getPassword())
                    .requireSubject(user.getUsername()).parseClaimsJws(token);

            Role userRole = getRole(RoleType.USER.toString());
            user.setRole(userRole);
            em.persist(user);

        } catch (SignatureException ex) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public List<User> findUsers(String[] rolesName, String username, String fullname, String email, int offset, int limit) {
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
        return typedQuery.getResultList();
    }

    @Override
    public void updateVerificationDocuments(String username, String documentType, String filePath) {
        User user = getUser(username);
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

    @Override
    public void banAccount(String username) {
        User user = getUser(username);

        if (user.getRole().getRoleName().equals(RoleType.ADMIN.toString())) {
            throw new RuntimeException("Cannot ban administrator account");
        }

        Role bannedRole = getRole(RoleType.BANNED.toString());
        user.setRole(bannedRole);

        em.persist(user);
    }

    @Override
    public void unbanAccount(String username) {
        User user = getUser(username);

        if (!user.getRole().getRoleName().equals(RoleType.BANNED.toString())) {
            throw new RuntimeException("The account is no banned");
        }

        Role userRole = getRole(RoleType.USER.toString());
        user.setRole(userRole);

        em.persist(user);
    }
}
