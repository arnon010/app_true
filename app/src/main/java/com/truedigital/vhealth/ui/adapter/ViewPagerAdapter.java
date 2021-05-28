package com.truedigital.vhealth.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songkrit on 1/26/2017 AD.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mListFragment = new ArrayList<>();
    private final List<String> mListFragmentTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mListFragment.add(fragment);
        mListFragmentTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragmentTitle.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }
}

