package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSale;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.dto.BookDTO;
import au.edu.uts.aip.domain.dto.BookSaleDTO;
import au.edu.uts.aip.domain.remote.BookSaleRemote;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @inheritDoc
 */
@Stateless
public class BookSaleBean implements BookSaleRemote {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserBean userBean;

    /**
     * @inheritDoc
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

        TypedQuery<BookSale> typedQuery
                = em.createNamedQuery("BookSale.findSales", BookSale.class);
        typedQuery.setParameter("bookId", book.getId());
        typedQuery.setParameter("sellerId", seller.getUsername());
        List<BookSale> existingSales = typedQuery.getResultList();

        // add new sales
        for (BookSaleDTO saleData : salesData.getSales()) {
            if (saleData.getId() == 0) {
                BookSale newSale = new BookSale();
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
        for (BookSale saleEntity : existingSales) {
            BookSaleDTO matched = null;
            for (BookSaleDTO saleDTO : salesData.getSales()) {
                if (saleEntity.getSalesId() == saleDTO.getId()) {
                    matched = saleDTO;
                    break;
                }
            }

            // update
            if (matched != null) {
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
     * @inheritDoc
     */
    @Override
    public List<BookSaleDTO> getSales(List<Long> saleIds) {
        TypedQuery<BookSale> typedQuery = em.createNamedQuery("BookSale.findSalesByIds", BookSale.class);
        typedQuery.setParameter("saleIds", saleIds);
        List<BookSale> bookSalesEntity = typedQuery.getResultList();

        List<BookSaleDTO> bookSalesDTO = new ArrayList<>();
        bookSalesEntity.stream().forEach(saleEntity -> {
            saleIds.remove(saleEntity.getSalesId());
            bookSalesDTO.add(new BookSaleDTO(saleEntity));
        });

        return bookSalesDTO;
    }

    /**
     * @inheritDoc
     */
    @Override
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
}
