package com.zajel.zajelandroid.Requests;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.zajel.zajelandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    @BindView(R.id.tabs_favourites)
    TabLayout tabLayoutRequests;
    @BindView(R.id.viewpager_favourites)
    ViewPager viewPagerRequests;
    public RequestsFragment() {
        // Required empty public constructor
    }

    public static RequestsFragment newInstance() {

        Bundle args = new Bundle();

        RequestsFragment fragment = new RequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this,v);
        setupViewPager(viewPagerRequests);
        tabLayoutRequests.setupWithViewPager(viewPagerRequests);


        tabLayoutRequests.getSelectedTabPosition();


        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(SentRequestsFragment.newInstance(), "Sent requests");
        adapter.addFragment(ReceiveRequestsFragment.newInstance(), "Receive requests ");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int mCurrentPosition = -1;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
