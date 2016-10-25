package au.edu.uts.aip.domain;

import au.edu.uts.aip.entity.Role;
import au.edu.uts.aip.entity.User;
import au.edu.uts.aip.utility.SHA;
import au.edu.uts.aip.utility.SendEmail;
import au.edu.uts.aip.validation.ValidationResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public ValidationResult createUser(User user) throws MessagingException {
        try{
            em.persist(user);
            
            // hash password
            String unhashedPassword = user.getPassword();
            String hashsedPassword = SHA.hash256(unhashedPassword);
            user.setPassword(hashsedPassword);
            
            // add user to INACTIVATED group
            TypedQuery<Role> typedQuery = em.createNamedQuery("Role.find", Role.class);
            typedQuery.setParameter("name", "INACTIVATED");
            Role inactivatedRole = typedQuery.getSingleResult();
            user.setRole(inactivatedRole);
            
            // generate token for email activation
            Date now = new Date();
            Date expirationDate = new Date();
            expirationDate.setTime(now.getTime() + 1000 * 60 * 24);
            String activateToken = Jwts.builder().setSubject(user.getUsername())
                                    .signWith(SignatureAlgorithm.HS512, hashsedPassword)
                                    .setIssuedAt(now).setExpiration(expirationDate).compact();
            SendEmail.SendActivationEmail(user, activateToken);
            
            return null;
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            
            ValidationResult result = new ValidationResult();
            Set<ConstraintViolation<?>> constraints = ex.getConstraintViolations();
            for(ConstraintViolation constraint: constraints){
                result.addFormError(constraint.getPropertyPath().toString(), constraint.getMessage());
            }
            return result;
        }
    }
}
