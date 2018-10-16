
package com.zajel.zajelandroid.Wishlist.WishlistModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wishlist {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("book")
    @Expose
    private Book book;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wishlist() {
    }

    /**
     * 
     * @param id
     * @param book
     */
    public Wishlist(Integer id, Book book) {
        super();
        this.id = id;
        this.book = book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
