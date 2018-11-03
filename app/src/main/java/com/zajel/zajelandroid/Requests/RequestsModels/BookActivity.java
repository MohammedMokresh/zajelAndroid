
package com.zajel.zajelandroid.Requests.RequestsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookActivity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("book")
    @Expose
    private Book book;
    @SerializedName("borrower")
    @Expose
    private Borrower borrower;
    @SerializedName("lender")
    @Expose
    private Lender lender;
    @SerializedName("status")
    @Expose
    private String approved;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookActivity() {
    }

    /**
     * 
     * @param id
     * @param approved
     * @param borrower
     * @param book
     * @param lender
     */
    public BookActivity(Integer id, Book book, Borrower borrower, Lender lender, String approved) {
        super();
        this.id = id;
        this.book = book;
        this.borrower = borrower;
        this.lender = lender;
        this.approved = approved;
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

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

}
