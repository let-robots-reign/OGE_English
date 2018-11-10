package com.eduapps.edumage.oge_app.VariantsTasks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduapps.edumage.oge_app.R;

public class AudioTaskFragment extends Fragment {
    private int position;
    private int number;

    public AudioTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        number = getArguments().getInt("number"); // variant number;
        position = getArguments().getInt("position"); // type of the audio task
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.audio_tasks_1_2, container, false);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
