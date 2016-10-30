package au.edu.uts.aip.domain.entity;

import java.io.Serializable;

public class BookSellerId implements Serializable {
    private long book;
    private long seller;

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
    
    @Override
    public int hashCode(){
        return (int)(book + seller);
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof BookSellerId){
            BookSellerId otherBookId = (BookSellerId)object;
            return (otherBookId.seller == this.seller)
                    && (otherBookId.book == this.seller);
        }
        return false;
    }
}
