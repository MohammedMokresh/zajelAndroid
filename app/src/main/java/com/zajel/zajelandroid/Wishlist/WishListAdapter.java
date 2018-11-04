package com.zajel.zajelandroid.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.BookList.BookDetailsActivity;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.Wishlist.WishlistModels.Wishlist;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListAdapterHolder> {

    private Context context;
    private List<Wishlist> whishlist;


    public WishListAdapter(Context context, List<Wishlist> whishlist) {
        this.context = context;
        this.whishlist = whishlist;


    }

    public WishListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.item_wish_list_adapter, null);
        return new WishListAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final WishListAdapter.WishListAdapterHolder holder, final int position) {

        holder.bookNameTextView.setText(whishlist.get(position).getBook().getTitle());
        holder.bookOwnerTextView.setText(whishlist.get(position).getBook().getAuthor());
        Picasso.get().load(whishlist.get(position).getBook().getImage()).into(holder.bookImageView);

        createStatusChip(holder.statusChip,position);


        holder.wishListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(context,BookDetailsActivity.class);
                i.putExtra("in_wishlist",whishlist.get(position).getBook().isInWishList());
                i.putExtra("borrowed",whishlist.get(position).getBook().isInWishList());
                i.putExtra("user_id",whishlist.get(position).getBook().getUserId());
                i.putExtra("bookId",whishlist.get(position).getBook().getId());
                i.putExtra("image",whishlist.get(position).getBook().getImage());
                i.putExtra("bookName",whishlist.get(position).getBook().getTitle());
                i.putExtra("author",whishlist.get(position).getBook().getAuthor());
                i.putExtra("pageCount",whishlist.get(position).getBook().getPageNumber().toString());
                i.putExtra("language",whishlist.get(position).getBook().getLanguage());
                i.putExtra("publishingYear",whishlist.get(position).getBook().getPublishingYear());
                i.putExtra("genre",whishlist.get(position).getBook().getGenre());
                i.putExtra("status",whishlist.get(position).getBook().getStatus());
                i.putExtra("description",whishlist.get(position).getBook().getDescription());

                context.startActivity(i);
            }
        });




    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public int getItemCount() {
        return whishlist.size();
    }

    public void createStatusChip(FlexboxLayout mLlChips, int position) {
        mLlChips.removeAllViews();

        TextView chipStatus = createChips(mLlChips);
        if (whishlist.get(position).getBook().getStatus().equals("available") ) {
            chipStatus.setText("Available");
            chipStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_green_test));
        } else {
            chipStatus.setText("Not Available");
            chipStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_rounded_grey_5));

        }
        chipStatus.setTextColor(context.getResources().getColor(R.color.white0));


    }

    public TextView createChips(FlexboxLayout mFlexboxLayout) {
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

    public class WishListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView bookNameTextView, bookOwnerTextView;
        public ImageView bookImageView;
        public FlexboxLayout statusChip;
        public RelativeLayout wishListRelativeLayout;
        public WishListAdapterHolder(View view) {
            super(view);

            bookNameTextView = view.findViewById(R.id.book_name_TextView);
            bookOwnerTextView = view.findViewById(R.id.book_owner_name_TextView);
            bookImageView = view.findViewById(R.id.book_ImageView);
            statusChip = view.findViewById(R.id.status_chips);
            wishListRelativeLayout= view.findViewById(R.id.wishList_RelativeLayout);

        }
    }


}


