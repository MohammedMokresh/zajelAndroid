
package com.zajel.zajelandroid.Book.AddBookModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookResponseModel {

    @SerializedName("book")
    @Expose
    private ResponseBook responseBook;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddBookResponseModel() {
    }

    /**
     * 
     * @param responseBook
     */
    public AddBookResponseModel(ResponseBook responseBook) {
        super();
        this.responseBook = responseBook;
    }

    public ResponseBook getResponseBook() {
        return responseBook;
    }

    public void setResponseBook(ResponseBook responseBook) {
        this.responseBook = responseBook;
    }

}
