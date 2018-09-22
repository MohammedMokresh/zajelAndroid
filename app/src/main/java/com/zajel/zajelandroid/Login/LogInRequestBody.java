package com.zajel.zajelandroid.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInRequestBody {


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public LogInRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
