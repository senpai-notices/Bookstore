package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.pin.dto.BookDTO;
import au.edu.uts.aip.domain.pin.dto.BookSaleDTO;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class BookstoreBean implements BookstoreRemote {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserBean userBean;

    @Override
    public List<Book> getLatestBooks(int offset, int limit) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getLatest", Book.class);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Book> result = typedQuery.getResultList();
        return result;
    }

    @Override
    public Book getSingleBook(String isbn10, String isbn13, String title) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getSingle", Book.class);
        typedQuery.setParameter("isbn10", "%" + isbn10 + "%");
        typedQuery.setParameter("isbn13", "%" + isbn13 + "%");
        typedQuery.setParameter("title", "%" + title + "%");
        return typedQuery.getSingleResult();
    }

    public Book createBook(BookDTO bookDTO) {
        Book bookEntity = new Book();
        bookEntity.setIsbn10(bookDTO.getIsbn10());
        bookEntity.setIsbn13(bookDTO.getIsbn13());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setImgPath(bookDTO.getImgPath());
        bookEntity.setPublishYear(bookDTO.getPublishYear());
        bookEntity.setPublisher(bookDTO.getPublisher());
        bookEntity.setPageCount(bookDTO.getPageCount());

        em.persist(bookEntity);
        return bookEntity;
    }

    @Override
    public BookDTO updateSale(String username, BookDTO salesData) {
        User seller = userBean.getUser(username);

        if (!seller.getRole().getRoleName().equals(Role.RoleType.ADMIN.toString())
                && !seller.getRole().getRoleName().equals(Role.RoleType.VERIFIED.toString())) {
            throw new RuntimeException("User do not have authority to sell books");
        }

        Book book = null;
        if (salesData.getId() == 0) {
            book = createBook(salesData);
        } else {
            book = em.find(Book.class, salesData.getId());
        }
        TypedQuery<BookSales> typedQuery
                = em.createNamedQuery("BookSales.findSales", BookSales.class);
        typedQuery.setParameter("book", book);
        typedQuery.setParameter("seller", seller);
        List<BookSales> oldSales = typedQuery.getResultList();

        for (BookSales oldSale : oldSales) {
            seller.getSellingBooks().remove(oldSale);
            book.getSales().remove(oldSale);
            em.remove(oldSale);
        }

        for (BookSaleDTO saleData : salesData.getSales()) {
            BookSales newSale = new BookSales();
            newSale.setBook(book);
            newSale.setSeller(seller);
            newSale.setCondition(saleData.getBookCondition());
            newSale.setPrice(saleData.getPrice());
            newSale.setQuantity(saleData.getQuantity());

            book.getSales().add(newSale);
            seller.getSellingBooks().add(newSale);
            em.persist(newSale);
        }

        return new BookDTO(book, book.getSales());
    }
}
