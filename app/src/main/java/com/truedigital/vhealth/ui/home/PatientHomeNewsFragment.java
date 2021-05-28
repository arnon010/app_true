package com.truedigital.vhealth.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;

public class PatientHomeNewsFragment extends Fragment {

    private ViewPager viewPager;
    private boolean isStopSlide;
    private int page;

    public PatientHomeNewsFragment() {
        super();
    }

    public static PatientHomeNewsFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("ARGS_POSITION",position);
        PatientHomeNewsFragment fragment = new PatientHomeNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_home_news, container, false);
        bindView(rootView);
        initInstances(rootView);
        return rootView;
    }

    private void bindView(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
    }

    private void initInstances(View rootView) {
        setRetainInstance(true);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(PatientHomeNewsListFragment.newInstance(0),"");
        adapter.addFragment(PatientHomeNewsListFragment.newInstance(1),"");
        adapter.addFragment(PatientHomeNewsListFragment.newInstance(2),"");
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                //updateIndicators(page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        doSlide();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        isStopSlide = true;
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void doSlide() {

        new Thread() {

            public void run() {

                while (!isStopSlide) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Log.d("Page : ", ""+page);
                                viewPager.setCurrentItem(page);
                                if (page++ == 3) page = 0;
                            }
                        });
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
