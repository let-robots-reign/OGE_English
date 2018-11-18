package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.eduapps.edumage.oge_app.VariantsTasks.AudioTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.ReadingTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.UoeTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.WritingTaskFragment;


public class CategoryInVariantsAdapter extends FragmentPagerAdapter {
    final int PAGES = 3;
    //private String tabTitles[] = new String[] {"1", "2", "3-8", "9", "10-17", "18-26", "27-32", "33"};
    private String tabTitles[] = new String[] {"1", "2", "3-8"};
    private static int number;

    public CategoryInVariantsAdapter(FragmentManager fm, int n) {
        super(fm);
        number = n;  // number of the variant (0 - 9)
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                return createAudioFragment(position);
//            case 3:
//            case 4:
//                return createReadingFragment(position);
//            case 5:
//            case 6:
//                return createUoeFragment(position);
//            case 7:
//                return new WritingTaskFragment();
            default:
                return null;
        }
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

    private static AudioTaskFragment createAudioFragment(int position) {
        AudioTaskFragment fragment = new AudioTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

//    private static ReadingTaskFragment createReadingFragment(int position) {
//        ReadingTaskFragment fragment = new ReadingTaskFragment();
//        Bundle bundle = new Bundle(2);
//        bundle.putInt("number", number);  // number of the variant
//        bundle.putInt("position", position);  // type of the task
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    private static UoeTaskFragment createUoeFragment(int position) {
//        UoeTaskFragment fragment = new UoeTaskFragment();
//        Bundle bundle = new Bundle(2);
//        bundle.putInt("number", number);  // number of the variant
//        bundle.putInt("position", position);  // type of the task
//        fragment.setArguments(bundle);
//        return fragment;
//    }
}
