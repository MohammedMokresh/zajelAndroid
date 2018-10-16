package com.zajel.zajelandroid;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.BookList.BooksFragment;
import com.zajel.zajelandroid.Profile.ProfileFragment;
import com.zajel.zajelandroid.Requests.RequestsFragment;
import com.zajel.zajelandroid.Wishlist.WishlistFragment;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zajel.zajelandroid.Utils.ZajelUtils.replaceFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static Activity fa;
    @BindView(R.id.main_content_frameLayout)
    FrameLayout mainframeLayout;
    @BindView(R.id.home_imageButton)
    ImageButton homeImageButton;
    @BindView(R.id.request_imageButton)
    ImageButton requestImageButton;
    @BindView(R.id.wishlist_imageButton)
    ImageButton wishlistImageButton;
    @BindView(R.id.profile_imageButton)
    ImageButton profileImageButton;

    @BindView(R.id.home_relativeLayout)
    RelativeLayout homeRelativeLayout;
    @BindView(R.id.request_RelativeLayout)
    RelativeLayout requestsRelativeLayout;
    @BindView(R.id.wishlist_relativeLayout)
    RelativeLayout wishlistRelativeLayout;
    @BindView(R.id.profile_relativeLayout)
    RelativeLayout profileRelativeLayout;
    @BindView(R.id.nav_bottom_linearLayout)
    LinearLayout navBottomLinearLayout;
    @BindView(R.id.grey_seperator)
    View greySeperator;

    private APIManager apiManager;
    private PreferenceManager preferenceManager;

    //    public void checkForUpdate() {
//        AppUpdater appUpdater = new AppUpdater(this)
//                .setCancelable(false)
//                .setTitleOnUpdateAvailable("Update available")
//                .setContentOnUpdateAvailable("A new and improved version of TheSugarBook is available.\n\nPlease update now.")
//                .setButtonDismiss("Not now")
//                .setButtonDoNotShowAgain("");
//        appUpdater.start();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fa = this;
        ButterKnife.bind(this);


        preferenceManager = PreferenceManager.getInstance();
        apiManager = new APIManager(getApplicationContext());


        homeImageButton.setOnClickListener(this);
        requestImageButton.setOnClickListener(this);
        profileImageButton.setOnClickListener(this);
        wishlistImageButton.setOnClickListener(this);
        homeRelativeLayout.setOnClickListener(this);
        requestsRelativeLayout.setOnClickListener(this);
        profileRelativeLayout.setOnClickListener(this);
        wishlistRelativeLayout.setOnClickListener(this);


        replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, BooksFragment.newInstance());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.home_relativeLayout:
            case R.id.home_imageButton:
                navBottomLinearLayout.setBackground(getResources().getDrawable(R.drawable.bg_nav_bottm));
                homeImageButton.setImageResource(R.drawable.home_red);
                requestImageButton.setImageResource(R.drawable.requests);
                wishlistImageButton.setImageResource(R.drawable.wishlist);
                profileImageButton.setImageResource(R.drawable.profile);
                greySeperator.setVisibility(View.GONE);

                //remover the  Margin from the fragment
                RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                params3.setMargins(0, 0, 0, 0);
                mainframeLayout.setLayoutParams(params3);
                replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, BooksFragment.newInstance());
//                FragmentSwitcher.replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, new NewSearchFragment());

                break;
            case R.id.request_RelativeLayout:
            case R.id.request_imageButton:
                navBottomLinearLayout.setBackground(getResources().getDrawable(R.drawable.bg_nav_bottom_white));
                homeImageButton.setImageResource(R.drawable.home_gray);
                requestImageButton.setImageResource(R.drawable.requests_red);
                wishlistImageButton.setImageResource(R.drawable.wishlist_gray);
                profileImageButton.setImageResource(R.drawable.profile_gray);
                greySeperator.setVisibility(View.VISIBLE);
                //make Margin to the fragment
                Resources r = getApplicationContext().getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        60,
                        r.getDisplayMetrics()
                );

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(0, 0, 0, px);
                mainframeLayout.setLayoutParams(params);
                replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, RequestsFragment.newInstance());
//                FragmentSwitcher.replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, new ViewedMeFrag());


                break;
            case R.id.wishlist_relativeLayout:
            case R.id.wishlist_imageButton:
                navBottomLinearLayout.setBackground(getResources().getDrawable(R.drawable.bg_nav_bottom_white));
                homeImageButton.setImageResource(R.drawable.home_gray);
                requestImageButton.setImageResource(R.drawable.requests_gray);
                wishlistImageButton.setImageResource(R.drawable.wishlist_red);
                profileImageButton.setImageResource(R.drawable.profile_gray);
                greySeperator.setVisibility(View.VISIBLE);

                //make Margin to the fragment
                Resources r2 = getApplicationContext().getResources();
                int px2 = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        60,
                        r2.getDisplayMetrics()
                );

                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                params2.setMargins(0, 0, 0, px2);
                mainframeLayout.setLayoutParams(params2);
                replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, WishlistFragment.newInstance());

//                FragmentSwitcher.replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, new FavouriteFragment());
                break;
            case R.id.profile_relativeLayout:
            case R.id.profile_imageButton:
                navBottomLinearLayout.setBackground(getResources().getDrawable(R.drawable.bg_nav_bottom_white));
                homeImageButton.setImageResource(R.drawable.home_gray);
                requestImageButton.setImageResource(R.drawable.requests_gray);
                wishlistImageButton.setImageResource(R.drawable.wishlist_gray);
                profileImageButton.setImageResource(R.drawable.profile_red);
                greySeperator.setVisibility(View.VISIBLE);


                //make Margin to the fragment
                Resources rProfile = getApplicationContext().getResources();
                int pxProfile = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        60,
                        rProfile.getDisplayMetrics()
                );

                RelativeLayout.LayoutParams paramsProfile = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                paramsProfile.setMargins(0, 0, 0, pxProfile);

                mainframeLayout.setLayoutParams(paramsProfile);
                replaceFragment(getSupportFragmentManager(), R.id.main_content_frameLayout, ProfileFragment.newInstance());

                break;

        }
    }
}
