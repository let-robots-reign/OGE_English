package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        final RecyclerView uoeTasksList = findViewById(R.id.uoe_tasks_list);

        uoeTasksList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTasksList.setLayoutManager(layoutManager);

        final List<UoeTask> tasks = new ArrayList<>();

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        setTitle(getResources().getStringArray(R.array.uoe_topics)[category]);

        switch (category) {
            case 0:
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
                break;
            case 1:
                tasks.add(new UoeTask(R.string.uoe_topic1_task1, R.string.uoe_topic1_origin1, R.string.uoe_topic1_answer1));
                tasks.add(new UoeTask(R.string.uoe_topic1_task2, R.string.uoe_topic1_origin2, R.string.uoe_topic1_answer2));
                break;
        }

//        for (int i = 1; i <= 2; i++) {
//            tasks.add(generateRandomTask(category));
//        }

        RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped, false);
        uoeTasksList.setAdapter(adapter);

        Button exitButton = findViewById(R.id.exit_button);
        Button submitButton = findViewById(R.id.submit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UoeTaskActivity.this, UoeActivity.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped, true);
                uoeTasksList.setAdapter(adapter);

                String[] rightAnswersList = getResources().getStringArray(R.array.uoe_answers);

                int rightAnswers = 0;
                for (int i = 0; i < 10; i++) {
                    if (answersTyped[i].equals(rightAnswersList[i])) {
                        rightAnswers += 1;
                    }
                }

                Toast.makeText(UoeTaskActivity.this, "You have " + rightAnswers + "/"
                        + "10 right answers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void generateRandomTask(int category) {
        // TODO: get tasks from DB
    }
}
