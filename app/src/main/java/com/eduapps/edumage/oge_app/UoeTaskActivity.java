package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uoe_tasks_page);

        RecyclerView uoeTasksList = findViewById(R.id.uoe_tasks_list);

        uoeTasksList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTasksList.setLayoutManager(layoutManager);

        List<UoeTask> tasks = new ArrayList<>();

        // retrieving the tasks' category passed from adapter class
//        Bundle extras = getIntent().getExtras();
//        int category = 0;
//        if (extras != null) {
//            category = extras.getInt("category");
//        }
//
//        for (int i = 1; i <= 2; i++) {
//            tasks.add(generateRandomTask(category));
//        }

        tasks.add(new UoeTask(R.string.uoe_task1, R.string.uoe_origin1, R.string.uoe_answer1));
        tasks.add(new UoeTask(R.string.uoe_task2, R.string.uoe_origin2, R.string.uoe_answer2));

        RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks);
        uoeTasksList.setAdapter(adapter);
    }

    public UoeTask generateRandomTask(int i) {
        // TODO: get tasks from DB
        if (i == 1) {
            return new UoeTask(R.string.uoe_task1, R.string.uoe_origin1, R.string.uoe_answer1);
        } else if (i == 2) {
            return new UoeTask(R.string.uoe_task2, R.string.uoe_origin2, R.string.uoe_answer2);
        } else {
            return null;
        }
    }
}
