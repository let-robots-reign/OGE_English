package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MistakesActivity extends AppCompatActivity {

    private String category;
    private String[] typedAnswers;
    private String[] rightAnswers;
    private boolean[] answersColors; // how to color user's answers
    private int taskID;
    private String[] annotations;
    private String[] rightAnswersFull;

    // for expandable list item
    ExpandableListAdapter mistakesAdapter;
    ExpandableListView mistakesList;
    List<String> userAnswers;
    HashMap<String, List<String>> explanations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mistakes_list);

        Bundle extras = getIntent().getExtras();
        category = null;
        if (extras != null) {
            category = extras.getString("task_category");

            // TODO: transfer ID of the task to pull explanations for it!!

            annotations = null;
            String[] question;

            if (category != null) {
                switch (category) {
                    case "task_1":
                        // assume taskID is 0
                        taskID = 0;
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");
                        // list of headings
                        question = getResources().getString(extras.getInt("question")).split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                try {
                                    annotations[i] = (i + 1) + ") " + question[Integer.parseInt(typedAnswers[i]) - 1];
                                } catch (NumberFormatException e) {
                                    annotations[i] = (i + 1) + ") " + typedAnswers[i];
                                }
                            }
                        }

                        answersColors = new boolean[typedAnswers.length];
                        rightAnswersFull = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(rightAnswers[i]);
                            rightAnswersFull[i] = question[Integer.parseInt(rightAnswers[i]) - 1];
                        }
                        break;

                    case "task_2":
                        // assume taskID is 1
                        taskID = 1;
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");
                        // list of headings
                        question = getResources().getString(extras.getInt("question")).split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                try {
                                    annotations[i] = (i + 1) + ") " + question[Integer.parseInt(typedAnswers[i]) - 1];
                                } catch (NumberFormatException e) {
                                    annotations[i] = (i + 1) + ") " + typedAnswers[i];
                                }
                            }
                        }

                        answersColors = new boolean[typedAnswers.length];
                        rightAnswersFull = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(rightAnswers[i]);
                            rightAnswersFull[i] = question[Integer.parseInt(rightAnswers[i]) - 1];
                        }
                        break;

                    case "task_3_8":
                        // assume taskID is 2
                        taskID = 2;
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");
                        // list of headings
                        question = getResources().getString(extras.getInt("question")).split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("-1")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                //annotations[i] = (i + 1) + ") " + question[Integer.parseInt(typedAnswers[i]) - 1];
                                annotations[i] = (i + 1) + ") " + question[i].split("/option/")[Integer.parseInt(typedAnswers[i]) + 1];
                            }
                        }

                        answersColors = new boolean[typedAnswers.length];
                        rightAnswersFull = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = Integer.parseInt(typedAnswers[i]) == Integer.parseInt(rightAnswers[i]) - 1;
                            rightAnswersFull[i] = question[i].split("/option/")[Integer.parseInt(rightAnswers[i])];
                        }
                        break;

                    case "task_9":
                        // assume taskID is 3
                        taskID = 3;
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");
                        // list of headings
                        String[] headings = getResources().getString(extras.getInt("question")).split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("Выберите заголовок")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                annotations[i] = (i + 1) + ") " + typedAnswers[i];
                            }
                        }

                        answersColors = new boolean[typedAnswers.length];
                        rightAnswersFull = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(headings[Integer.parseInt(rightAnswers[i])]);
                            rightAnswersFull[i] = headings[Integer.parseInt(rightAnswers[i])];
                        }
                        break;

                    case "task_10":
                        // assume taskID is 4
                        taskID = 4;
                        typedAnswers = extras.getStringArray("typed_answers");
                        // indices of right answers
                        rightAnswers = extras.getStringArray("right_answers");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("-1")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                switch (typedAnswers[i]) {
                                    case "0":
                                        annotations[i] = (i + 1) + ") True";
                                        break;
                                    case "1":
                                        annotations[i] = (i + 1) + ") False";
                                        break;
                                    case "2":
                                        annotations[i] = (i + 1) + ") Not stated";
                                        break;
                                }
                            }
                        }

                        answersColors = new boolean[typedAnswers.length];
                        rightAnswersFull = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = Integer.parseInt(typedAnswers[i]) == Integer.parseInt(rightAnswers[i]) - 1;
                            switch (rightAnswers[i]) {
                                case "1":
                                    rightAnswersFull[i] = "True";
                                    break;
                                case "2":
                                    rightAnswersFull[i] = "False";
                                    break;
                                case "3":
                                    rightAnswersFull[i] = "Not stated";
                                    break;
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

            mistakesList = findViewById(R.id.mistakes_list);
            prepareListData();
            mistakesAdapter = new MistakesAdapter(this, userAnswers, explanations, answersColors);
            mistakesList.setAdapter(mistakesAdapter);
        }
    }

    private void prepareListData() {
        userAnswers = new ArrayList<String>();
        explanations = new HashMap<String, List<String>>();

        // filling userAnswers
        userAnswers.addAll(Arrays.asList(annotations));
        // filling explanations
        for (int i = 0; i < userAnswers.size(); i++) {
            List<String> explanation = new ArrayList<String>();
            if (!answersColors[i]) {
                explanation.add("Правильный ответ: " + rightAnswersFull[i]);
            }
            explanation.add("Пояснение: " + getExplanations(taskID).split("\n")[i]);
            explanations.put(userAnswers.get(i), explanation);
        }
    }

    private String getExplanations(int id) {
        // TODO: pull the explanations from DB
        switch (taskID) {
            case 0:
                return "Отсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует";
            case 1:
                return "Отсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует";
            case 2:
                return "Отсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует";
            case 3:
                return "Отсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует";
            case 4:
                return "Отсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует\nОтсутсвует";
            default:
                return null;
        }
    }
}

