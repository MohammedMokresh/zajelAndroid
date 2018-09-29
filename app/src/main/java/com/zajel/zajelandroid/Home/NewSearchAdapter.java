//package com.zajel.zajelandroid.Home;
//
//import android.content.Context;
//import android.graphics.drawable.GradientDrawable;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.zajel.zajelandroid.R;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//
//public class NewSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    // The Native Express ad view type.
//    final static ArrayList<Integer> tempSpanArray = new ArrayList<>();
//    final static ArrayList<Integer> tempSpanArrayTest = new ArrayList<>();
//    // A menu item view type.
//    private static final int MENU_ITEM_VIEW_TYPE = 0;
//    // The banner ad view type.
//    private static final int BANNER_AD_VIEW_TYPE = 1;
//    private static final int BANNER_AD_VIEW_TYPE_TEST = 2;
//    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
//    private final Random mRandom;
//    Context context;
//    GradientDrawable gradientDrawable;
//    private List<Object> gridData;
//    private int spanCounter;
//    private int spanCounterTest;
//    private int x = 0;
//
//    public NewSearchAdapter(Context context, List<Object> gridData) {
//        spanCounter = 18;
//        spanCounterTest = 9;
//        tempSpanArray.add(spanCounter);
//        tempSpanArrayTest.add(spanCounterTest);
//        this.gridData = gridData;
//        this.context = context;
//        this.mRandom = new Random();
//
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
////        if (viewType == MENU_ITEM_VIEW_TYPE) {
//        View itemView1 = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.book_item, parent, false);
//        View v = null;
//        return new MyViewHolder1(itemView1);
////        }
////        else if (viewType == BANNER_AD_VIEW_TYPE) {
////            View itemView = LayoutInflater.from(parent.getContext())
////                    .inflate(R.layout.banner, parent, false);
////            View v = null;
////            return new MyViewHolder(itemView);
////        } else {
////            View itemView = LayoutInflater.from(parent.getContext())
////                    .inflate(R.layout.test_banner, parent, false);
////            View v = null;
////            return new MyViewHolderTest(itemView);
////        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//        int viewType = getItemViewType(position);
//
//        switch (viewType) {
//            case MENU_ITEM_VIEW_TYPE:
//
//
////                final MyViewHolder1 menuItemHolder = (MyViewHolder1) holder;
////
////                final ProfileGrid item = (ProfileGrid) gridData.get(position);
////                menuItemHolder.nameTextView.setText(item.getName());
////                menuItemHolder.ageTextView.setText(", " + String.valueOf(item.getAge()));
//////                Log.e("test", String.valueOf(item.getAge()));
////                menuItemHolder.locationTextView.setText(item.getCity() + ", " + item.getCountry());
////
////                ViewGroup.LayoutParams rlp = menuItemHolder.searchItemCardView.getLayoutParams();
//////                ViewGroup.LayoutParams rlp1 =  menuItemHolder.dynamicHeightImageView.getLayoutParams();
//////                float ratio = item.getHeight() / item.getWidth();
//////                rlp.height = (int) (rlp.width * ratio);
//////                menuItemHolder.searchItemCardView.setLayoutParams(rlp);
////                rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//////                rlp1.width= ViewGroup.LayoutParams.MATCH_PARENT;
////                if (position == 1) {
////                    holder.setIsRecyclable(false);
////                    rlp.height = 1000;
//////                    rlp1.height=1000;
////                } else {
////                    holder.setIsRecyclable(true);
////                    rlp.height = 800;
//////                    rlp1.height=800;
////                }
////
////                menuItemHolder.searchItemCardView.setLayoutParams(rlp);
//////                menuItemHolder.dynamicHeightImageView.setLayoutParams(rlp);
//////                menuItemHolder.dynamicHeightImageView.setRatio(item.getRatio());
////
//////                try {
//////                    URL url = new URL(item.getImgURL());
////                Picasso.with(context)
////                        .load(item.getImgURL())
////                        .fit()
////                        .centerCrop()
////                        .into(menuItemHolder.dynamicHeightImageView);
////
//////                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//////                    Palette palette = Palette.from(image).generate();
//////                    Palette.Swatch swatch = CTKUtiles.getMostPopulousSwatch(palette);
//////                    gradientDrawable = new GradientDrawable();
//////                    gradientDrawable.setShape(GradientDrawable.RECTANGLE);
//////                    gradientDrawable.setColor(swatch.getRgb());
////
//////                } catch(Exception e) {
//////                    System.out.println(e);
//////                }
//////                Picasso
//////                        .with(context)
//////                        .load(item.getImgURL())
//////                        .fit() // use fit()fit and centerInside() for making //it memory efficient.
//////                        .transform(new FaceCenterCrop(100, 100)) //in pixels. You can also use FaceCenterCrop1(width, height, unit) to provide width, height in DP.
//////                        .into(menuItemHolder.dynamicHeightImageView);
////
////
////                if (item.getOnline().equals("online")) {
////                    menuItemHolder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
////                } else {
////                    menuItemHolder.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
////
////                }
////                menuItemHolder.dynamicHeightImageView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////
//////                        Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
////
////
//////                        YoYo.with(Techniques.RubberBand).duration(700).playOn(view);
////                        Intent i = new Intent(context, ProfileActivity.class);
//////                        context.startActivity(i);
////
////                        i.putExtra("profileid", item.getId());
////
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                            ((Activity) context).startActivityForResult(i, 1, ActivityOptions.makeScaleUpAnimation(menuItemHolder.dynamicHeightImageView
////                                    , 0, 0
////                                    , menuItemHolder.dynamicHeightImageView.getWidth(),
////                                    menuItemHolder.dynamicHeightImageView.getHeight()).toBundle());
////
////                        } else {
////                            ((Activity) context).startActivityForResult(i, 1);
////
////                        }
////
////                    }
////                });
//
//                break;
//
//
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        try {
//            return gridData.size();
//        } catch (Exception e) {
//            if (x == 0) {
//                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
//            }
//            x = 1;
//            return 0;
//        }
//
//    }
//
//    public class MyViewHolder1 extends RecyclerView.ViewHolder {
//        TextView nameTextView;
//        TextView ageTextView;
//        ImageView ivOnline;
//        CardView searchItemCardView;
//        ImageView dynamicHeightImageView;
//        TextView locationTextView;
//
//        public MyViewHolder1(View view) {
//            super(view);
//            nameTextView = view.findViewById(R.id.name_TextView);
//            searchItemCardView = view.findViewById(R.id.search_item_card);
//            dynamicHeightImageView = view.findViewById(R.id.dynamic_height_image_view);
//            locationTextView = view.findViewById(R.id.location_textView);
//            ageTextView = view.findViewById(R.id.age_TextView);
//        }
//    }
//
//}
//
