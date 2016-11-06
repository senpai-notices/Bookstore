package au.edu.uts.aip.domain.ejb;

/**
 *The needed libraries
 */
import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.dto.BookDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
import au.edu.uts.aip.domain.remote.BookstoreRemote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * BookstoreBean is a JavaBean class to manage the books and sales on the website
 * It is a bookstore controller class
 * 
 * It has four methods:
 * getLatestBooks;
 * getSingleBook;
 * createBook;
 * updateSale;
 * getSales;
 * 
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Stateless
public class BookstoreBean implements BookstoreRemote {

    @PersistenceContext
    private EntityManager em;

    /**
    * the userBean is used to manage the users 
    */
    @EJB
    private UserBean userBean;
    
    /**
    * getLatestBooks function is used to get the list of books in the order of time
    * return a list of Book objects
    */
    @Override
    public List<BookDTO> getLatestBooks(int offset, int limit) {
        TypedQuery<Book> typedQuery = em.createNamedQuery("Book.getLatest", Book.class);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(limit);
        List<Book> booksEntity = typedQuery.getResultList();
        List<BookDTO> booksDTO = new ArrayList<>();
        
        for(Book bookEntity: booksEntity){
            booksDTO.add(new BookDTO(bookEntity));
        }
        return booksDTO;
    }

    /**
    * getSingleBook function is used to get the information of the a specific book
    * return BookDTO object
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

    /**
    * createBook function is used to create a book record
    * return a Book object
    */
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

    /**
    * updateSale is used to update the sale information of the book
    * return a BookDTO object
    */
    @Override
    public BookDTO updateSale(String username, BookDTO salesData) {
        User seller = userBean.getUserEntity(username);

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
        typedQuery.setParameter("bookId", book.getId());
        typedQuery.setParameter("sellerId", seller.getUsername());
        List<BookSales> existingSales = typedQuery.getResultList();
        
        // add new sales
        for (BookSaleDTO saleData: salesData.getSales()){
            if (saleData.getId() == 0){
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
        }
        
        // update & remove old sales
        for(BookSales saleEntity: existingSales){
            BookSaleDTO matched = null;
            for (BookSaleDTO saleDTO: salesData.getSales()){
                if (saleEntity.getSalesId() == saleDTO.getId()){
                    matched = saleDTO;
                    break;
                }
            }
            
            // update
            if (matched != null){
                salesData.getSales().remove(matched);
                saleEntity.setCondition(matched.getBookCondition());
                saleEntity.setPrice(matched.getPrice());
                saleEntity.setQuantity(matched.getQuantity());
                em.persist(saleEntity);
                
            } else { // remove
                seller.getSellingBooks().remove(saleEntity);
                book.getSales().remove(saleEntity);
                em.remove(saleEntity);
            }
        }

        return new BookDTO(book, book.getSales());
    }
    
    /**
    * getSales function is used to get the list of the sales in the order of the time
    * return a list of BookSaleDTO objects
    */
    @Override
    public List<BookSaleDTO> getSales(List<Long> saleIds) {
        TypedQuery<BookSales> typedQuery = em.createNamedQuery("BookSales.findSalesByIds", BookSales.class);
        typedQuery.setParameter("saleIds", saleIds);
        List<BookSales> bookSalesEntity = typedQuery.getResultList();
        
        List<BookSaleDTO> bookSalesDTO = new ArrayList<>();
        bookSalesEntity.stream().forEach(saleEntity -> {
            saleIds.remove(saleEntity.getSalesId());
            bookSalesDTO.add(new BookSaleDTO(saleEntity));
        });
        
        return bookSalesDTO;
    }
}
