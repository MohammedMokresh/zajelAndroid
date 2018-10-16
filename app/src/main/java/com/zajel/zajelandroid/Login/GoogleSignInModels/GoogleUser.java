
package com.zajel.zajelandroid.Login.GoogleSignInModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleUser {

    @SerializedName("user")
    @Expose
    private User user;

    public GoogleUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
