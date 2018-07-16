package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UoeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uoe_topics_list);

        RecyclerView uoeTopicsList = findViewById(R.id.uoe_topics_list);

        uoeTopicsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTopicsList.setLayoutManager(layoutManager);

        List<UoeTopic> uoeTopics = new ArrayList<>();

        uoeTopics.add(new UoeTopic(R.string.topic1));
        uoeTopics.add(new UoeTopic(R.string.topic2));
        uoeTopics.add(new UoeTopic(R.string.topic3));

        RVUoeAdapter adapter = new RVUoeAdapter(uoeTopics);
        uoeTopicsList.setAdapter(adapter);
    }
}
