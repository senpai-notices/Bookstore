package au.edu.uts.aip.domain.dto;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookDTO implements Serializable {

    private long id;
    private String title;
    private String author;
    private int publishYear;
    private String publisher;
    private String imgPath;
    private String isbn10;
    private String isbn13;

    private int pageCount;
    private List<BookSaleDTO> sales;

    public BookDTO() {

    }

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

    public BookDTO(Book bookEntity, List<BookSales> salesEntity) {
        this(bookEntity);

        this.sales = new ArrayList<>();
        for (BookSales saleEntity : salesEntity) {
            this.sales.add(new BookSaleDTO(saleEntity));
        }
    }

    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    @XmlElement
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @XmlElement
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @XmlElement
    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @XmlElement
    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @XmlElement
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @XmlElement
    public List<BookSaleDTO> getSales() {
        return sales;
    }

    public void setSales(List<BookSaleDTO> sales) {
        this.sales = sales;
    }
}
