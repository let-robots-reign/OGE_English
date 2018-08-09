package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReadingTaskActivity extends AppCompatActivity {

    private List<String> rightAnswersList;
    private int category;
    private String heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        final int currentText = getRandomText(category);
        final int currentQuestion = getRandomQuestion(category);

        String[] question = new String[]{};
        // the layout depends on the type of the task
        if (category == 0) {
            setContentView(R.layout.reading_task_9);
            setTitle(R.string.reading_topic1);
            TextView headings = findViewById(R.id.headings_list);
            headings.setText(currentQuestion);

            question = getResources().getString(currentText).split("\n");

        } else if (category == 1) {
            setContentView(R.layout.reading_tasks_10_17);
            setTitle(R.string.reading_topic2);
            TextView headingView = findViewById(R.id.heading);
            headingView.setText(heading);
            TextView text = findViewById(R.id.reading_text);
            text.setText(currentText);

            question = getResources().getString(currentQuestion).split("\n");
        }

        TextView question1 = findViewById(R.id.question1);
        question1.setText(question[0]);

        TextView question2 = findViewById(R.id.question2);
        question2.setText(question[1].trim());

        TextView question3 = findViewById(R.id.question3);
        question3.setText(question[2].trim());

        TextView question4 = findViewById(R.id.question4);
        question4.setText(question[3].trim());

        TextView question5 = findViewById(R.id.question5);
        question5.setText(question[4].trim());

        TextView question6 = findViewById(R.id.question6);
        question6.setText(question[5].trim());

        TextView question7 = findViewById(R.id.question7);
        question7.setText(question[6].trim());

        if (category == 1) {
            TextView question8 = findViewById(R.id.question8);
            question8.setText(question[7].trim());
        }
    }

    private int getRandomText(int category) {
        switch (category) {
            case 0:
                return R.string.task9_task1_texts1;
            case 1:
                return R.string.reading_topic2_text1;
            default:
                return 0;
        }
    }

    private int getRandomQuestion(int category) {
        rightAnswersList = new ArrayList<>();
        // TODO: query question from DB
        switch (category) {
            case 0:
                rightAnswersList.add("1");
                rightAnswersList.add("3");
                rightAnswersList.add("6");
                rightAnswersList.add("5");
                rightAnswersList.add("8");
                rightAnswersList.add("2");
                rightAnswersList.add("4");
                return R.string.task9_task1_headings;
            case 1:
                rightAnswersList.add("False");
                rightAnswersList.add("True");
                rightAnswersList.add("False");
                rightAnswersList.add("Not stated");
                rightAnswersList.add("True");
                rightAnswersList.add("Not stated");
                rightAnswersList.add("False");
                rightAnswersList.add("False");
                heading = "Two sports brands";
                return R.string.reading_topic2_task1_real;
            default:
                return 0;
        }
    }
}
