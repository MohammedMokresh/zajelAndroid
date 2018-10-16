
package com.zajel.zajelandroid.Book.AddBookModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookRequestModel {

    @SerializedName("book")
    @Expose
    private RequestBook requestBook;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddBookRequestModel() {
    }

    /**
     * 
     * @param requestBook
     */
    public AddBookRequestModel(RequestBook requestBook) {
        super();
        this.requestBook = requestBook;
    }

    public RequestBook getRequestBook() {
        return requestBook;
    }

    public void setRequestBook(RequestBook requestBook) {
        this.requestBook = requestBook;
    }

}
