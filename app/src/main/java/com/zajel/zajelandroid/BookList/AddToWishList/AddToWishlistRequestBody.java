
package com.zajel.zajelandroid.BookList.AddToWishList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToWishlistRequestBody {

    @SerializedName("wishlist")
    @Expose
    private Wishlist wishlist;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddToWishlistRequestBody() {
    }

    /**
     * 
     * @param wishlist
     */
    public AddToWishlistRequestBody(Wishlist wishlist) {
        super();
        this.wishlist = wishlist;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

}
