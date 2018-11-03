package com.zajel.zajelandroid.BookList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.BookList.BooksModels.Book;
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
        final Book item = getItem(position);

        if (item != null) {
            ViewGroup.LayoutParams rlp = holder.searchItemCardView.getLayoutParams();
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if (position == 1) {
                holder.setIsRecyclable(false);
                Resources r = mCtx.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        290,
                        r.getDisplayMetrics()
                );
                rlp.height = px;
            } else {
                holder.setIsRecyclable(true);
                Resources r = mCtx.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        250,
                        r.getDisplayMetrics()
                );
                rlp.height = px;
            }

            holder.searchItemCardView.setLayoutParams(rlp);
            Picasso.with(mCtx)
                    .load(item.getImage())
                    .fit()
                    .into(holder.dynamicHeightImageView);


            holder.searchItemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(mCtx,BookDetailsActivity.class);
                    i.putExtra("image",item.getImage());
                    i.putExtra("bookName",item.getTitle());
                    i.putExtra("author",item.getAuthor());
                    i.putExtra("pageCount",item.getPageNumber().toString());
                    i.putExtra("language",item.getLanguage());
                    i.putExtra("publishingYear",item.getPublishingYear());
                    i.putExtra("genre",item.getGenre());
                    i.putExtra("status",item.getStatus());
                    i.putExtra("description",item.getDescription());

                    mCtx.startActivity(i);
                }
            });

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