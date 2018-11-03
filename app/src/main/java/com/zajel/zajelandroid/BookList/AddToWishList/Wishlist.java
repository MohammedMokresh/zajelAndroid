
package com.zajel.zajelandroid.BookList.AddToWishList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wishlist {

    @SerializedName("book_id")
    @Expose
    private Integer bookId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wishlist() {
    }

    /**
     * 
     * @param bookId
     */
    public Wishlist(Integer bookId) {
        super();
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

}
