package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        RecyclerView audioTopicsList = findViewById(R.id.topics_list);

        audioTopicsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        audioTopicsList.setLayoutManager(layoutManager);

        List<Category> categories = new ArrayList<>();

        categories.add(new Category(R.string.audio_topic1));
        categories.add(new Category(R.string.audio_topic2));
        categories.add(new Category(R.string.audio_topic3));

        RVCategoryAdapter adapter = new RVCategoryAdapter(categories, 0);
        audioTopicsList.setAdapter(adapter);
    }
}
