//package com.zajel.zajelandroid.Home;
//
//
//import android.animation.ObjectAnimator;
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearSmoothScroller;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class NewSearchFragment extends Fragment implements
//        , View.OnClickListener, SearchQueryResponse, ProgressResponse
//        , SwipeRefreshLayout.OnRefreshListener {
//
//    public static int search_counter;
//    final int spanCount = 3;
//    public String nextPageURL;
//    @BindView(R.id.browse_members)
//    LinearLayout linLayout;
//    @BindView(R.id.btn_browse_members)
//    Button btn_browse_members;
//    @BindView(R.id.rv_SecTwo_ProfilesGrid)
//    CustomRecyclerView rv_NewProfilesGrid;
//    @BindView(R.id.swipe_container)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.message_button)
//    ImageButton messageButton;
//    @BindView(R.id.ivTabFilter)
//    ImageView filterCategory;
//    @BindView(R.id.app_logo_ImageView)
//    ImageView appLogoImageView;
//    @BindView(R.id.recently_active_TextView)
//    TextView recentlyActiveTextView;
//    @BindView(R.id.featured_TextView)
//    TextView featuredTextView;
//    @BindView(R.id.nearestTextView)
//    TextView nearestTExtView;
//    @BindView(R.id.recently_active_relativeLayout)
//    RelativeLayout recentlyActiveRelativeLayout;
//
////    public MainActivity activity;
//    @BindView(R.id.nearby_relativeLayout)
//    RelativeLayout nearbyRelativeLayout;
//    @BindView(R.id.featured_relativeLayout)
//    RelativeLayout featuredRelativeLayout;
//    @BindView(R.id.filters_relativeLayout)
//    RelativeLayout filtersRelativeLayout;
//    String token;
//    boolean test;
//    int previousLastItemOnList;
//    //pagination and the banner stuff
//    boolean loadOneTime = false;
//    int currentTab = 1;
//    StaggeredGridLayoutManager gridLayoutManager;
//    int spanCounter = 0;
//    ArrayList<Integer> tempSpanArray;
//    View view;
//    Context context;
//    private APIManager apiManager;
//    private StorageManager storageManager;
//    private ListSearch newListSearchTwo;
//    private int progressPercentage = 10;
//    // new search tab stuff
//    private List<Object> gridData;
//    private NewSearchAdapter newSearchAdapter;
//    //dialog for link ticket
//    private AlertDialog alertDialogAndroid;
//
//    public NewSearchFragment() {
//        // Required empty public constructor
//    }
//
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_new_search, container, false);
//        ButterKnife.bind(this, view);
//        context = view.getContext();
//        apiManager = APIManager.getInstance(App.getContext());
//        storageManager = StorageManager.getInstance(App.getContext());
//        token = storageManager.getLoginToken();
//        //update uid in analytics
//        Amplitude.getInstance().setUserId(storageManager.getId());
//        tempSpanArray = new ArrayList<>();
//        tempSpanArray.add(spanCounter);
//
//        // Log event to Mixpanel
//        MixpanelAPI mixpanel = App.getInstance().mixpanel;
//        mixpanel.track("Search - Open");
//
//        newListSearchTwo = new ListSearch();
//
//
//        btn_browse_members.setOnClickListener(this);
//        linLayout.setVisibility(View.GONE);
//
//
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setRefreshing(false);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
//
//
//        sendProgressRequest();
//        //Analytics
//        Answers.getInstance().logContentView(new ContentViewEvent()
//                .putContentName("Search")
//                .putContentType("Screen")
//                .putContentId("search"));
//
//
//        rv_NewProfilesGrid.setHasFixedSize(true);
//        rv_NewProfilesGrid.setItemViewCacheSize(20);
//        rv_NewProfilesGrid.setDrawingCacheEnabled(true);
//        rv_NewProfilesGrid.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//
//
//        // Set recently active as visible first tab
//
//        messageButton.setOnClickListener(this);
//        filterCategory.setOnClickListener(this);
//        recentlyActiveTextView.setOnClickListener(this);
//        nearestTExtView.setOnClickListener(this);
//        featuredTextView.setOnClickListener(this);
//        recentlyActiveRelativeLayout.setOnClickListener(this);
//        featuredRelativeLayout.setOnClickListener(this);
//        nearbyRelativeLayout.setOnClickListener(this);
//        filtersRelativeLayout.setOnClickListener(this);
////        appLogoImageView.setOnClickListener(this);
//
//
//        appLogoImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
////                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(appLogoImageView,
////                                "scaleX", 0.8f);
////                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(appLogoImageView,
////                                "scaleY", 0.8f);
////                        scaleDownX.setDuration(1000);
////                        scaleDownY.setDuration(1000);
////
////                        AnimatorSet scaleDown = new AnimatorSet();
////                        scaleDown.play(scaleDownX).with(scaleDownY);
////
//
//
//                        ObjectAnimator anim = ObjectAnimator.ofFloat(appLogoImageView, "alpha", 0.5f);
//                        anim.setDuration(50); // duration 3 seconds
//                        anim.start();
////                        spinslot();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        ObjectAnimator anim1 = ObjectAnimator.ofFloat(appLogoImageView, "alpha", 1f);
//                        anim1.setDuration(50); // duration 3 seconds
//                        anim1.start();
//
//                        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
//                            @Override
//                            protected int getVerticalSnapPreference() {
//                                return LinearSmoothScroller.SNAP_TO_START;
//                            }
//                        };
//                        smoothScroller.setTargetPosition(0);
//                        gridLayoutManager.startSmoothScroll(smoothScroller);
//
//
////                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
////                                appLogoImageView, "scaleX", 1f);
////                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
////                                appLogoImageView, "scaleY", 1f);
////                        scaleDownX2.setDuration(1000);
////                        scaleDownY2.setDuration(1000);
////
////                        AnimatorSet scaleDown2 = new AnimatorSet();
////                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
////
////                        scaleDown2.start();
//
//                        break;
//                }
//                return true;
//            }
//        });
//
////        appLogoImageView.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                if(event.getAction() == MotionEvent.ACTION_DOWN) {
////                    AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
////                    animation1.setDuration(1000);
////                    animation1.setStartOffset(5000);
////                    animation1.setFillAfter(false);
////                    v.startAnimation(animation1);
//////                    v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.image_alpha));
////                } else if (event.getAction() == MotionEvent.ACTION_UP) {
////                    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
////                        @Override protected int getVerticalSnapPreference() {
////                            return LinearSmoothScroller.SNAP_TO_START;
////                        }
////                    };
////                    smoothScroller.setTargetPosition(0);
////                    gridLayoutManager.startSmoothScroll(smoothScroller);
////                }
////
////                return true;
////            }
////        });
//
//        test = true;
//        CTKUtiles.hideKeyboard(getActivity());
//        loadTab(1);
//        return view;
//    }
//
//    public void loadSearchTab(JSONObject obj) {
//
//        try {
//            JSONObject response = obj.getJSONObject("response");
//            try {
//                nextPageURL = obj.getJSONObject(APIManager.DataField.RESPONSE.toString()).getString(APIManager.DataField.NEXTPAGEURL.toString());
//            } catch (Exception e) {
//
//            }
//            JSONArray sec2_data = response.getJSONArray("data");
//
//
//            ///////////////////////
//            // SECTION TWO
//            ///////////////////////
//
//            newListSearchTwo.clearList();
//            newListSearchTwo.fillSearchList(sec2_data);
//            ArrayList<JSONObject> listPartyListTwo = newListSearchTwo.getSearchList();
//
//            gridData = new ArrayList<>();
//            gridData.clear();
//            newSearchAdapter = new NewSearchAdapter(getActivity(), gridData);
//            for (int data1 = 0; data1 < listPartyListTwo.size(); data1++) {
//                ProfileGrid item = new ProfileGrid(listPartyListTwo.get(data1).getString("username")
//                        , "https://s3-ap-southeast-1.amazonaws.com/tsbmainbucket/images/public/" + listPartyListTwo.get(data1).getString("profilephoto")
//                        , listPartyListTwo.get(data1).getInt("age")
//                        , listPartyListTwo.get(data1).getString("id")
//                        , listPartyListTwo.get(data1).getString("online")
//                        , listPartyListTwo.get(data1).getJSONObject("location").getString("currentcity")
//                        , listPartyListTwo.get(data1).getJSONObject("location").getString("currentcountry"));
//                int width = 150;
////                if (data1==1){
////                    int height = 280;
////                    item.setWidth(width);
////                    item.setHeight(height);
////                }
////                else {
////                    int height = 230;
////                    item.setWidth(width);
////                    item.setHeight(height);
////                }
//
//                gridData.add(item);
////                newSearchAdapter.addDrawable(item);
//
//                loadOneTime = true;
//            }
//            int startPosition = newSearchAdapter.getItemCount();
//            newSearchAdapter.notifyItemRangeInserted(startPosition, gridData.size());
//            InitNewEndless();
//
////            rv_NewProfilesGrid.setLayoutManager(gridLayoutManager);
//
////            rv_NewProfilesGrid.setAdapter(newSearchAdapter);
////            initilizeTheGridView();
//            gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//            gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
////        newSearchAdapter = new NewSearchAdapter(getActivity(), gridData);
//            rv_NewProfilesGrid.setLayoutManager(gridLayoutManager);
//            rv_NewProfilesGrid.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
//                @Override
//                public void onLoadMore(int current_page) {
//                    apiManager.sendSearchRequest(APIManager.URL.SEARCH, nextPageURL, "", token, new SearchResponse() {
//                        @Override
//                        public void onSearchResponse(JSONObject response) {
//
//                            try {
//                                Log.d("Paginate", "resp data: " + response.toString());
//                                JSONObject res = response.getJSONObject("response");
//                                JSONArray tempdata = res.getJSONArray("data");
//                                try {
//                                    nextPageURL = res.getString("next_page_url");
//                                } catch (Exception e) {
//
//                                }
//
//                                ListSearch tempListSearch = new ListSearch();
//                                tempListSearch.fillSearchList(tempdata);
//                                ArrayList<JSONObject> tempList = tempListSearch.getSearchList();
//                                int startPosition = newSearchAdapter.getItemCount();
//
//                                for (int data1 = 0; data1 < tempList.size(); data1++) {
//                                    ProfileGrid item = new ProfileGrid(tempList.get(data1).getString("username")
//                                            , "https://s3-ap-southeast-1.amazonaws.com/tsbmainbucket/images/public/" + tempList.get(data1).getString("profilephoto")
//                                            , tempList.get(data1).getInt("age")
//                                            , tempList.get(data1).getString("id")
//                                            , tempList.get(data1).getString("online")
//                                            , tempList.get(data1).getJSONObject("location").getString("currentcity")
//                                            , tempList.get(data1).getJSONObject("location").getString("currentcountry"));
////                                    int width = 150;
//
////                                if (data1==0){
////                                    int height = 270;
////                                    item.setWidth(width);
////                                    item.setHeight(height);
////
////                                }
////                                else {
////                                    int height = 230;
////                                    item.setWidth(width);
////                                    item.setHeight(height);
////                                }
//
//                                    gridData.add(item);
////                                    newSearchAdapter.addDrawable(item);
//                                    loadOneTime = true;
//
//                                }
//
//
//                                newSearchAdapter.notifyItemRangeInserted(startPosition, gridData.size());
////                            newSearchAdapter.notifyDataSetChanged();
//                            } catch (JSONException e) {
//
//                                e.printStackTrace();
//                            } finally {
////                            kp.dismiss();
//                                mSwipeRefreshLayout.setRefreshing(false);
//
//                            }
//                        }
//
//                        @Override
//                        public void onSearchErrorResponse(VolleyError error) {
////                        kp.dismiss();
//                            mSwipeRefreshLayout.setRefreshing(false);
//                        }
//                    });
//
//
//                }
//            });
//
//            rv_NewProfilesGrid.setAdapter(newSearchAdapter);
//        } catch (JSONException e) {
//            Log.e("ex", e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("SearchingFrag", "onActivityResult: req code: " + requestCode);
//        super.onActivityResult(requestCode, resultCode, data);
//        //if want to go to messaging frag
//        if (requestCode == 1) {
//            if (resultCode == -1) {
//
//                String strUID = data.getStringExtra("userid");
//                String strUN = data.getStringExtra("username");
//                String strAL = data.getStringExtra("ageloc");
//                String strProfPic = data.getStringExtra("profilepic");
//
////                FragmentSwitcher.switchFragmentMaintain(getFragmentManager()
////                        , R.id.content_main, ConversationFrag.newInstance(strUID, "true", strUN, strAL, strProfPic), FragmentSwitcher.AnimationType.FADE);
//                Intent conversationIntent = new Intent(getActivity(), ConveersationActivity.class);
//                conversationIntent.putExtra("idOtherUser", strUID);
//                conversationIntent.putExtra("isUserProfile", "true");
//                conversationIntent.putExtra("username", strUN);
//                conversationIntent.putExtra("ageloc", strAL);
//                conversationIntent.putExtra("profilepic", strProfPic);
//                startActivity(conversationIntent);
//
//            } else if (resultCode == -2) {
//                //go to report user fragment
//                String strUID = data.getStringExtra("userid");
//
////                FragmentSwitcher.switchFragmentMaintain(getFragmentManager()
////                        , R.id.content_main, ReportUser.newInstance(strUID, token), FragmentSwitcher.AnimationType.FADE);
//                Intent reportUserIntent = new Intent(getContext(), ReportUserActivity.class);
//                reportUserIntent.putExtra("userId", strUID);
//                reportUserIntent.putExtra("token", token);
//                startActivity(reportUserIntent);
//            }
//        }
//    }
//
//    public void loadTab(int tabindex) {
//        if (tabindex == 1) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            startSearch();
//            currentTab = 1;
//        } else if (tabindex == 2) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            sendCategoriesSearchRequest(null, APIManager.URL.FEATURED);
//
//            currentTab = 2;
//        } else if (tabindex == 3) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            sendCategoriesSearchRequest(null, APIManager.URL.COLLEGE);
//
//            currentTab = 3;
//        } else if (tabindex == 4) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            currentTab = 4;
//            sendCategoriesSearchRequest(null, APIManager.URL.NEWEST);
//
//        } else if (tabindex == 5) {
//
//            // PARTY TAB
//            mSwipeRefreshLayout.setVisibility(View.GONE);
//            currentTab = 5;
//        } else if (tabindex == 6) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            sendCategoriesSearchRequest(null, APIManager.URL.PREMIUM);
//
//            currentTab = 6;
//        } else if (tabindex == 7) {
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            sendCategoriesSearchRequest(null, APIManager.URL.NEAREST);
//
//            currentTab = 7;
//        }
//    }
//
//    public void InitNewEndless() {
//        loadOneTime = false;
//        EndlessRecyclerViewScrollListener.current_page = 1;
//        EndlessRecyclerViewScrollListener.loading = true;
//        EndlessRecyclerViewScrollListener.previousTotal = 0;
//    }
//
//    public void startSearch() {
//
//        previousLastItemOnList = 0;
//
//        if (storageManager.getIsFilter() != 0) {
//            mSwipeRefreshLayout.setRefreshing(true);
//
//            String queryURL = storageManager.getUrlSettings().replaceAll("%5b", "[").replaceAll("%5d", "]").replaceAll("%20", "-");
//
//            if (!storageManager.getSearchKeyword().equals("")) {
//                queryURL += storageManager.getSearchKeyword();
//
//            }
//
//            System.out.println("search query URL: " + queryURL);
//            apiManager.sendSearchQueryRequest(queryURL, this, token);
//        } else {
//
//            sendSearchRequest(null);
//        }
//    }
//
//    private void sendSearchRequest(String nextPageURL) {
//        mSwipeRefreshLayout.setRefreshing(true);
//
//        String searchQuery = "";
//        Log.e("tes", storageManager.getUrlSettings());
//        //todo
//        if (!storageManager.getSearchKeyword().equals("")) {
//            searchQuery += storageManager.getSearchKeyword();
//            filterCategory.setBackground(getResources().getDrawable(R.drawable.filter_icon_active));
//        } else {
//            filterCategory.setBackground(getResources().getDrawable(R.drawable.filter_icon));
//        }
//
//        if (nextPageURL != null) {
//            System.out.println("nextpageurl: " + nextPageURL);
//        }
//
//        if (searchQuery.equals("")) {
//            if (nextPageURL == null) {
//                apiManager.sendSearchRequest(APIManager.URL.SEARCH, "", "", token, this);
//            } else {
//                apiManager.sendSearchRequest(APIManager.URL.SEARCH, nextPageURL, "", token, this);
//            }
//        } else {
//            if (nextPageURL == null) {
//                apiManager.sendSearchRequest(APIManager.URL.SEARCHFILTERS, "", searchQuery.replace("&", "?"), token, this);
//            } else {
//                apiManager.sendSearchRequest(APIManager.URL.SEARCHFILTERS, nextPageURL, "", token, this);
//            }
//        }
//    }
//
//
//    private void sendCategoriesSearchRequest(String nextPageURL, APIManager.URL url) {
//        mSwipeRefreshLayout.setRefreshing(true);
//        if (nextPageURL != null) {
//            System.out.println("nextpageurl: " + nextPageURL);
//        }
//
//        if (nextPageURL == null) {
//            apiManager.sendSearchRequest(url, "", "", token, this);
//        } else {
//            apiManager.sendSearchRequest(url, nextPageURL, "", token, this);
//        }
//
//    }
//
//
//    private void sendProgressRequest() {
//
//        apiManager.sendProfileProgressRequest(APIManager.URL.PROFILEPROGRESS, "", token, this);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.message_button:
//                Intent i = new Intent(getContext(), MessageListActivity.class);
//                startActivity(i);
//                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
////                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                break;
//            case R.id.btn_browse_members:
//                //todo
//                Intent iii = new Intent(getContext(), SearchFiltersActivity.class);
//                startActivity(iii);
//
////                FragmentSwitcher.switchFragment(getFragmentManager(), R.id.content_main, new SearchFilters(), FragmentSwitcher.AnimationType.PUSH);
//                break;
//            case R.id.nearestTextView:
//            case R.id.nearby_relativeLayout:
//                nearestTExtView.setBackground(getResources().getDrawable(R.drawable.bg_gradient_categories));
//                recentlyActiveTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
//                featuredTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
//
//                nearestTExtView.setTextColor(getResources().getColor(R.color.white));
//                recentlyActiveTextView.setTextColor(getResources().getColor(R.color.grey_categories));
//                featuredTextView.setTextColor(getResources().getColor(R.color.grey_categories));
//
//                loadTab(7);
//                break;
//
//
//            case R.id.recently_active_TextView:
//            case R.id.recently_active_relativeLayout:
//                recentlyActiveTextView.setBackground(getResources().getDrawable(R.drawable.bg_gradient_categories));
//                nearestTExtView.setBackgroundColor(getResources().getColor(R.color.transparent));
//                featuredTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
//
//                nearestTExtView.setTextColor(getResources().getColor(R.color.grey_categories));
//                recentlyActiveTextView.setTextColor(getResources().getColor(R.color.white));
//                featuredTextView.setTextColor(getResources().getColor(R.color.grey_categories));
//
//                loadTab(1);
//                break;
//
//
//            case R.id.featured_TextView:
//            case R.id.featured_relativeLayout:
//                featuredTextView.setBackground(getResources().getDrawable(R.drawable.bg_gradient_categories));
//                nearestTExtView.setBackgroundColor(getResources().getColor(R.color.transparent));
//                recentlyActiveTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
//
//                nearestTExtView.setTextColor(getResources().getColor(R.color.grey_categories));
//                recentlyActiveTextView.setTextColor(getResources().getColor(R.color.grey_categories));
//                featuredTextView.setTextColor(getResources().getColor(R.color.white));
//                loadTab(2);
//                break;
//            case R.id.ivTabFilter:
//            case R.id.filters_relativeLayout:
//                Intent ii = new Intent(getContext(), SearchFiltersActivity.class);
//                startActivity(ii);
//                getActivity().finish();
//                break;
//            case R.id.app_logo_ImageView:
////                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.image_alpha));
////
////                RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
////                    @Override protected int getVerticalSnapPreference() {
////                        return LinearSmoothScroller.SNAP_TO_START;
////                    }
////                };
////                smoothScroller.setTargetPosition(0);
////                gridLayoutManager.startSmoothScroll(smoothScroller);
//                break;
//        }
//    }
//
//
//}
