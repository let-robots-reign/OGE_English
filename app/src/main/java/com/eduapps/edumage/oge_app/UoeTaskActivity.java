package com.eduapps.edumage.oge_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskActivity extends AppCompatActivity {

    String[] typedAnswers = new String[10];
    private List<UoeTask> tasks;
    private List<String> rightAnswersList;
    private SQLiteDatabase db;
    final String EXPERIENCE_KEY = "Experience";
    private int rightAnswers;
    private int category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uoe_tasks_page);

        db = new DbHelper(this).getWritableDatabase();

        for (int i = 0; i < 10; i++) {
            typedAnswers[i] = "";
        }

        tasks = new ArrayList<>();
        rightAnswersList = new ArrayList<>();

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        setTitle(getResources().getStringArray(R.array.uoe_topics)[category]);

        generateRandomTasks(category);

        TextView question1 = findViewById(R.id.uoe_task1);
        final EditText origin1 = findViewById(R.id.uoe_answer1);
        TextView question2 = findViewById(R.id.uoe_task2);
        final EditText origin2 = findViewById(R.id.uoe_answer2);
        TextView question3 = findViewById(R.id.uoe_task3);
        final EditText origin3 = findViewById(R.id.uoe_answer3);
        TextView question4 = findViewById(R.id.uoe_task4);
        final EditText origin4 = findViewById(R.id.uoe_answer4);
        TextView question5 = findViewById(R.id.uoe_task5);
        final EditText origin5 = findViewById(R.id.uoe_answer5);
        TextView question6 = findViewById(R.id.uoe_task6);
        final EditText origin6 = findViewById(R.id.uoe_answer6);
        TextView question7 = findViewById(R.id.uoe_task7);
        final EditText origin7 = findViewById(R.id.uoe_answer7);
        TextView question8 = findViewById(R.id.uoe_task8);
        final EditText origin8 = findViewById(R.id.uoe_answer8);
        TextView question9 = findViewById(R.id.uoe_task9);
        final EditText origin9 = findViewById(R.id.uoe_answer9);
        TextView question10 = findViewById(R.id.uoe_task10);
        final EditText origin10 = findViewById(R.id.uoe_answer10);

        question1.setText(tasks.get(0).getQuestion());
        origin1.setHint(tasks.get(0).getOrigin());
        question2.setText(tasks.get(1).getQuestion());
        origin2.setHint(tasks.get(1).getOrigin());
        question3.setText(tasks.get(2).getQuestion());
        origin3.setHint(tasks.get(2).getOrigin());
        question4.setText(tasks.get(3).getQuestion());
        origin4.setHint(tasks.get(3).getOrigin());
        question5.setText(tasks.get(4).getQuestion());
        origin5.setHint(tasks.get(4).getOrigin());
        question6.setText(tasks.get(5).getQuestion());
        origin6.setHint(tasks.get(5).getOrigin());
        question7.setText(tasks.get(6).getQuestion());
        origin7.setHint(tasks.get(6).getOrigin());
        question8.setText(tasks.get(7).getQuestion());
        origin8.setHint(tasks.get(7).getOrigin());
        question9.setText(tasks.get(8).getQuestion());
        origin9.setHint(tasks.get(8).getOrigin());
        question10.setText(tasks.get(9).getQuestion());
        origin10.setHint(tasks.get(9).getOrigin());

        applyTextListener(origin1);
        applyTextListener(origin2);
        applyTextListener(origin3);
        applyTextListener(origin4);
        applyTextListener(origin5);
        applyTextListener(origin6);
        applyTextListener(origin7);
        applyTextListener(origin8);
        applyTextListener(origin9);
        applyTextListener(origin10);

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
                rightAnswers = 0;

                checkEditTextAnswer(origin1, 0);
                checkEditTextAnswer(origin2, 1);
                checkEditTextAnswer(origin3, 2);
                checkEditTextAnswer(origin4, 3);
                checkEditTextAnswer(origin5, 4);
                checkEditTextAnswer(origin6, 5);
                checkEditTextAnswer(origin7, 6);
                checkEditTextAnswer(origin8, 7);
                checkEditTextAnswer(origin9, 8);
                checkEditTextAnswer(origin10, 9);

                AlertDialog.Builder builder = new AlertDialog.Builder(UoeTaskActivity.this);
                builder.setTitle("Ваш результат:")
                                .setMessage("You have " + rightAnswers + "/" + "10 right answers")
                                .setCancelable(false)
                                .setNegativeButton("Попробовать снова",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                                recordRecentActivity();
                                            }
                                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void checkEditTextAnswer(EditText answer, int position) {
        if (answer.getText().toString().equals(rightAnswersList.get(position))) {
            answer.setTextColor(getResources().getColor(R.color.right_answer));
            rightAnswers += 1;
        } else {
            answer.setTextColor(getResources().getColor(R.color.wrong_answer));
        }
        typedAnswers[position] = answer.getText().toString();
    }

    private void applyTextListener(final EditText answer) {
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                answer.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void generateRandomTasks(int category) {
        Cursor cursor;
        String selection = Tables.UseOfEnglishTask.COLUMN_TOPIC + " = ?";
        String[] selectionArgs = null;
        switch (category) {
            case 0:
                selectionArgs = new String[]{"Множественное число существительных"};
                break;
            case 1:
                selectionArgs = new String[]{"Порядковые числительные"};
                break;
            case 2:
                selectionArgs = new String[]{"Объектные местоимения"};
                break;
        }
        cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                selectionArgs, null, null, "RANDOM()", "1");

        if (cursor != null) {
            try {
                int taskColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_TASK);
                int originColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ORIGIN);
                int answerColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ANSWER);

                List<String> wordsList = new ArrayList<>();
                cursor.moveToFirst();
                for (int i = 0; i < 10; i++) {
                    String task = cursor.getString(taskColumnIndex);
                    String origin = cursor.getString(originColumnIndex);
                    String answer = cursor.getString(answerColumnIndex);
                    UoeTask elem = new UoeTask(task, origin, answer);
                    while ((wordsList.contains(origin)&& category != 2) ||
                            (wordsList.size() > 0 && wordsList.get(i - 1).equals(origin))) {
                        cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                                selectionArgs, null, null, "RANDOM()", "1");
                        cursor.moveToFirst();
                        task = cursor.getString(taskColumnIndex);
                        origin = cursor.getString(originColumnIndex);
                        answer = cursor.getString(answerColumnIndex);
                        elem = new UoeTask(task, origin, answer);
                    }
                    tasks.add(elem);
                    wordsList.add(origin);
                    rightAnswersList.add(cursor.getString(answerColumnIndex));
                    cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                            selectionArgs, null, null, "RANDOM()", "1");
                    cursor.moveToFirst();
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void recordRecentActivity() {
        Cursor cursor;

        // forming the data to write
        int exp;
        int dynamics = 0;
        int totalQuestions = 10;  // in UoE there are always 10 tasks
        String topicName = getResources().getStringArray(R.array.uoe_topics)[category];
        exp = 10 * rightAnswers;

        // searching for records of the same topic to define dynamics
        String selection = Tables.RecentActivities.COLUMN_TOPIC + " = ?";
        String[] selectionArgs = new String[]{topicName};
        cursor = db.query(Tables.RecentActivities.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    int idRightAnswersColumn = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_RIGHT);
                    int lastResult = cursor.getInt(idRightAnswersColumn);
                    if (rightAnswers > lastResult) {
                        dynamics = 1;
                    } else if (rightAnswers < lastResult) {
                        dynamics = -1;
                    }
                }
            } finally {
                cursor.close();
            }
        }

        // putting all the data in the dbRecent
        ContentValues values = new ContentValues();
        values.put(Tables.RecentActivities.COLUMN_TOPIC, topicName);
        values.put(Tables.RecentActivities.COLUMN_RIGHT, rightAnswers);
        values.put(Tables.RecentActivities.COLUMN_TOTAL, totalQuestions);
        values.put(Tables.RecentActivities.COLUMN_EXP, exp);
        values.put(Tables.RecentActivities.COLUMN_DYNAMICS, dynamics);
        db.insert(Tables.RecentActivities.TABLE_NAME, null, values);

        // add collected experience to user's level
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(EXPERIENCE_KEY)) {
            int collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);
            editor.putInt(EXPERIENCE_KEY, collectedXP + exp);
        } else {
            editor.putInt(EXPERIENCE_KEY, exp);
        }
        editor.apply();
    }
}
