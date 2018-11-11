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
public class ReceiveRequestsFragment extends Fragment implements  APIManager.RequestsResponse  , ReceiveRequestsAdapter.RecieveRequestAdapterListener {
    @BindView(R.id.receive_request_recyclerView)
    RecyclerView receiveRequestRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeToRefresh;

    APIManager apiManager;

    ReceiveRequestsAdapter receiveRequestsAdapter;
    List<BookActivity> requestsList;

    public ReceiveRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_receive_requests, container, false);

        ButterKnife.bind(this,v);
        apiManager= new APIManager(getContext());
        apiManager.setRequestsResponse(this);
        apiManager.getReceiveRequest();
        requestsList= new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        receiveRequestRecyclerView.setLayoutManager(mLayoutManager);
        swipeToRefresh.setRefreshing(true);

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiManager.getReceiveRequest();
            }
        });
        return v;
    }

    public static ReceiveRequestsFragment newInstance() {

        Bundle args = new Bundle();

        ReceiveRequestsFragment fragment = new ReceiveRequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getRequestResponse(Requests requests) {
        swipeToRefresh.setRefreshing(false);
        if (requests.getBookActivities().size()!=0){
            requestsList.addAll(requests.getBookActivities());
        }

        receiveRequestsAdapter = new ReceiveRequestsAdapter(getContext(),requestsList,this);
        receiveRequestRecyclerView.setAdapter(receiveRequestsAdapter);

    }

    @Override
    public void errorOccureRequest() {
        swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void acceptButtonOnClick(View v, int position) {

    }

    @Override
    public void rejectButtonOnClick(View v, int position) {

    }

    @Override
    public void messageButtonOnClick(View v, int position) {

    }
}
