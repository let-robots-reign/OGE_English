package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        AdView adView = findViewById(R.id.adView_topics);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        RecyclerView readingTopicsList = findViewById(R.id.topics_list);

        readingTopicsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        readingTopicsList.setLayoutManager(layoutManager);

        List<Category> categories = new ArrayList<>();

        String[] topicsArray = getResources().getStringArray(R.array.reading_topics);
        for (String topic: topicsArray) {
            categories.add(new Category(topic));
        }

        RVCategoryAdapter adapter = new RVCategoryAdapter(categories, 1);
        readingTopicsList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReadingActivity.this, TrainingsActivity.class);
        startActivity(intent);
    }
}
