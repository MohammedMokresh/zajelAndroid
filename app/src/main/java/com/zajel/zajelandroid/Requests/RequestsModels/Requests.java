package com.zajel.zajelandroid.Requests.RequestsModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requests {

    @SerializedName("book_activities")
    @Expose
    private List<BookActivity> bookActivities = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Requests() {
    }

    /**
     * 
     * @param bookActivities
     */
    public Requests(List<BookActivity> bookActivities) {
        super();
        this.bookActivities = bookActivities;
    }

    public List<BookActivity> getBookActivities() {
        return bookActivities;
    }

    public void setBookActivities(List<BookActivity> bookActivities) {
        this.bookActivities = bookActivities;
    }

}
