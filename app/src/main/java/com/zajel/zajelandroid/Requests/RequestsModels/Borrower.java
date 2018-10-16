
package com.zajel.zajelandroid.Requests.RequestsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Borrower {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Borrower() {
    }

    /**
     * 
     * @param lastName
     * @param email
     * @param birthdate
     * @param image
     * @param firstName
     * @param city
     * @param country
     */
    public Borrower(String email, String firstName, String lastName, String image, String birthdate, String country, String city) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.birthdate = birthdate;
        this.country = country;
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
