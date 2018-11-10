package com.zajel.zajelandroid.BookList;

import android.app.Application;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    String genre;

    Application application;
    public MyViewModelFactory( String genre) {

       this.genre = genre;

    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BooksViewModel(genre);
    }
}