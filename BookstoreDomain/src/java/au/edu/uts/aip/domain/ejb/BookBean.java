package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.dto.BookDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import au.edu.uts.aip.domain.remote.BookRemote;

/**
 * @inheritDoc
 */
@Stateless
public class BookBean implements BookRemote {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserBean userBean;

    /**
     * @inheritDoc
     */
    @Override
    public List<BookDTO> getLatestBooks(int offset, int limit) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getLatest", Book.class);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Book> booksEntity = typedQuery.getResultList();
        List<BookDTO> booksDTO = new ArrayList<>();

        for (Book bookEntity : booksEntity) {
            booksDTO.add(new BookDTO(bookEntity));
        }
        return booksDTO;
    }

    /**
     * @inheritDoc
     */
    @Override
    public BookDTO getSingleBook(String isbn10, String isbn13, String title) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getSingle", Book.class);
        typedQuery.setParameter("isbn10", "%" + isbn10 + "%");
        typedQuery.setParameter("isbn13", "%" + isbn13 + "%");
        typedQuery.setParameter("title", "%" + title + "%");
        Book bookEntity = typedQuery.getSingleResult();
        return new BookDTO(bookEntity, bookEntity.getSales());
    }
}
