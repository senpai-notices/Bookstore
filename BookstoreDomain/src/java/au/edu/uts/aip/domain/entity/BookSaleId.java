package au.edu.uts.aip.domain.entity;

import java.io.Serializable;

public class BookSaleId implements Serializable {
    private long book;
    private long seller;
    private double price;

    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public long getSeller() {
        return seller;
    }

    public void setSeller(long seller) {
        this.seller = seller;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public int hashCode(){
        return (int)(book + seller + price);
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof BookSaleId){
            BookSaleId otherBookId = (BookSaleId)object;
            return (otherBookId.seller == this.seller)
                    && (otherBookId.book == this.seller)
                    && (otherBookId.price == this.price);
        }
        return false;
    }
}
