package com.zajel.zajelandroid.BookList;

import com.zajel.zajelandroid.BookList.BooksModels.Book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class BooksViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<Book>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Book>> liveDataSource;

    //constructor
    public BooksViewModel() {
        //getting our data source factory
        BooksDataSourceFactory itemDataSourceFactory = new BooksDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(BooksDataSource.PAGE_SIZE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}