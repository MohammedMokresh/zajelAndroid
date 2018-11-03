package com.zajel.zajelandroid.Requests;


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
import com.zajel.zajelandroid.Requests.RequestsModels.BookActivity;
import com.zajel.zajelandroid.Requests.RequestsModels.Requests;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentRequestsFragment extends Fragment implements  APIManager.RequestsResponse {
    @BindView(R.id.sent_request_recyclerView)
    RecyclerView sentRequestRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeToRefresh;

    private APIManager apiManager;

    private SentRequestAdapter sentRequestAdapter;
    private List<BookActivity> requestsList;
    public SentRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_sent_requests, container, false);
        ButterKnife.bind(this,v);

        apiManager= new APIManager(getContext());
        apiManager.setRequestsResponse(this);
        apiManager.getSendRequest();
        requestsList= new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sentRequestRecyclerView.setLayoutManager(mLayoutManager);
        swipeToRefresh.setRefreshing(true);

        swipeToRefresh.setOnRefreshListener(() -> apiManager.getSendRequest());
        return v;
    }

    static SentRequestsFragment newInstance() {

        Bundle args = new Bundle();

        SentRequestsFragment fragment = new SentRequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void getRequestResponse(Requests requests) {


        if (requests.getBookActivities().size()!=0){
            requestsList.addAll(requests.getBookActivities());
        }

        sentRequestAdapter = new SentRequestAdapter(getContext(),requestsList);
        sentRequestRecyclerView.setAdapter(sentRequestAdapter);
        swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void errorOccureRequest() {

        swipeToRefresh.setRefreshing(false);

    }
}
