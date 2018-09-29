package com.zajel.zajelandroid.Home;


import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zajel.zajelandroid.Home.BooksModels.Book;
import com.zajel.zajelandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {


    @BindView(R.id.books_RecyclerView)
    RecyclerView booksRecyclerView;

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

        booksRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        booksRecyclerView.setHasFixedSize(true);

        //getting our ItemViewModel
        BooksViewModel itemViewModel = ViewModelProviders.of(this).get(BooksViewModel.class);

        //creating the Adapter
        final BooksAdapter adapter = new BooksAdapter(getActivity());


        //observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Book>>() {
            @Override
            public void onChanged(@Nullable PagedList<Book> items) {

                //in case of any changes
                //submitting the items to adapter
                adapter.submitList(items);
            }
        });

        //setting the adapter
        booksRecyclerView.setAdapter(adapter);

        return v;
    }

}
