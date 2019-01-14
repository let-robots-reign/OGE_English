package com.eduapps.edumage.oge_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskActivity extends AppCompatActivity {

    String[] typedAnswers = new String[10];
    private List<UoeTask> tasks;
    private SQLiteDatabase db;
    final String EXPERIENCE_UOE_KEY = "ExperienceUoe";
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

        final Button exitButton = findViewById(R.id.exit_button);
        final Button submitButton = findViewById(R.id.submit_button);

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

                recordRecentActivity();

                AlertDialog.Builder builder = new AlertDialog.Builder(UoeTaskActivity.this);
                builder.setTitle("Ваш результат:")
                                .setMessage("You have " + rightAnswers + "/" + "10 right answers")
                                .setCancelable(false)
                                .setNegativeButton("Попробовать снова",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if (isOpen) {
                            exitButton.setVisibility(View.GONE);
                            submitButton.setVisibility(View.GONE);
                        } else {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    exitButton.setVisibility(View.VISIBLE);
                                    submitButton.setVisibility(View.VISIBLE);
                                }
                            }, 100);

                        }
                    }
                });
    }

    private void checkEditTextAnswer(EditText answer, int position) {
        if (answer.getText().toString().equals(tasks.get(position).getAnswer())) {
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

        String[] topicsArray = getResources().getStringArray(R.array.uoe_topics);
        if (category == 0) {
            // no filtration if "по всем темам"
            selection = null;
        } else {
            selectionArgs = new String[]{topicsArray[category]}; // filter by topic name
        }

        // select the tasks that were done less than twice
        String completionSelect = Tables.UseOfEnglishTask.COLUMN_COMPLETION + " < ?";
        String[] completionSelectArgs = new String[]{"100"};
        String totalSelection;
        String[] totalSelectionArgs;
        if (selection != null) {
            totalSelection = selection + " AND " + completionSelect;
            totalSelectionArgs = new String[]{selectionArgs[0], completionSelectArgs[0]};
        } else {
            totalSelection = completionSelect;
            totalSelectionArgs = completionSelectArgs;
        }

        cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, totalSelection,
                totalSelectionArgs,null, null, "RANDOM()", "1");
        if (cursor == null) {
            cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                    selectionArgs, null,null, "RANDOM()", "1");
        }

        if (cursor != null) {
            try {
                int idColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ID);
                int taskColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_TASK);
                int originColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ORIGIN);
                int answerColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ANSWER);
                int completionColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_COMPLETION);

                List<String> tasksList = new ArrayList<>();
                List<String> originsList = new ArrayList<>();
                cursor.moveToFirst();
                for (int i = 0; i < 10; i++) {
                    int id = cursor.getInt(idColumnIndex);
                    String task = cursor.getString(taskColumnIndex);
                    String origin = cursor.getString(originColumnIndex);
                    String answer = cursor.getString(answerColumnIndex);
                    int completion = cursor.getInt(completionColumnIndex);
                    UoeTask elem = new UoeTask(id, task, origin, answer, completion);
                    while (tasksList.contains(task) || (originsList.contains(origin) && category != 4
                            && category != 6 && category != 8 && category != 10 && category != 12) ||
                            (tasksList.size() > 0 && tasksList.get(i - 1).equals(task))) {
                        cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                                selectionArgs, null, null, "RANDOM()", "1");
                        cursor.moveToFirst();
                        id = cursor.getInt(idColumnIndex);
                        task = cursor.getString(taskColumnIndex);
                        origin = cursor.getString(originColumnIndex);
                        answer = cursor.getString(answerColumnIndex);
                        completion = cursor.getInt(completionColumnIndex);
                        elem = new UoeTask(id, task, origin, answer, completion);
                    }
                    tasks.add(elem);
                    tasksList.add(task);
                    originsList.add(origin);
                    cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, totalSelection,
                            totalSelectionArgs,null, null, "RANDOM()", "1");
                    if (cursor == null) {
                        cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                                selectionArgs, null,null, "RANDOM()", "1");
                    }
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
        int dynamics = 0;
        int totalQuestions = 10;  // in UoE there are always 10 tasks
        String topicName = getResources().getStringArray(R.array.uoe_topics)[category];

        // building exp points. If user does the task correctly for the first time, they get 2exp
        // if they did it correctly once, they get 1xp
        // if they did it correctly twice or more, no exp
        int exp = 0;
        for (int i = 0; i < typedAnswers.length; i++) {
            UoeTask elem = tasks.get(i);
            if (typedAnswers[i].equals(elem.getAnswer())) {
                // user answered question #i correctly
                int comp = elem.getCompletion();
                if (comp == 0) {
                    exp += 2;
                } else if (comp == 50) {
                    exp += 1;
                }
                // also, update completion if user answered the question correctly (and completion < 100)
                if (comp < 100) {
                    Log.v("UoeTaskActivity", comp+"");
                    ContentValues values = new ContentValues();
                    values.put("completion", comp + 50);
                    db.update(Tables.UseOfEnglishTask.TABLE_NAME, values,
                            "_id=" + elem.getId(), null);
                }
            }
        }

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
        if (preferences.contains(EXPERIENCE_UOE_KEY)) {
            int collectedXP = preferences.getInt(EXPERIENCE_UOE_KEY, 0);
            editor.putInt(EXPERIENCE_UOE_KEY, collectedXP + exp);
        } else {
            editor.putInt(EXPERIENCE_UOE_KEY, exp);
        }
        editor.apply();
    }
}
