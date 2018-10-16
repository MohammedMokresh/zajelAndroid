package com.zajel.zajelandroid.BookList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zajel.zajelandroid.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.70f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.7f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    @BindView(R.id.background_ImageView)
    SimpleDraweeView backgroundImageView;


    @BindView(R.id.book_ImageView)
    ImageView bookImageView;
    @BindView(R.id.book_name_TextView)
    AutoResizeTextView bookNameTextView;
    @BindView(R.id.author_name_TextView)
    TextView authorNameTextView;
    @BindView(R.id.page_count_TextView)
    TextView pageCountTextView;
    @BindView(R.id.language_TextView)
    TextView languageTextView;
    @BindView(R.id.publishing_year_TextView)
    TextView publishingYearTextView;
    @BindView(R.id.genre_TextView)
    TextView genreTextView;
    @BindView(R.id.status_TextView)
    TextView statusTextView;
    @BindView(R.id.description_TextView)
    TextView descriptionTextView;
    @BindView(R.id.book_name2_TextView)
    TextView bookName2TextView;
    @BindView(R.id.author_name2_TextView)
    TextView authorName2TextView;


    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        bindActivity();



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

        bookNameTextView.setText(getIntent().getStringExtra("bookName"));
        bookName2TextView.setText(getIntent().getStringExtra("bookName"));
        authorNameTextView.setText(getIntent().getStringExtra("author"));
        authorName2TextView.setText(getIntent().getStringExtra("author"));
        pageCountTextView.setText(getIntent().getStringExtra("pageCount"));
        languageTextView.setText(getIntent().getStringExtra("language"));
        publishingYearTextView.setText(getIntent().getStringExtra("publishingYear"));
        genreTextView.setText(getIntent().getStringExtra("genre"));
        statusTextView.setText(getIntent().getStringExtra("status"));
        descriptionTextView.setText(getIntent().getStringExtra("description"));

        mAppBarLayout.addOnOffsetChangedListener(this);

//            mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    private void bindActivity() {
        mToolbar = findViewById(R.id.main_toolbar);
        mTitle = findViewById(R.id.book_name2_TextView);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = findViewById(R.id.main_appbar);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        Log.e("per", String.valueOf(percentage));
        float x=1-percentage;
        bookNameTextView.setTextSize(x*14);
        authorNameTextView.setTextSize(x*14);

//        handleAlphaOnTitle(percentage);
//        handleToolbarTitleVisibility(percentage);
    }

//    private void handleToolbarTitleVisibility(float percentage) {
//        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
//
//            if (!mIsTheTitleVisible) {
//                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//
//                mIsTheTitleVisible = true;
//            }
//
//        } else {
//
//            if (mIsTheTitleVisible) {
//                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleVisible = false;
//            }
//        }
//    }

//    private void handleAlphaOnTitle(float percentage) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = false;
//            }
//
//        } else {
//
//            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleContainerVisible = true;
//            }
//        }
//    }
}
