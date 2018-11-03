package com.zajel.zajelandroid.BookList;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zajel.zajelandroid.Utils.ZajelUtils.stringToDateGetTheYear;

public class BookDetailsActivity extends AppCompatActivity {
    @BindView(R.id.background_ImageView)
    SimpleDraweeView backgroundImageView;
    @BindView(R.id.book_ImageView)
    ImageView bookImageView;
    @BindView(R.id.book_name_TextView)
    AppCompatTextView bookNameTextView;
    @BindView(R.id.author_name_TextView)
    AppCompatTextView authorNameTextView;
    @BindView(R.id.page_count_TextView)
    AppCompatTextView pageCountTextView;
    @BindView(R.id.language_TextView)
    AppCompatTextView languageTextView;
    @BindView(R.id.genre_TextView)
    AppCompatTextView genreTextView;
    @BindView(R.id.status_TextView)
    AppCompatTextView statusTextView;
    @BindView(R.id.description_TextView)
    AppCompatTextView descriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details2);
        ButterKnife.bind(this);

        Postprocessor postprocessor = new IterativeBoxBlurPostProcessor(50);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(getIntent().getStringExtra("image")))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(backgroundImageView.getController())
                .build();
        backgroundImageView.setController(controller);


        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image")).fit().centerCrop().into(bookImageView);
        bookNameTextView.setText(getIntent().getStringExtra("bookName") + " (" + stringToDateGetTheYear(getIntent().getStringExtra("publishingYear")) + ")");
        authorNameTextView.setText(getIntent().getStringExtra("author"));
        pageCountTextView.setText(getIntent().getStringExtra("pageCount") + " page");
        languageTextView.setText(getIntent().getStringExtra("language"));
        genreTextView.setText(getIntent().getStringExtra("genre"));
        statusTextView.setText(getIntent().getStringExtra("status"));
        descriptionTextView.setText(getIntent().getStringExtra("description"));
    }

}
