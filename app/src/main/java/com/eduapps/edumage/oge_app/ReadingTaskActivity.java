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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadingTaskActivity extends AppCompatActivity {

    private List<String> rightAnswersList;
    private List<String> typedAnswers;
    private int category;
    private int rightAnswers;
    private String heading;
    private boolean canRetry;

    private SQLiteDatabase db;
    final String EXPERIENCE_KEY = "Experience";

    private int currentID;
    private String currentText;
    private String currentQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DbHelper(this).getWritableDatabase();

        canRetry = true;
        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        assignRandomQuestion();

        String[] question = new String[]{};
        ArrayAdapter<String> adapter; // adapter for spinners
        // the layout depends on the type of the task
        if (category == 0) {
            setContentView(R.layout.reading_task_9);
            setTitle(R.string.reading_topic1);
            TextView headings = findViewById(R.id.headings_list);
            headings.setText(currentQuestion.split("Выберите заголовок\n")[1]);

            // spinner options are the list of headings
            String[] spinnerOptions = currentQuestion.split("\n");
            // Spinners for task9
            Spinner spinner1 = findViewById(R.id.spinner1);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);

            Spinner spinner2 = findViewById(R.id.spinner2);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);

            Spinner spinner3 = findViewById(R.id.spinner3);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter);

            Spinner spinner4 = findViewById(R.id.spinner4);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner4.setAdapter(adapter);

            Spinner spinner5 = findViewById(R.id.spinner5);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner5.setAdapter(adapter);

            Spinner spinner6 = findViewById(R.id.spinner6);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapter);

            Spinner spinner7 = findViewById(R.id.spinner7);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner7.setAdapter(adapter);

            question = currentText.split("\n");

        } else if (category == 1) {
            setContentView(R.layout.reading_tasks_10_17);
            setTitle(R.string.reading_topic2);
            TextView headingView = findViewById(R.id.heading);
            headingView.setText(heading);
            TextView text = findViewById(R.id.reading_text);
            text.setText(currentText);

            question = currentQuestion.split("\n");
        }

        // first category has 7 questions, 2nd - 8
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

        // defining buttons' behavior
        Button exitButton = findViewById(R.id.exit_button);
        Button submitButton = findViewById(R.id.submit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadingTaskActivity.this, ReadingActivity.class);
                startActivity(intent);
            }
        });

        typedAnswers = new ArrayList<>();
        if (category == 0) {
            // the task with EditTexts and Spinners
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightAnswers = 0;

                    Spinner spinner1 = findViewById(R.id.spinner1);
                    checkSpinnerSelection(spinner1, 0);

                    Spinner spinner2 = findViewById(R.id.spinner2);
                    checkSpinnerSelection(spinner2, 1);

                    Spinner spinner3 = findViewById(R.id.spinner3);
                    checkSpinnerSelection(spinner3, 2);

                    Spinner spinner4 = findViewById(R.id.spinner4);
                    checkSpinnerSelection(spinner4, 3);

                    Spinner spinner5 = findViewById(R.id.spinner5);
                    checkSpinnerSelection(spinner5, 4);

                    Spinner spinner6 = findViewById(R.id.spinner6);
                    checkSpinnerSelection(spinner6, 5);

                    Spinner spinner7 = findViewById(R.id.spinner7);
                    checkSpinnerSelection(spinner7, 6);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReadingTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/7 right answers")
                            .setCancelable(false)
                            .setPositiveButton("Смотреть ошибки",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ReadingTaskActivity.this, MistakesActivity.class);
                                            // put category to know the key-value pairs
                                            intent.putExtra("task_category", "task_9");
                                            // answers user typed (transforming to String[] array)
                                            String[] answersArray = typedAnswers.toArray(new String[typedAnswers.size()]);
                                            intent.putExtra("typed_answers", answersArray);
                                            // right answers indices (transforming to String[] array)
                                            String[] rightAnswersArray = rightAnswersList.toArray(new String[rightAnswersList.size()]);
                                            intent.putExtra("right_answers", rightAnswersArray);
                                            // headings (to get right answers by indices)
                                            intent.putExtra("question", currentQuestion);
                                            // id of the task
                                            intent.putExtra("id", currentID);

                                            // the result should appear in 'recent activities'
                                            recordRecentActivity(7);

                                            startActivity(intent);
                                        }
                                    });

                    if (canRetry) {
                        builder.setNegativeButton("Попробовать снова",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    typedAnswers.clear();
                                }
                            });
                        canRetry = false;
                    }

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        } else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightAnswers = 0;

                    final RadioGroup options1 = findViewById(R.id.options1);
                    final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options1, radioButton1, 0);

                    final RadioGroup options2 = findViewById(R.id.options2);
                    final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options2, radioButton2, 1);

                    final RadioGroup options3 = findViewById(R.id.options3);
                    final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options3, radioButton3, 2);

                    final RadioGroup options4 = findViewById(R.id.options4);
                    final RadioButton radioButton4 = options4.findViewById(options4.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options4, radioButton4, 3);

                    final RadioGroup options5 = findViewById(R.id.options5);
                    final RadioButton radioButton5 = options5.findViewById(options5.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options5, radioButton5, 4);

                    final RadioGroup options6 = findViewById(R.id.options6);
                    final RadioButton radioButton6 = options6.findViewById(options6.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options6, radioButton6, 5);

                    final RadioGroup options7 = findViewById(R.id.options7);
                    final RadioButton radioButton7 = options7.findViewById(options7.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options7, radioButton7, 6);

                    final RadioGroup options8 = findViewById(R.id.options8);
                    final RadioButton radioButton8 = options8.findViewById(options8.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options8, radioButton8, 7);

                    applyRadioListener(options1, radioButton1);
                    applyRadioListener(options2, radioButton2);
                    applyRadioListener(options3, radioButton3);
                    applyRadioListener(options4, radioButton4);
                    applyRadioListener(options5, radioButton5);
                    applyRadioListener(options6, radioButton6);
                    applyRadioListener(options7, radioButton7);
                    applyRadioListener(options8, radioButton8);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReadingTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/8 right answers")
                            .setCancelable(false)
                            .setPositiveButton("Смотреть ошибки",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ReadingTaskActivity.this, MistakesActivity.class);
                                            // put category to know the key-value pairs
                                            intent.putExtra("task_category", "task_10_17");
                                            // answers user typed (transforming to String[] array)
                                            String[] answersArray = typedAnswers.toArray(new String[typedAnswers.size()]);
                                            intent.putExtra("typed_answers", answersArray);
                                            // right answers indices (transforming to String[] array)
                                            String[] rightAnswersArray = rightAnswersList.toArray(new String[rightAnswersList.size()]);
                                            intent.putExtra("right_answers", rightAnswersArray);
                                            // id of the task
                                            intent.putExtra("id", currentID);

                                            // the result should appear in 'recent activities'
                                            recordRecentActivity(8);

                                            startActivity(intent);
                                        }
                                    });

                    if (canRetry) {
                        builder.setNegativeButton("Попробовать снова",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        typedAnswers.clear();
                                    }
                                });
                        canRetry = false;
                    }

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        // when a user enters, he should see an instruction to the task
        AlertDialog.Builder builder = new AlertDialog.Builder(ReadingTaskActivity.this);
        builder.setTitle("Инструкция")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        switch(category) {
            case 0:
                builder.setMessage("Прочитайте тексты и установите соответствие между текстами и " +
                        "их заголовками: к каждому тексту, обозначенному буквами А–G, подберите " +
                        "соответствующий заголовок, обозначенный цифрами 1–8. Используйте каждую " +
                        "цифру только один раз. В задании есть один лишний заголовок.");
                break;
            case 1:
                builder.setMessage("Прочитайте текст. Определите, какие из приведённых утверждений " +
                        "10–17 соответствуют содержанию текста (1 – True), какие не соответствуют " +
                        "(2 – False) и о чём в тексте не сказано, то есть на основании текста нельзя" +
                        " дать ни положительного, ни отрицательного ответа (3 – Not stated).");
                break;
        }

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void checkSpinnerSelection(Spinner spinner, int position) {
        if (spinner.getSelectedItemPosition() ==
                Integer.parseInt(rightAnswersList.get(position))) {
            TextView text = (TextView) spinner.getSelectedView();
            text.setTextColor(getResources().getColor(R.color.right_answer));
            rightAnswers += 1;
        } else {
            TextView text = (TextView) spinner.getSelectedView();
            text.setTextColor(getResources().getColor(R.color.wrong_answer));
        }
        typedAnswers.add(spinner.getSelectedItem().toString());
    }

    private void checkRadioButtonAnswer(RadioGroup options, RadioButton radioButton, int position) {
        if (radioButton != null) {
            if (options.indexOfChild(radioButton) == Integer.parseInt(rightAnswersList.get(position)) - 1) {
                rightAnswers += 1;
                radioButton.setTextColor(getResources().getColor(R.color.right_answer));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
            typedAnswers.add("" + options.indexOfChild(radioButton));
        } else {
            typedAnswers.add("-1");
        }
    }

    private void applyRadioListener(RadioGroup options, final RadioButton radioButton) {
        if (radioButton != null) {
            options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioButton.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                }
            });
        }
    }

    private void assignRandomQuestion() {
        rightAnswersList = new ArrayList<>();
        Cursor cursor;
        switch (category) {
            case 0:
                cursor = db.query(Tables.ReadingTask1.TABLE_NAME, null, null,
                        null, null, null, "RANDOM()", "1");
                break;
            case 1:
                cursor = db.query(Tables.ReadingTask2.TABLE_NAME, null, null,
                        null, null, null, "RANDOM()", "1");
                int headingColumnIndex = cursor.getColumnIndex(Tables.ReadingTask2.COLUMN_HEADING);
                cursor.moveToFirst();
                heading = cursor.getString(headingColumnIndex);
                break;
            default:
                cursor = null;
        }

        if (cursor != null) {
            try {
                int idColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_ID);
                int textColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_TEXT);
                int taskColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_TASK);
                int answerColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_ANSWER);

                cursor.moveToFirst();
                currentID = cursor.getInt(idColumnIndex);
                currentText = cursor.getString(textColumnIndex);
                currentQuestion = cursor.getString(taskColumnIndex);
                String currentAnswer = cursor.getString(answerColumnIndex);

                rightAnswersList.addAll(Arrays.asList(currentAnswer.split(" ")));
            } finally {
                cursor.close();
            }
        }
    }

    private void recordRecentActivity(int totalQuestions) {
        Cursor cursor;

        // forming the data to write
        int exp;
        int dynamics = 0;
        String topicName;
        if (category == 0) {
            topicName = "Задание 9";
            exp = rightAnswers * 2 * 10;
        } else {
            topicName = "Задания 10-17";
            exp = rightAnswers * 10;
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
        if (preferences.contains(EXPERIENCE_KEY)) {
            int collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);
            editor.putInt(EXPERIENCE_KEY, collectedXP + exp);
        } else {
            editor.putInt(EXPERIENCE_KEY, exp);
        }
        editor.apply();
    }
}
