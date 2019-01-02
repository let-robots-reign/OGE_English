package com.eduapps.edumage.oge_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WritingActivity extends AppCompatActivity {

    private String[] currentQuestion;
    private String[] currentAnswer;
    private List<String> rightAnswersList;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_task);

        db = new DbHelper(this).getReadableDatabase();

        /* упражнение на фразы-клише */
        List<Integer> ids = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);

        LinearLayout phrases = findViewById(R.id.phrases);
        ArrayAdapter<String> adapter;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 12);

        for (int i = 0; i < 6; i++) {

            assignQuestion(ids.get(i));
            Collections.shuffle(Arrays.asList(currentQuestion));
            while (TextUtils.join(" ", currentQuestion).equals(TextUtils.join(" ", currentAnswer))) {
                Collections.shuffle(Arrays.asList(currentQuestion));
            }

            LinearLayout question = new LinearLayout(this);
            question.setOrientation(LinearLayout.HORIZONTAL);

            TextView questionNumber = new TextView(this);
            questionNumber.setText((i + 1) + ")");
            questionNumber.setTextSize(16);
            questionNumber.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            question.addView(questionNumber);

            LinearLayout totalRows = new LinearLayout(this);
            totalRows.setOrientation(LinearLayout.VERTICAL);
            int numberOfRows = (int) Math.ceil(currentQuestion.length / 3);

            for (int r = 0; r < numberOfRows; r++) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                for (int f = 0; f < 3; f++) {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    Spinner spinner = (Spinner) inflater.inflate(R.layout.writing_spinner, row, false);
                    adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, currentQuestion);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(f + 3*r);
                    row.addView(spinner);
                }

                totalRows.addView(row);

            }

            question.addView(totalRows);
            question.setLayoutParams(lp);

            phrases.addView(question);
        }

        /* упражнение на структуру письма */
        int id = 7;
        assignQuestion(id);
        Collections.shuffle(Arrays.asList(currentQuestion));

        LinearLayout structure = findViewById(R.id.structure);

        for (int i = 0; i < 8; i++) {

            while (TextUtils.join("\n", currentQuestion).equals(TextUtils.join("\n", currentAnswer))) {
                Collections.shuffle(Arrays.asList(currentQuestion));
            }

            LinearLayout question = new LinearLayout(this);
            question.setOrientation(LinearLayout.HORIZONTAL);

            TextView questionNumber = new TextView(this);
            questionNumber.setText((i + 1) + ")");
            questionNumber.setTextSize(16);
            questionNumber.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            questionNumber.setPadding(0, 0, 8, 0);
            question.addView(questionNumber);

            TextView line = new TextView(this);
            line.setText(currentQuestion[i]);
            line.setTextSize(16);
            line.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            question.addView(line);

            structure.addView(question);
        }


        /* упражнение на слова-связки (сопоставить перевод) */
        id = 8;
        assignQuestion(id);
        Collections.shuffle(Arrays.asList(currentQuestion));
        Collections.shuffle(Arrays.asList(currentAnswer));

        LinearLayout linkers = findViewById(R.id.linkers);

        for (int i = 0; i < 24; i++) {

            LinearLayout question = new LinearLayout(this);
            question.setOrientation(LinearLayout.HORIZONTAL);

            TextView questionNumber = new TextView(this);
            questionNumber.setText((i + 1) + ")");
            questionNumber.setTextSize(16);
            questionNumber.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            questionNumber.setPadding(0, 0, 8, 0);
            question.addView(questionNumber);

            TextView eng_phrase = new TextView(this);
            eng_phrase.setText(currentQuestion[i]);
            eng_phrase.setTextSize(16);
            eng_phrase.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            question.addView(eng_phrase);

            LayoutInflater inflater = LayoutInflater.from(this);
            Spinner spinner = (Spinner) inflater.inflate(R.layout.writing_spinner,
                    question, false);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, currentAnswer);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(i);
            question.addView(spinner);

            linkers.addView(question);
        }

        /* слова-связки (дополнить предложения) */

        LinearLayout sentences = findViewById(R.id.sentences);

        for (int i = 9; i < 11; i++) {
            assignQuestion(i);
            Collections.shuffle(Arrays.asList(currentAnswer));

            LinearLayout sentence = new LinearLayout(this);
            sentence.setOrientation(LinearLayout.HORIZONTAL);

            TextView questionNumber = new TextView(this);
            questionNumber.setText((i - 8) + ")");
            questionNumber.setTextSize(16);
            questionNumber.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            questionNumber.setPadding(0, 0, 8, 0);
            sentence.addView(questionNumber);

            LinearLayout totalRows = new LinearLayout(this);
            totalRows.setOrientation(LinearLayout.VERTICAL);

            for (int j = 0; j < currentQuestion.length; j++) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                LayoutInflater inflater = LayoutInflater.from(this);
                Spinner spinner = (Spinner) inflater.inflate(R.layout.writing_spinner, row, false);
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, currentAnswer);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(j);
                row.addView(spinner);

                TextView extract = new TextView(this);
                extract.setText(currentQuestion[j]);
                extract.setTextSize(16);
                extract.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                FrameLayout.LayoutParams textLp = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                textLp.setMargins(0, 0, 0, 12);
                extract.setLayoutParams(textLp);
                row.addView(extract);

                totalRows.addView(row);

            }

            sentence.addView(totalRows);

            sentences.addView(sentence);
        }


    }

    private void assignQuestion(int currentId) {
        rightAnswersList = new ArrayList<>();
        Cursor cursor;

        String selection = Tables.WritingTask.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{currentId+""};

        cursor = db.query(Tables.WritingTask.TABLE_NAME, null, selection, selectionArgs,
                null ,null, null);

        if (cursor != null) {
            try {
                int taskColumnIndex = cursor.getColumnIndex(Tables.WritingTask.COLUMN_TASK);
                int answerColumnIndex = cursor.getColumnIndex(Tables.WritingTask.COLUMN_ANSWER);

                cursor.moveToFirst();
                String delimiter = (currentId < 7) ? " " : "\n";
                currentQuestion = cursor.getString(taskColumnIndex).split(delimiter);
                currentAnswer = cursor.getString(answerColumnIndex).split(delimiter);

                rightAnswersList.addAll(Arrays.asList(currentAnswer));
            } finally {
                cursor.close();
            }
        }
    }
}
