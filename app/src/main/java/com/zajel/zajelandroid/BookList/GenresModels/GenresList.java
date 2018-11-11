package com.zajel.zajelandroid.BookList.GenresModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresList
{
    @SerializedName("genres")
    @Expose
    private List<Genres> genres = null;

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}