package com.zajel.zajelandroid.BookList;

import android.util.Log;

import com.zajel.zajelandroid.BookList.BooksModels.Book;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class BooksDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Book>> itemLiveDataSource = new MutableLiveData<>();


    String genre;

    public BooksDataSourceFactory(String genre) {
        this.genre = genre;
    }

    @Override
    public DataSource<Integer, Book> create() {
        //getting our data source object
        BooksDataSource itemDataSource = new BooksDataSource(genre);

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Book>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}