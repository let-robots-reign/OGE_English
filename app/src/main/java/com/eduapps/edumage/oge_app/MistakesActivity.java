package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MistakesActivity extends AppCompatActivity {

    private String category;
    private String[] typedAnswers;
    private String[] rightAnswers;
    private boolean[] answersColors; // how to color user's answers
    private int taskID;
    private String[] annotations;
    private String[] rightAnswersFull;

    private SQLiteDatabase db;

    // for expandable list item
    ExpandableListAdapter mistakesAdapter;
    ExpandableListView mistakesList;
    List<String> userAnswers;
    HashMap<String, List<String>> explanations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mistakes_list);

        db = new DbHelper(this).getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("task_category");

            annotations = null;
            String[] question;

            taskID = extras.getInt("id");
            typedAnswers = extras.getStringArray("typed_answers");
            // indices of right answers
            rightAnswers = extras.getStringArray("right_answers");

            if (category != null) {
                switch (category) {
                    case "task_1":
                        // list of headings
                        question = extras.getString("question").split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                try {
                                    annotations[i] = (i + 1) + ") " + question[Integer.parseInt(typedAnswers[i]) - 1];
                                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
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
                        // list of headings
                        question = extras.getString("question").split("\n");

                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("")) {
                                annotations[i] = (i + 1) + ") Нет ответа";
                            } else {
                                try {
                                    annotations[i] = (i + 1) + ") " + question[Integer.parseInt(typedAnswers[i]) - 1];
                                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
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
                        // list of headings
                        question = extras.getString("question").split("\n");

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
                        // list of headings
                        String[] headings = extras.getString("question").split("\n");

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

                    case "task_10_17":
                        question = extras.getStringArray("question");
                        assert question != null;
                        annotations = new String[typedAnswers.length];
                        for (int i = 0; i < typedAnswers.length; i++) {
                            if (typedAnswers[i].equals("-1")) {
                                annotations[i] = (i + 1) + ") " + question[i] + "\n" + "Нет ответа";
                            } else {
                                switch (typedAnswers[i]) {
                                    case "0":
                                        annotations[i] = (i + 1) + ") " + question[i] + "\n" + "True";
                                        break;
                                    case "1":
                                        annotations[i] = (i + 1) + ") " + question[i] + "\n" + "False";
                                        break;
                                    case "2":
                                        annotations[i] = (i + 1) + ") " + question[i] + "\n" + "Not stated";
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
        userAnswers = new ArrayList<>();
        String explanationFull = getExplanations(taskID);
        explanations = new HashMap<>();

        // filling userAnswers
        userAnswers.addAll(Arrays.asList(annotations));
        // filling explanations
        for (int i = 0; i < userAnswers.size(); i++) {
            List<String> explanation = new ArrayList<>();
            if (!answersColors[i]) {
                explanation.add("Правильный ответ: " + rightAnswersFull[i]);
            }
            if (explanationFull.equals("Отсутствует")) {
                explanation.add("Пояснение:\n" + explanationFull);
            } else {
                explanation.add("Пояснение:\n" + explanationFull.split("---")[i].trim());
            }
            explanations.put(userAnswers.get(i), explanation);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MistakesActivity.this, TrainingsActivity.class);
        startActivity(intent);
    }

    private String getExplanations(int id) {
        // WHERE clause
        String[] columns;
        String selection;
        String[] selectionArgs;
        Cursor cursor;
        int idExplanationIndex;
        switch (category) {
            case "task_1":
                columns = new String[]{Tables.AudioTask1.COLUMN_EXPLANATION};
                selection = Tables.AudioTask1.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id + ""};
                cursor = db.query(Tables.AudioTask1.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
                idExplanationIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_EXPLANATION);
                break;
            case "task_2":
                columns = new String[]{Tables.AudioTask2.COLUMN_EXPLANATION};
                selection = Tables.AudioTask2.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id + ""};
                cursor = db.query(Tables.AudioTask2.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
                idExplanationIndex = cursor.getColumnIndex(Tables.AudioTask2.COLUMN_EXPLANATION);
                break;
            case "task_3_8":
                columns = new String[]{Tables.AudioTask3.COLUMN_EXPLANATION};
                selection = Tables.AudioTask3.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id + ""};
                cursor = db.query(Tables.AudioTask3.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
                idExplanationIndex = cursor.getColumnIndex(Tables.AudioTask3.COLUMN_EXPLANATION);
                break;
            case "task_9":
                columns = new String[]{Tables.ReadingTask1.COLUMN_EXPLANATION};
                selection = Tables.ReadingTask1.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id + ""};
                cursor = db.query(Tables.ReadingTask1.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
                idExplanationIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_EXPLANATION);
                break;
            case "task_10_17":
                columns = new String[]{Tables.ReadingTask2.COLUMN_EXPLANATION};
                selection = Tables.ReadingTask2.COLUMN_ID + " = ?";
                selectionArgs = new String[]{id + ""};
                cursor = db.query(Tables.ReadingTask2.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
                idExplanationIndex = cursor.getColumnIndex(Tables.ReadingTask2.COLUMN_EXPLANATION);
                break;
            default:
                cursor = null;
                idExplanationIndex = 0;
        }

        if (cursor != null) {
            try {
                cursor.moveToFirst();
                return cursor.getString(idExplanationIndex);
            } finally {
                cursor.close();
            }
        } else {
            return null;
        }
    }
}
