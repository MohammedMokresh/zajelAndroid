
package com.zajel.zajelandroid.Requests.AcceptRejectRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptRejectRequestRequestBody {

    @SerializedName("book_activity")
    @Expose
    private BookActivity bookActivity;

    public BookActivity getBookActivity() {
        return bookActivity;
    }

    public void setBookActivity(BookActivity bookActivity) {
        this.bookActivity = bookActivity;
    }

    public AcceptRejectRequestRequestBody(BookActivity bookActivity) {
        this.bookActivity = bookActivity;
    }
}
