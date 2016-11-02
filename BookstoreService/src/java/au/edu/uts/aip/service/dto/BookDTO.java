package au.edu.uts.aip.service.dto;

import au.edu.uts.aip.domain.entity.Book;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookDTO implements Serializable {
    @XmlElement
    private long id;
    @XmlElement
    private String title;
    @XmlElement
    private String author;
    @XmlElement
    private int publishYear;
    @XmlElement
    private String publisher;
    @XmlElement
    private String imgPath;
    @XmlElement
    private String isbn10;
    @XmlElement
    private String isbn13;
    @XmlElement
    private int pageCount;

    public BookDTO(){
        
    }
    
    public BookDTO(Book bookEntity){
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
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
