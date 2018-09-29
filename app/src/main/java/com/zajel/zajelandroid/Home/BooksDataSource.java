package com.zajel.zajelandroid.Home;


import com.zajel.zajelandroid.APIManager.NetworkService;
import com.zajel.zajelandroid.Home.BooksModels.Book;
import com.zajel.zajelandroid.Home.BooksModels.Books;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksDataSource extends PageKeyedDataSource<Integer, Book> {

    //the size of a page that we want
    public static final int PAGE_SIZE = 20;
    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow

    private final NetworkService networkService;

    public BooksDataSource() {
        networkService = new NetworkService();
    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Book> callback) {
        networkService
                .getAPI().getBooks(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,FIRST_PAGE)
                .enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getBooks(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {

                    }
                });
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Book> callback) {
      networkService
                .getAPI().getBooks(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,params.key)
                .enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {

                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            //passing the loaded data
                            //and the previous page key
                            callback.onResult(response.body().getBooks(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {

                    }
                });
    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Book> callback) {
        networkService
                .getAPI()
                .getBooks(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,params.key)
                .enqueue(new Callback<Books>() {
                    @Override
                    public void onResponse(Call<Books> call, Response<Books> response) {

                        if (response.body() != null) {
                            //if the response has next page

                            //incrementing the next page number
                            Integer key = !response.body().getBooks().isEmpty() ? params.key + 1 : null;

                            //passing the loaded data and next page value
                            callback.onResult(response.body().getBooks(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Books> call, Throwable t) {

                    }
                });
    }
}