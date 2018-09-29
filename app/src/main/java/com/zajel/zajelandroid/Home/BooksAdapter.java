package com.zajel.zajelandroid.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.Home.BooksModels.Book;
import com.zajel.zajelandroid.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter  extends PagedListAdapter<Book, BooksAdapter.ItemViewHolder> {

    private Context mCtx;

    BooksAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.book_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Book item = getItem(position);

        if (item != null) {
            ViewGroup.LayoutParams rlp = holder.searchItemCardView.getLayoutParams();
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if (position == 1) {
                holder.setIsRecyclable(false);
                rlp.height = 1000;
            } else {
                holder.setIsRecyclable(true);
                rlp.height = 800;
            }

            holder.searchItemCardView.setLayoutParams(rlp);
            Picasso.with(mCtx)
                    .load(item.getImage())
                    .fit()
                    .centerCrop()
                    .into(holder.dynamicHeightImageView);

        }else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    private static DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(Book oldItem, Book newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Book oldItem, Book newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView searchItemCardView;
        ImageView dynamicHeightImageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            searchItemCardView = itemView.findViewById(R.id.search_item_card);
            dynamicHeightImageView = itemView.findViewById(R.id.dynamic_height_image_view);
        }
    }
}