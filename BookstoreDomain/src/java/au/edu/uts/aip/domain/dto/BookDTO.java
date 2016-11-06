package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A BookDTO is used to access all the information that is related to a book It is a data transfer
 * object for Book describes the detail of a book It has properties: id, title, author, publishYear,
 * publisher, imgPath, isbn10 and isbn13, pageCount and sales
 *
 * @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@XmlRootElement
public class BookDTO implements Serializable {

    /**
     * The unique ID of the book.
     */
    private long id;
    /**
     * the title of the book
     */
    private String title;
    /**
     * the author of the book
     */
    private String author;
    /**
     * the publishing year of the book
     */
    private int publishYear;
    /**
     * the publisher of the book
     */
    private String publisher;
    /**
     * The storage image location on the internet.
     */
    private String imgPath;
    private String isbn10;
    private String isbn13;

    /**
     * The page count of the book.
     */
    private int pageCount;
    /**
     * The sale records of the book.
     */
    private List<BookSaleDTO> sales;

    /**
     * Empty constructor of the BookDTO class
     */
    public BookDTO() {

    }

    /**
     * Constructor of the BookDTO class with parameters the parameter passed to the constructor is
     * the Book object
     *
     * @param bookEntity
     */
    public BookDTO(Book bookEntity) {
        this.id = bookEntity.getId();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
        this.publishYear = bookEntity.getPublishYear();
        this.publisher = bookEntity.getPublisher();
        this.imgPath = bookEntity.getImgPath();
        this.isbn10 = bookEntity.getIsbn10();
        this.isbn13 = bookEntity.getIsbn13();
        this.pageCount = bookEntity.getPageCount();
    }

    /**
     * constructor of the BookDTO class with parameters the parameter passed to the constructor:
     * first parameter is book obejct second parameter is a book sales list
     *
     * @param bookEntity
     * @param salesEntity
     */
    public BookDTO(Book bookEntity, List<BookSales> salesEntity) {
        this(bookEntity);

        this.sales = new ArrayList<>();
        for (BookSales saleEntity : salesEntity) {
            this.sales.add(new BookSaleDTO(saleEntity));
        }
    }

    /**
     * {@link BookDTO#id}
     * @return 
     */
    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * {@link BookDTO#title}
     * @return title
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * {@link BookDTO#author}
     * @return author
     */
    @XmlElement
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * {@link BookDTO#publishYear}
     * @return publishYear
     */
    @XmlElement
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * {@link BookDTO#publisher}
     * @return publisher
     */
    @XmlElement
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * {@link BookDTO#imgPath}
     * @return imgPath
     */
    @XmlElement
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * {@link BookDTO#isbn10}
     * @return isbn10
     */
    @XmlElement
    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    /**
     * {@link BookDTO#isbn13}
     * @return isbn13
     */
    @XmlElement
    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     * {@link BookDTO#pageCount}
     * @return pageCount
     */
    @XmlElement
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * {@link BookDTO#sales}
     * @return sales
     */
    @XmlElement
    public List<BookSaleDTO> getSales() {
        return sales;
    }

    public void setSales(List<BookSaleDTO> sales) {
        this.sales = sales;
    }
}
