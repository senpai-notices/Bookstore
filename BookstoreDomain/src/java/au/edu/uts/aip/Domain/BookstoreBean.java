package au.edu.uts.aip.Domain;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generates tables
 */
@Stateless
public class BookstoreBean {
    @PersistenceContext
    private EntityManager em;
}
