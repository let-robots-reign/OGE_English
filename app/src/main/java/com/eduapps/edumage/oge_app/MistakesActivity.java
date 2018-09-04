package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MistakesActivity extends AppCompatActivity {

    private String category;
    private String[] typedAnswers;
    private String[] rightAnswers;
    private boolean[] answersColors; // how to color user's answers
    private int taskID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mistakes_list);

        Bundle extras = getIntent().getExtras();
        category = null;
        if (extras != null) {
            category = extras.getString("task_category");

            // TODO: transfer ID of the task to pull explanations for it!!

            String[] annotations = null;
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
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(rightAnswers[i]);
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
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(rightAnswers[i]);
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
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = Integer.parseInt(typedAnswers[i]) == Integer.parseInt(rightAnswers[i]) - 1;
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
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = typedAnswers[i].equals(headings[Integer.parseInt(rightAnswers[i])]);
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
                        for (int i = 0; i < typedAnswers.length; i++) {
                            answersColors[i] = Integer.parseInt(typedAnswers[i]) == Integer.parseInt(rightAnswers[i]) - 1;
                        }
                        break;

                    default:
                        break;
                }
            }

            String[] explanations = getExplanations(taskID).split("\n");

            // коллекция для групп
            ArrayList<Map<String, String>> groupData;

            // коллекция для элементов одной группы
            ArrayList<Map<String, String>> childDataItem;

            // общая коллекция для коллекций элементов
            ArrayList<ArrayList<Map<String, String>>> childData;
            // в итоге получится childData = ArrayList<childDataItem>

            // список атрибутов группы или элемента
            Map<String, String> m;

            ExpandableListView mistakes;

            // заполняем коллекцию групп из массива с названиями групп
            groupData = new ArrayList<Map<String, String>>();
            for (String info : annotations) {
                // заполняем список атрибутов для каждой группы
                m = new HashMap<String, String>();
                m.put("user_answer", info); // ответ пользователя
                groupData.add(m);
            }

            // список атрибутов групп для чтения
            String groupFrom[] = new String[]{"user_answer"};
            // список ID view-элементов, в которые будет помещены атрибуты групп
            int groupTo[] = new int[]{android.R.id.text1};

            // создаем коллекцию для коллекций элементов
            childData = new ArrayList<ArrayList<Map<String, String>>>();

            for (String explanation : explanations) {
                // создаем коллекцию элементов для каждой группы
                childDataItem = new ArrayList<Map<String, String>>();
                // заполняем список атрибутов для каждого элемента
                m = new HashMap<String, String>();
                m.put("explanation", explanation); // пояснение к ответу
                childDataItem.add(m);
                // добавляем в коллекцию коллекций
                childData.add(childDataItem);
            }

            // список атрибутов элементов для чтения
            String childFrom[] = new String[]{"explanation"};
            // список ID view-элементов, в которые будет помещены атрибуты элементов
            int childTo[] = new int[]{android.R.id.text1};

            SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                    this,
                    groupData,
                    android.R.layout.simple_expandable_list_item_1,
                    groupFrom,
                    groupTo,
                    childData,
                    android.R.layout.simple_list_item_1,
                    childFrom,
                    childTo);

            mistakes = (ExpandableListView) findViewById(R.id.mistakes_list);
            mistakes.setAdapter(adapter);
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

