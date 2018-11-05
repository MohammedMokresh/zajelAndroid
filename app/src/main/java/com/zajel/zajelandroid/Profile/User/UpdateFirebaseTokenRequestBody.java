
package com.zajel.zajelandroid.Profile.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateFirebaseTokenRequestBody {

    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateFirebaseTokenRequestBody() {
    }

    /**
     * 
     * @param user
     */
    public UpdateFirebaseTokenRequestBody(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
