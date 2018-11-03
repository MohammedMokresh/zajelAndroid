
package com.zajel.zajelandroid.BookList.Borrow.BorrowAndCancelBookResponseBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BorrowBookResponseBody {

    @SerializedName("status")
    @Expose
    private Status status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BorrowBookResponseBody() {
    }

    /**
     * 
     * @param status
     */
    public BorrowBookResponseBody(Status status) {
        super();
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
