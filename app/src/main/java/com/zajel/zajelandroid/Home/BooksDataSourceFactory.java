package com.zajel.zajelandroid.Home;

import com.zajel.zajelandroid.Home.BooksModels.Book;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class BooksDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Book>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Book> create() {
        //getting our data source object
        BooksDataSource itemDataSource = new BooksDataSource();

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