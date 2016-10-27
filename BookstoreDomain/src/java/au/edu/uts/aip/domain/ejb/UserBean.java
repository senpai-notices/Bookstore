package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.exception.ActivationException;
import au.edu.uts.aip.domain.exception.InvalidTokenException;
import au.edu.uts.aip.domain.utility.SHA;
import au.edu.uts.aip.domain.utility.SendEmail;
import au.edu.uts.aip.domain.validation.ValidationResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Stateless
public class UserBean implements UserRemote {

    @PersistenceContext
    private EntityManager em;

    @Resource
    Validator validator;

    @Override
    public User getUser(String username) {
        return em.find(User.class, username);
    }

    /**
     *
     * @param user
     * @return
     * @throws MessagingException: when activation email cannot be sent
     */
    @Override
    public ValidationResult createUser(User user) {
        try {
            em.persist(user);

            // hash password
            String unhashedPassword = user.getPassword();
            String hashsedPassword = SHA.hash256(unhashedPassword);
            user.setPassword(hashsedPassword);

            // add user to INACTIVATED group
            Role inactivatedRole = getRole("INACTIVATED");
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
        expirationDate.setTime(now.getTime() + 1000 * 60 * 24);
        String activateToken = Jwts.builder().setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, user.getPassword())
                .setIssuedAt(now).setExpiration(expirationDate).compact();
        return activateToken;
    }

    @Override
    public void activateAccount(String token, String username) throws ActivationException, InvalidTokenException{
        try {
            User user = em.find(User.class, username);
            
            if (!user.getRole().getRoleName().equals("INACTIVATED")) {
                throw new ActivationException();
            }
            
            Jwts.parser().setSigningKey(user.getPassword())
                .requireSubject(user.getUsername()).parseClaimsJws(token);
            
            Role userRole = getRole("USER");
            user.setRole(userRole);
            em.persist(user);
            
        } catch (SignatureException ex) {
            throw new InvalidTokenException();
        }
    }
    
    private Role getRole(String roleName){
        TypedQuery<Role> typedQuery = em.createNamedQuery("Role.find", Role.class);
        typedQuery.setParameter("name", roleName);
        return typedQuery.getSingleResult();
    }
}
