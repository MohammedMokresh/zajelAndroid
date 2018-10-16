
package com.zajel.zajelandroid.Wishlist.WishlistModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListResponseBody {

    @SerializedName("wishlists")
    @Expose
    private List<Wishlist> wishlists = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WishListResponseBody() {
    }

    /**
     * 
     * @param wishlists
     */
    public WishListResponseBody(List<Wishlist> wishlists) {
        super();
        this.wishlists = wishlists;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

}
