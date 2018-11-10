package com.zajel.zajelandroid.BookList;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.BookList.BooksModels.Book;
import com.zajel.zajelandroid.BookList.GenresModels.GenresList;
import com.zajel.zajelandroid.Dialogs.DialogUtil;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment implements APIManager.GenreListResponse {


    @BindView(R.id.books_RecyclerView)
    RecyclerView booksRecyclerView;
    @BindView(R.id.categories_Button)
    ImageButton categoriesImageButton;


    APIManager apiManager;
    ArrayList<String> genreDataArrayList = new ArrayList<>();
    ArrayList<String> genreIdArrayList = new ArrayList<>();
    BooksViewModel booksViewModel;
    PreferenceManager preferenceManager;
     BooksAdapter adapter ;
    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance() {

        Bundle args = new Bundle();

        BooksFragment fragment = new BooksFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this,v);
        preferenceManager= PreferenceManager.getInstance();
        booksRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        booksRecyclerView.setHasFixedSize(true);
        //getting our ItemViewModel

        booksViewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(preferenceManager.getChoosenCategory())).get(BooksViewModel.class);


//        BooksViewModel itemViewModel = ViewModelProviders.of(this).get(BooksViewModel.class);

        //creating the Adapter
         adapter = new BooksAdapter(getActivity());


        //observing the itemPagedList from view model
        booksViewModel.itemPagedList.observe(this, new Observer<PagedList<Book>>() {
            @Override
            public void onChanged(@Nullable PagedList<Book> items) {

                //in case of any changes
                //submitting the items to adapter
                adapter.submitList(items);
            }
        });

        //setting the adapter
        booksRecyclerView.setAdapter(adapter);



        apiManager= new APIManager(getContext());
        apiManager.setGenreListResponse(this);
        apiManager.getGenreSList();


        categoriesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showChooseCategory(getFragmentManager(), genreDataArrayList, genreIdArrayList);
            }
        });


        IntentFilter intentFilter = new IntentFilter("com.zajel.zajelandroid.chooseCategory");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(onNotice, intentFilter);

        return v;
    }

    @Override
    public void getGeneres(GenresList genresList) {

        genreDataArrayList.add("All");
        genreIdArrayList.add("All");
        if (!genresList.getGenres().isEmpty()){
            for (int i = 0; i <genresList.getGenres().size(); i++){
            genreDataArrayList.add(genresList.getGenres().get(i).getName());
            genreIdArrayList.add(genresList.getGenres().get(i).getName());

            }
        }



    }

    @Override
    public void errorOccureGenre() {

    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                booksViewModel.initTheViewModel(preferenceManager.getChoosenCategory());
                //observing the itemPagedList from view model
                booksViewModel.itemPagedList.observe(BooksFragment.this, new Observer<PagedList<Book>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Book> items) {

                        adapter.submitList(items);
                    }
                });
                adapter.notifyDataSetChanged();



            }catch (Exception e){
                }



        }
    };

}
