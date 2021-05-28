package com.truedigital.vhealth.ui.home.listnews;


import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ApiNewsObject;
import com.truedigital.vhealth.ui.adapter.SearchListDoctorAdapter;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;

import java.util.ArrayList;


public class NewsFragment extends BaseMvpFragment<NewsFragmentInterface.Presenter>
        implements NewsFragmentInterface.View{

    private RecyclerView recyclerView;
    private SearchListDoctorAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<ApiNewsObject.Lists> mListNews;

    public NewsFragment() {
        super();
    }

    public static NewsFragment newInstance(int position) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NewsFragmentInterface.Presenter createPresenter(){
        return NewsFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.patient_home_news;
    }

    @Override
    public void bindView( View view ){
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    @Override
    public void setupInstance(){
        setRetainInstance(true);
        setupViewPager(viewPager);
    }

    @Override
    public void setupView(){
        getPresenter().loadNews();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void restoreView( Bundle savedInstanceState ){

    }

    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }

    @Override
    public void updateNews(ArrayList<ApiNewsObject.Lists> listNews) {
        //adapter.setListData(listNews);
        mListNews = listNews;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.newInstance(0),"");
        adapter.addFragment(NewsListFragment.newInstance(1),"");
        adapter.addFragment(NewsListFragment.newInstance(2),"");
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //page = position;
                //updateIndicators(page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //doSlide();
    }
}

