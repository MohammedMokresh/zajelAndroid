package com.zajel.zajelandroid.Wishlist;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.Requests.SentRequestAdapter;
import com.zajel.zajelandroid.Wishlist.WishlistModels.Book;
import com.zajel.zajelandroid.Wishlist.WishlistModels.WishListResponseBody;
import com.zajel.zajelandroid.Wishlist.WishlistModels.Wishlist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment  implements APIManager.WishlistResponse {
    @BindView(R.id.wishlist_recyclerView)
    RecyclerView wishListRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeToRefresh;

    private APIManager apiManager;
    private WishListAdapter wishListAdapter;
    private List<Wishlist> wishlistList;

    public WishlistFragment() {
        // Required empty public constructor
    }

    public static WishlistFragment newInstance() {

        Bundle args = new Bundle();

        WishlistFragment fragment = new WishlistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_wishlist, container, false);
        ButterKnife.bind(this,v);
        apiManager= new APIManager(getContext());
        apiManager.setWishlistResponse(this);
        apiManager.getWishlist();


        wishlistList= new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        wishListRecyclerView.setLayoutManager(mLayoutManager);
        swipeToRefresh.setRefreshing(true);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiManager.getWishlist();
            }
        });
        return v;
    }

    @Override
    public void getWishlist(WishListResponseBody wishListResponseBody) {
        wishlistList= new ArrayList<>();
        if (wishListResponseBody.getWishlists().size()!=0){
            for (int i = 0; i <wishListResponseBody.getWishlists().size();i++){
                wishlistList.add(wishListResponseBody.getWishlists().get(i));
            }

        }

        wishListAdapter = new WishListAdapter(getContext(),wishlistList);
        wishListRecyclerView.setAdapter(wishListAdapter);
        swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void errorOccureWishlist() {
        swipeToRefresh.setRefreshing(false);
    }
}
