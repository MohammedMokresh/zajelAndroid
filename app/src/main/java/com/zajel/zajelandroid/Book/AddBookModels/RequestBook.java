
package com.zajel.zajelandroid.Book.AddBookModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestBook {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("publishing_year")
    @Expose
    private String publishingYear;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("page_number")
    @Expose
    private Integer pageNumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("genre")
    @Expose
    private String genre;



    @SerializedName("status")
    @Expose
    private String status;


    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestBook() {
    }

    /**
     * 
     * @param genre
     * @param author
     * @param title
     * @param userId
     * @param image
     * @param pageNumber
     * @param publishingYear
     * @param language
     *
     */
    public RequestBook(String title, String author, String publishingYear, String language, Integer pageNumber, String image, String genre, Integer userId, String status) {
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
        this.language = language;
        this.pageNumber = pageNumber;
        this.image = image;
        this.genre = genre;
        this.status = status;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
