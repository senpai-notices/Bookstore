package au.edu.uts.aip.domain.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BookstoreBean {
    @PersistenceContext
    private EntityManager em;
}
