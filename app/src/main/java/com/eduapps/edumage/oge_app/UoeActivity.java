package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class UoeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        RecyclerView uoeTopicsList = findViewById(R.id.topics_list);

        uoeTopicsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTopicsList.setLayoutManager(layoutManager);

        List<Category> categories = new ArrayList<>();

        String[] topicsArray = getResources().getStringArray(R.array.uoe_topics);
        for (String topic: topicsArray) {
            categories.add(new Category(topic));
        }

        RVCategoryAdapter adapter = new RVCategoryAdapter(categories, 2);
        uoeTopicsList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UoeActivity.this, TrainingsActivity.class);
        startActivity(intent);
    }
}
