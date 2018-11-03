
package com.zajel.zajelandroid.BookList.Borrow.BorrowBookRequestBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BorrowBookRequestBody {

    @SerializedName("book_activity")
    @Expose
    private BookActivity bookActivity;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BorrowBookRequestBody() {
    }

    /**
     * 
     * @param bookActivity
     */
    public BorrowBookRequestBody(BookActivity bookActivity) {
        super();
        this.bookActivity = bookActivity;
    }

    public BookActivity getBookActivity() {
        return bookActivity;
    }

    public void setBookActivity(BookActivity bookActivity) {
        this.bookActivity = bookActivity;
    }

}
