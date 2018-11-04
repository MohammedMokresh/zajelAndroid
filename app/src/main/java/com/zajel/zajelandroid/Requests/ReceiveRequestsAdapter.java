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

public class ReceiveRequestsAdapter extends RecyclerView.Adapter<ReceiveRequestsAdapter.SentRequestAdapterHolder> {
    public MyAdapterListener onClickListener;
    private Context context;
    private List<BookActivity> bookActivityList;

    public ReceiveRequestsAdapter(Context context, List<BookActivity> bookActivityList) {
        this.context = context;
        this.bookActivityList = bookActivityList;

    }

    public SentRequestAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.item_receive_request_list, null); //item_my_request_page
        return new SentRequestAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReceiveRequestsAdapter.SentRequestAdapterHolder holder, final int position) {
        holder.bookNameTextView.setText(bookActivityList.get(position).getBook().getTitle());
        holder.userNameTextView.setText(bookActivityList.get(position).getBorrower().getFirstName() + " " + bookActivityList.get(position).getBorrower().getLastName());
        Picasso.get().load(bookActivityList.get(position).getBorrower().getImage()).into(holder.userImageView);


    }

    @Override
    public int getItemCount() {
        return bookActivityList.size();
    }


    public interface MyAdapterListener {

        void mobileImageViewOnClick(View v, int position);

        void emailImageViewOnClick(View v, int position);

        void confirmButtonViewOnClick(View v, int position);
    }

    public static class SentRequestAdapterHolder extends RecyclerView.ViewHolder {
        public TextView userNameTextView, bookNameTextView;
        public ImageView userImageView;
        public Button acceptButton, rejectButton;


        public SentRequestAdapterHolder(View view) {
            super(view);
            userImageView = view.findViewById(R.id.profile_ImageView);
            userNameTextView = view.findViewById(R.id.user_name_TextView);
            bookNameTextView = view.findViewById(R.id.book_name_TextView);
            acceptButton = view.findViewById(R.id.accept_Button);
            rejectButton = view.findViewById(R.id.reject_Button);

        }
    }
}


