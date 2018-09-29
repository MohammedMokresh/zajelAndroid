
package com.zajel.zajelandroid.Home.BooksModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Books {

    @SerializedName("books")
    @Expose
    private List<Book> books = null;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
