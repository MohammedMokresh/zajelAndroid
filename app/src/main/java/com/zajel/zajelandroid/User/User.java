
package com.zajel.zajelandroid.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param fcmToken
     */
    public User(String fcmToken) {
        super();
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}
