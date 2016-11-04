package au.edu.uts.aip.domain.entity;

import java.io.Serializable;

public class BookSaleId implements Serializable {

    private long bookId;
    private long sellerId;
    private double price;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return (int) (bookId + sellerId + price);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BookSaleId) {
            BookSaleId otherBookId = (BookSaleId) object;
            return (otherBookId.sellerId == this.sellerId)
                    && (otherBookId.bookId == this.sellerId)
                    && (otherBookId.price == this.price);
        }
        return false;
    }
}
