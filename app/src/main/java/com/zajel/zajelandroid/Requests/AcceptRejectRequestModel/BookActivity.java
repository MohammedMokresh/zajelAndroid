
package com.zajel.zajelandroid.Requests.AcceptRejectRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookActivity {

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BookActivity(String status) {
        this.status = status;
    }
}
