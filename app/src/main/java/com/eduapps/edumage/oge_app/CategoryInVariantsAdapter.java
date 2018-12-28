package com.eduapps.edumage.oge_app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class CategoryInVariantsAdapter extends FragmentPagerAdapter {
    final int PAGES = 7;
    private String tabTitles[] = new String[] {"1", "2", "3-8", "9", "10-17", "18-26", "27-32"};
    private List<Fragment> fragments;

    public CategoryInVariantsAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        fragments = frags;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGES;
    }
}
