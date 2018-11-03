
package com.zajel.zajelandroid.BookList.Borrow.BorrowBookRequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookActivity {

    @SerializedName("book_id")
    @Expose
    private Integer bookId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookActivity() {
    }

    /**
     * 
     * @param bookId
     */
    public BookActivity(Integer bookId) {
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
