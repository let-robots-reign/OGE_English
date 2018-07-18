package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskActivity extends AppCompatActivity {

    String[] answersTyped = new String[10];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uoe_tasks_page);


        for (int i = 0; i < 10; i++) {
            answersTyped[i] = "";
        }

        RecyclerView uoeTasksList = findViewById(R.id.uoe_tasks_list);

        uoeTasksList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTasksList.setLayoutManager(layoutManager);

        List<UoeTask> tasks = new ArrayList<>();

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        if (category == 0) {
            tasks.add(new UoeTask(R.string.uoe_topic1_task1, R.string.uoe_topic1_origin1, R.string.uoe_topic1_answer1));
            tasks.add(new UoeTask(R.string.uoe_topic1_task2, R.string.uoe_topic1_origin2, R.string.uoe_topic1_answer2));
            tasks.add(new UoeTask(R.string.uoe_topic1_task3, R.string.uoe_topic1_origin3, R.string.uoe_topic1_answer3));
            tasks.add(new UoeTask(R.string.uoe_topic1_task4, R.string.uoe_topic1_origin4, R.string.uoe_topic1_answer4));
            tasks.add(new UoeTask(R.string.uoe_topic1_task5, R.string.uoe_topic1_origin5, R.string.uoe_topic1_answer5));
            tasks.add(new UoeTask(R.string.uoe_topic1_task6, R.string.uoe_topic1_origin6, R.string.uoe_topic1_answer6));
            tasks.add(new UoeTask(R.string.uoe_topic1_task7, R.string.uoe_topic1_origin7, R.string.uoe_topic1_answer7));
            tasks.add(new UoeTask(R.string.uoe_topic1_task8, R.string.uoe_topic1_origin8, R.string.uoe_topic1_answer8));
            tasks.add(new UoeTask(R.string.uoe_topic1_task9, R.string.uoe_topic1_origin9, R.string.uoe_topic1_answer9));
            tasks.add(new UoeTask(R.string.uoe_topic1_task10, R.string.uoe_topic1_origin10, R.string.uoe_topic1_answer10));
        } else if (category == 1) {
            tasks.add(new UoeTask(R.string.uoe_topic1_task1, R.string.uoe_topic1_origin1, R.string.uoe_topic1_answer1));
            tasks.add(new UoeTask(R.string.uoe_topic1_task2, R.string.uoe_topic1_origin2, R.string.uoe_topic1_answer2));
        }

//        for (int i = 1; i <= 2; i++) {
//            tasks.add(generateRandomTask(category));
//        }

        RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped);
        uoeTasksList.setAdapter(adapter);
    }

    public void generateRandomTask(int category) {
        // TODO: get tasks from DB
    }
}
