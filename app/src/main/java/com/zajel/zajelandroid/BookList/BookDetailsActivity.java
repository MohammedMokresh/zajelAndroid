package com.zajel.zajelandroid.BookList;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.BookList.Borrow.BorrowAndCancelBookResponseBody.BorrowBookResponseBody;
import com.zajel.zajelandroid.BookList.Borrow.BorrowBookRequestBody.BookActivity;
import com.zajel.zajelandroid.BookList.Borrow.BorrowBookRequestBody.BorrowBookRequestBody;
import com.zajel.zajelandroid.Dialogs.DialogUtil;
import com.zajel.zajelandroid.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zajel.zajelandroid.Utils.ZajelUtils.stringToDateGetTheYear;

public class BookDetailsActivity extends AppCompatActivity implements APIManager.BorrowBookResponse {
    @BindView(R.id.background_ImageView)
    SimpleDraweeView backgroundImageView;
    @BindView(R.id.book_ImageView)
    SimpleDraweeView bookImageView;
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
    @BindView(R.id.boorow_book_Button)
    AppCompatButton borrowBookButton;

    APIManager apiManager;

    View parentLayout;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details2);
        ButterKnife.bind(this);
         parentLayout = findViewById(android.R.id.content);
        apiManager= new APIManager(getApplicationContext());
        apiManager.setBorrowBookResponse(this);


        // the image blured
        Postprocessor postprocessor = new IterativeBoxBlurPostProcessor(50);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(getIntent().getStringExtra("image")))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(backgroundImageView.getController())
                .build();
        backgroundImageView.setController(controller);

        ////// the book cover
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
        bookImageView.setHierarchy(new GenericDraweeHierarchyBuilder(getResources())
                .setRoundingParams(roundingParams)
                .build());
        PipelineDraweeController controller2 = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(bookImageView.getController())
                .build();
        bookImageView.setController(controller2);


        // rest of the fields
        Integer bookId=getIntent().getIntExtra("bookId",0);
        bookNameTextView.setText(getIntent().getStringExtra("bookName") + " (" + stringToDateGetTheYear(getIntent().getStringExtra("publishingYear")) + ")");
        authorNameTextView.setText(getIntent().getStringExtra("author"));
        pageCountTextView.setText(getIntent().getStringExtra("pageCount") + " page");
        languageTextView.setText(getIntent().getStringExtra("language"));
        genreTextView.setText(getIntent().getStringExtra("genre"));
        statusTextView.setText(getIntent().getStringExtra("status"));
        descriptionTextView.setText(getIntent().getStringExtra("description"));



        borrowBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showProgressDialog("",getSupportFragmentManager());
                if (borrowBookButton.getText().toString().equals("Borrow")){
                    apiManager.borrowBook(new BorrowBookRequestBody(new BookActivity(bookId)));
                }else {
                    apiManager.cancelBorrowBook(bookId);
                }
            }
        });
    }

    @Override
    public void getBorrowResponse(BorrowBookResponseBody borrowBookResponseBody) {
        Snackbar.make(parentLayout,borrowBookResponseBody.getStatus().getMessage(),Snackbar.LENGTH_LONG).show();
        DialogUtil.removeProgressDialog();
    }

    @Override
    public void errorOccureBorrowBook() {
        DialogUtil.removeProgressDialog();
    }
}
