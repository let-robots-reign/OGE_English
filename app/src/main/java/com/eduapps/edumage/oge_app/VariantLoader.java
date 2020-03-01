package com.eduapps.edumage.oge_app;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.eduapps.edumage.oge_app.VariantsTasks.AudioTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.ReadingTaskFragment;
import com.eduapps.edumage.oge_app.VariantsTasks.UoeTaskFragment;

import java.util.ArrayList;
import java.util.List;

public class VariantLoader extends AsyncTaskLoader<List<Fragment>> {

    private static int number;

    public VariantLoader(Context context, int n) {
        super(context);
        number = n;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Fragment> loadInBackground() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(createAudioFragment(0));
        fragments.add(createAudioFragment(1));
        fragments.add(createAudioFragment(2));
        fragments.add(createReadingFragment(0));
        fragments.add(createReadingFragment(1));
        fragments.add(createUoeFragment(0));
        fragments.add(createUoeFragment(1));
        return fragments;
    }

    private static AudioTaskFragment createAudioFragment(int position) {
        AudioTaskFragment fragment = new AudioTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

    private static ReadingTaskFragment createReadingFragment(int position) {
        ReadingTaskFragment fragment = new ReadingTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }

    private static UoeTaskFragment createUoeFragment(int position) {
        UoeTaskFragment fragment = new UoeTaskFragment();
        Bundle bundle = new Bundle(2);
        bundle.putInt("number", number);  // number of the variant
        bundle.putInt("position", position);  // type of the task
        fragment.setArguments(bundle);
        return fragment;
    }
}
