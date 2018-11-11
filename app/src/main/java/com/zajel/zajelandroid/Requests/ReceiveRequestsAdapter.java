package com.zajel.zajelandroid.Requests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.Requests.RequestsModels.BookActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ReceiveRequestsAdapter extends RecyclerView.Adapter<ReceiveRequestsAdapter.ReceiveRequestAdapterHolder> {
    public RecieveRequestAdapterListener recieveRequestAdapterListener;
    private Context context;
    private List<BookActivity> bookActivityList;

    public ReceiveRequestsAdapter(Context context, List<BookActivity> bookActivityList,RecieveRequestAdapterListener recieveRequestAdapterListener) {
        this.context = context;
        this.bookActivityList = bookActivityList;
        this.recieveRequestAdapterListener=recieveRequestAdapterListener;

    }

    public ReceiveRequestAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.item_receive_request_list, null); //item_my_request_page
        return new ReceiveRequestAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReceiveRequestsAdapter.ReceiveRequestAdapterHolder holder, final int position) {
        holder.bookNameTextView.setText(bookActivityList.get(position).getBook().getTitle());
        bookActivityList.get(position).getId();
        holder.userNameTextView.setText(bookActivityList.get(position).getBorrower().getFirstName() + " " + bookActivityList.get(position).getBorrower().getLastName());
        Picasso.get().load(bookActivityList.get(position).getBorrower().getImage()).into(holder.userImageView);

        holder.acceptButton.setOnClickListener(v -> {recieveRequestAdapterListener.acceptButtonOnClick(v,position); });
        holder.rejectButton.setOnClickListener(v -> {recieveRequestAdapterListener.rejectButtonOnClick(v,position); });
//        holder..setOnClickListener(v -> {recieveRequestAdapterListener.acceptButtonOnClick(v,position); });

    }

    @Override
    public int getItemCount() {
        return bookActivityList.size();
    }


    public interface RecieveRequestAdapterListener {

        void acceptButtonOnClick(View v, int position);

        void rejectButtonOnClick(View v, int position);

        void messageButtonOnClick(View v, int position);
    }

    public static class ReceiveRequestAdapterHolder extends RecyclerView.ViewHolder {
        public TextView userNameTextView, bookNameTextView;
        public ImageView userImageView;
        public Button acceptButton, rejectButton;


        public ReceiveRequestAdapterHolder(View view) {
            super(view);
            userImageView = view.findViewById(R.id.profile_ImageView);
            userNameTextView = view.findViewById(R.id.user_name_TextView);
            bookNameTextView = view.findViewById(R.id.book_name_TextView);
            acceptButton = view.findViewById(R.id.accept_Button);
            rejectButton = view.findViewById(R.id.reject_Button);

        }
    }
}


