package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.pin.dto.BookDTO;
import au.edu.uts.aip.domain.pin.dto.BookSaleDTO;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import java.util.ArrayList;
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
    public Book getSingleBook(String isbn10, String isbn13, String title){
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getSingle", Book.class);
        typedQuery.setParameter("isbn10", "%" + isbn10 + "%");
        typedQuery.setParameter("isbn13", "%" + isbn13 + "%");
        typedQuery.setParameter("title", "%" + title + "%");
        return typedQuery.getSingleResult();
    }
    
    @Override
    public void updateSale(String username, BookDTO salesData) {
        User seller = userBean.getUser(username);
        
        if (!seller.getRole().getRoleName().equals(Role.RoleType.ADMIN.toString())
                &&seller.getRole().getRoleName().equals(Role.RoleType.VERIFIED.toString())){
            throw new RuntimeException("User do not have authority to sell books");
        }
        
        Book book = em.find(Book.class, salesData.getId());
        
        TypedQuery<BookSales> typedQuery = 
                em.createNamedQuery("BookSales.getSingle", BookSales.class);
        typedQuery.setParameter("book", book);
        typedQuery.setParameter("seller", seller);
        List<BookSales> oldSales = typedQuery.getResultList();
        
        for(BookSales oldSale: oldSales){
            seller.getSellingBooks().remove(oldSale);
            book.getSales().remove(oldSale);
            em.remove(oldSale);
        }
        
        List<BookSales> newSales = new ArrayList<>();
        for(BookSaleDTO saleData: salesData.getSales()){
            BookSales newDbSale = new BookSales();
            newDbSale.setBook(book);
            newDbSale.setSeller(seller);
            newDbSale.setCondition(saleData.getBookCondition());
            newDbSale.setPrice(saleData.getPrice());
            newDbSale.setQuantity(saleData.getQuantity());

            book.getSales().add(newDbSale);
            seller.getSellingBooks().add(newDbSale);
            em.persist(newDbSale);
        }
    }
}
