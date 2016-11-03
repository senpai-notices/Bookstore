package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSeller;
import au.edu.uts.aip.domain.entity.Category;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class BookstoreBean implements BookstoreRemote {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getLatestBooks(int offset, int limit) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getLatest", Book.class);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Book> result = typedQuery.getResultList();
        return result;
    }
    
    @Override
    public Book getSingleBook(String isbn10, String isbn13, String title){
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getSingle", Book.class);
        typedQuery.setParameter("isbn10", "%" + isbn10 + "%");
        typedQuery.setParameter("isbn13", "%" + isbn13 + "%");
        typedQuery.setParameter("title", "%" + title + "%");
        return typedQuery.getSingleResult();
    }
}
