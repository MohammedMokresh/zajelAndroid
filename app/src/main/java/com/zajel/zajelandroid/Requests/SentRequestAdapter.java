package com.zajel.zajelandroid.Requests;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.BookList.BookDetailsActivity;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.Requests.RequestsModels.BookActivity;
import com.zajel.zajelandroid.Requests.RequestsModels.Requests;


import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestAdapter.SentRequestAdapterHolder> {

    private Context context;
    private List<BookActivity> requestsList;

    public class SentRequestAdapterHolder extends RecyclerView.ViewHolder {

        public TextView bookNameTextView, authorTextView;
        public ImageView bookImageView;

        RelativeLayout sentRequestRelativeLayout;
        Button status;

        public SentRequestAdapterHolder(View view) {
            super(view);
            sentRequestRelativeLayout=view.findViewById(R.id.sent_request_relativeLayout);
            bookNameTextView =  view.findViewById(R.id.book_name_TextView);
            authorTextView =  view.findViewById(R.id.author_TextView);
            status =  view.findViewById(R.id.btn_request);
            bookImageView = view.findViewById(R.id.book_ImageView);


        }
    }

    public SentRequestAdapter(Context context, List<BookActivity> requestsList) {
        this.context = context;
        this.requestsList = requestsList;
    }


    public SentRequestAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.item_my_request_page, null);
        return new SentRequestAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final SentRequestAdapter.SentRequestAdapterHolder holder, final int position) {
            holder.bookNameTextView.setText(requestsList.get(position).getBook().getTitle());
            holder.authorTextView.setText(requestsList.get(position).getBook().getAuthor());
        Picasso.with(context).load(requestsList.get(position).getBook().getImage()).into(holder.bookImageView);

        holder.sentRequestRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,BookDetailsActivity.class);
                i.putExtra("bookId",requestsList.get(position).getBook().getId());
                i.putExtra("image",requestsList.get(position).getBook().getImage());
                i.putExtra("bookName",requestsList.get(position).getBook().getTitle());
                i.putExtra("author",requestsList.get(position).getBook().getAuthor());
                i.putExtra("pageCount",requestsList.get(position).getBook().getPageNumber().toString());
                i.putExtra("language",requestsList.get(position).getBook().getLanguage());
                i.putExtra("publishingYear",requestsList.get(position).getBook().getPublishingYear());
                i.putExtra("genre",requestsList.get(position).getBook().getGenre());
                i.putExtra("status",requestsList.get(position).getBook().getStatus());
                i.putExtra("description",requestsList.get(position).getBook().getDescription());
                context.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    private void createJobDetailChips(FlexboxLayout mLlChips, int position, String distance) {
        mLlChips.removeAllViews();

        TextView chipStatus = createChips(mLlChips);
//        if (requestsList.get(position).getStatus() == 1) {
//            chipStatus.setText("Available");
//            chipStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_green_test));
//        } else {
//            chipStatus.setText("Not Available");
//            chipStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_grey_5));
//            chipStatus.setPadding(25, 0, 25, 0);
//        }

        chipStatus.setTextColor(context.getResources().getColor(R.color.white0));

        if (!distance.equals("")) {
            TextView mTvChipType = createChips(mLlChips);
            mTvChipType.setText(distance);
        }

    }

    private TextView createChips(FlexboxLayout mFlexboxLayout) {
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        FlexboxLayout.LayoutParams params = new
                FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, margin, margin);
        TextView mTvChip = new TextView(context);
        mTvChip.setLayoutParams(params);
        mTvChip.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_grey_2));

        mTvChip.setTextColor(Color.parseColor("#323133"));
        mTvChip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mTvChip.setGravity(Gravity.CENTER);
        mFlexboxLayout.addView(mTvChip);
        return mTvChip;

    }


}


