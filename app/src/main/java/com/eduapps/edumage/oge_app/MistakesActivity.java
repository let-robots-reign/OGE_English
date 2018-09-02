package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MistakesActivity extends AppCompatActivity {

    private String category;
    private String[] typedAnswers;
    private String[] rightAnswers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        category = null;
        if (extras != null) {
            category = extras.getString("task_category");

            if (category != null) {
                switch (category) {
                    case "task_9":
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");
                        // list of headings
                        String[] headings = getResources().getString(extras.getInt("question")).split("\n");

                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals(headings[Integer.parseInt(rightAnswers[i])])) {
                                Log.v("MistakesActivity", "Верно!");
                            } else {
                                Log.v("MistakesActivity", "Неверно!");
                            }
                        }
                        break;

                    case "task_10":
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");

                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (Integer.parseInt(typedAnswers[i]) == Integer.parseInt(rightAnswers[i]) - 1) {
                                Log.v("MistakesActivity", "Верно!");
                            } else {
                                Log.v("MistakesActivity", "Неверно!");
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
        }
    }
}
