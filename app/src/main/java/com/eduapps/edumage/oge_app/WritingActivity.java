package com.eduapps.edumage.oge_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private int rightAnswers;

    private SQLiteDatabase db;
    final String EXPERIENCE_WRITING_KEY = "ExperienceWriting";

    private View.OnTouchListener disableTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_task);

        db = new DbHelper(this).getReadableDatabase();

        addInstruction();

        /* упражнение на структуру письма */
        int id = 7;
        assignQuestion(id);
        Collections.shuffle(Arrays.asList(currentQuestion));
        final String[] currentStructureQuestion = currentQuestion.clone();
        final String[] currentStructureAnswer = currentAnswer.clone();

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
        lp.setMargins(0, 0, 0, 32);

        final String[] clichesAnswers = new String[6];
        final List<List<Spinner>> spinnersCluster = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            assignQuestion(ids.get(i));
            Collections.shuffle(Arrays.asList(currentQuestion));
            while (TextUtils.join(" ", currentQuestion).equals(TextUtils.join(" ", currentAnswer))) {
                Collections.shuffle(Arrays.asList(currentQuestion));
            }
            clichesAnswers[i] = TextUtils.join(" ", currentAnswer);

            LinearLayout question = new LinearLayout(this);
            question.setOrientation(LinearLayout.HORIZONTAL);

            TextView questionNumber = new TextView(this);
            questionNumber.setId((i + 1));   // id is needed to find it when checking and color it
            questionNumber.setText((i + 1) + ")");
            questionNumber.setTextSize(16);
            questionNumber.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            question.addView(questionNumber);

            LinearLayout totalRows = new LinearLayout(this);
            totalRows.setOrientation(LinearLayout.VERTICAL);
            int numberOfRows = (int) Math.ceil((double)currentQuestion.length / 3);
            List<Spinner> rowSpinners = new ArrayList<>();
            int itemsLeft = currentQuestion.length;

            for (int r = 0; r < numberOfRows; r++) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                for (int f = 0; f < Math.min(3, itemsLeft); f++) {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    Spinner spinner = (Spinner) inflater.inflate(R.layout.writing_spinner, row, false);
                    adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, currentQuestion);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(f + 3*r);
                    row.addView(spinner);
                    rowSpinners.add(spinner);
                }

                itemsLeft -= 3;

                totalRows.addView(row);

            }

            spinnersCluster.add(rowSpinners);
            question.addView(totalRows);
            question.setLayoutParams(lp);

            phrases.addView(question);
        }

        /* упражнение на слова-связки (сопоставить перевод) */
        id = 8;
        assignQuestion(id);
        final String[] unshuffledQuestion = currentQuestion.clone();
        final String[] unshuffledAns = currentAnswer.clone();
        Collections.shuffle(Arrays.asList(currentQuestion));
        Collections.shuffle(Arrays.asList(currentAnswer));
        final String[] currentLinkersQuestion = currentQuestion.clone();
        final List<Spinner> rowSpinners = new ArrayList<>();

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
            eng_phrase.setText(currentQuestion[i] + " —");
            eng_phrase.setTextSize(16);
            eng_phrase.setTextColor(getResources().getColor(R.color.colorPrimaryText))  ;
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
            rowSpinners.add(spinner);

            linkers.addView(question);
        }

        /* слова-связки (дополнить предложения) */

        LinearLayout sentences = findViewById(R.id.sentences);
        final List<List<String>> sentencesAnswers = new ArrayList<>();
        final List<List<Spinner>> sentencesSpinnersCluster = new ArrayList<>();

        for (int i = 9; i < 11; i++) {
            assignQuestion(i);
            String[] unshuffledSentence = currentAnswer.clone();
            sentencesAnswers.add(Arrays.asList(unshuffledSentence));
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
            List<Spinner> sentenceRowSpinners = new ArrayList<>();

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
                sentenceRowSpinners.add(spinner);

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
            sentencesSpinnersCluster.add(sentenceRowSpinners);

            sentences.addView(sentence);
        }

        /* упражнение на полные ответы */

        ids.clear();
        for (int i = 11; i < 14; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);

        final int[] radioAnswers = new int[3];
        final String[] comments = new String[3];

        assignQuestion(ids.get(0));
        String heading1 = currentQuestion[0];
        TextView question1 = findViewById(R.id.full_answer_1);
        question1.setText(heading1);
        RadioButton option1_1 = findViewById(R.id.question1_option1);
        option1_1.setText(currentQuestion[1]);
        RadioButton option1_2 = findViewById(R.id.question1_option2);
        option1_2.setText(currentQuestion[2]);
        RadioButton option1_3 = findViewById(R.id.question1_option3);
        option1_3.setText(currentQuestion[3]);
        radioAnswers[0] = Integer.parseInt(currentAnswer[0]) - 1;
        comments[0] = currentQuestion[4] + "\n" + currentQuestion[5] + "\n" + currentQuestion[6];

        assignQuestion(ids.get(1));
        String heading2 = currentQuestion[0];
        TextView question2 = findViewById(R.id.full_answer_2);
        question2.setText(heading2);
        RadioButton option2_1 = findViewById(R.id.question2_option1);
        option2_1.setText(currentQuestion[1]);
        RadioButton option2_2 = findViewById(R.id.question2_option2);
        option2_2.setText(currentQuestion[2]);
        RadioButton option2_3 = findViewById(R.id.question2_option3);
        option2_3.setText(currentQuestion[3]);
        radioAnswers[1] = Integer.parseInt(currentAnswer[0]) - 1;
        comments[1] = currentQuestion[4] + "\n" + currentQuestion[5] + "\n" + currentQuestion[6];

        assignQuestion(ids.get(2));
        String heading3 = currentQuestion[0];
        TextView question3 = findViewById(R.id.full_answer_3);
        question3.setText(heading3);
        RadioButton option3_1 = findViewById(R.id.question3_option1);
        option3_1.setText(currentQuestion[1]);
        RadioButton option3_2 = findViewById(R.id.question3_option2);
        option3_2.setText(currentQuestion[2]);
        RadioButton option3_3 = findViewById(R.id.question3_option3);
        option3_3.setText(currentQuestion[3]);
        radioAnswers[2] = Integer.parseInt(currentAnswer[0]) - 1;
        comments[2] = currentQuestion[4] + "\n" + currentQuestion[5] + "\n" + currentQuestion[6];

        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritingActivity.this, TrainingsActivity.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* проверка упражнения на структуру */
                EditText answer1 = findViewById(R.id.writing_cell_1);
                EditText answer2 = findViewById(R.id.writing_cell_2);
                EditText answer3 = findViewById(R.id.writing_cell_3);
                EditText answer4 = findViewById(R.id.writing_cell_4);
                EditText answer5 = findViewById(R.id.writing_cell_5);
                EditText answer6 = findViewById(R.id.writing_cell_6);
                EditText answer7 = findViewById(R.id.writing_cell_7);
                EditText answer8 = findViewById(R.id.writing_cell_8);

                checkEditTextAnswer(answer1, 0, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer2, 1, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer3, 2, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer4, 3, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer5, 4, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer6, 5, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer7, 6, currentStructureQuestion, currentStructureAnswer);
                checkEditTextAnswer(answer8, 7, currentStructureQuestion, currentStructureAnswer);

                disableEditText(answer1);
                disableEditText(answer2);
                disableEditText(answer3);
                disableEditText(answer4);
                disableEditText(answer5);
                disableEditText(answer6);
                disableEditText(answer7);
                disableEditText(answer8);

                Spinner curSpinner;

                /* проверка упражнения на фразы-клише */
                for (int i = 0; i < 6; i++) {
                    List<Spinner> rowSpinners = spinnersCluster.get(i);
                    String[] curAns = clichesAnswers[i].split(" ");
                    String[] typedAnswer = new String[curAns.length];
                    for (int j = 0; j < curAns.length; j++) {
                        curSpinner = rowSpinners.get(j);
                        curSpinner.setOnTouchListener(disableTouch);
                        typedAnswer[j] = checkSpinnerSelection(curSpinner, curAns[j]);
                    }

                    if (TextUtils.join("\n", typedAnswer).equals(
                            TextUtils.join("\n", curAns))) {
                        rightAnswers++;
                    }
                }

                /* проверка упражнения на слова-связки (перевод) */
                for (int i = 0; i < rowSpinners.size(); i++) {
                    int indexOfTypedWord = Arrays.asList(unshuffledQuestion).indexOf(currentLinkersQuestion[i]);
                    String rightAns = unshuffledAns[indexOfTypedWord];
                    curSpinner = rowSpinners.get(i);
                    curSpinner.setOnTouchListener(disableTouch);
                    String typed = checkSpinnerSelection(curSpinner, rightAns);
                    if (typed.equals(rightAns)) {
                        rightAnswers++;
                    }
                }

                /* проверка упражнения на слова-связки (дополнить) */
                for (int i = 0; i < sentencesAnswers.size(); i++) {
                    List<Spinner> sentence = sentencesSpinnersCluster.get(i);
                    List<String> sentenceAns = sentencesAnswers.get(i);
                    for (int j = 0; j < sentence.size(); j++) {
                        String curAns = sentenceAns.get(j);
                        curSpinner = sentence.get(j);
                        curSpinner.setOnTouchListener(disableTouch);
                        String typed = checkSpinnerSelection(curSpinner, curAns);
                        if (typed.equals(curAns)) {
                            rightAnswers++;
                        }
                    }
                }

                /* проверка упражнения на полный ответ */
                final RadioGroup options1 = findViewById(R.id.options1);
                final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
                checkRadioButtonAnswer(options1, radioButton1, radioAnswers[0]);

                TextView explan1 = findViewById(R.id.explanation_1);
                if (radioButton1 != null) {
                    int typed = options1.indexOfChild(radioButton1);
                    explan1.setText(comments[0].split("\n")[typed]);
                }

                final RadioGroup options2 = findViewById(R.id.options2);
                final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
                checkRadioButtonAnswer(options2, radioButton2, radioAnswers[1]);

                TextView explan2 = findViewById(R.id.explanation_2);
                if (radioButton2 != null) {
                    int typed = options2.indexOfChild(radioButton2);
                    explan2.setText(comments[1].split("\n")[typed]);
                }

                final RadioGroup options3 = findViewById(R.id.options3);
                final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
                checkRadioButtonAnswer(options3, radioButton3, radioAnswers[2]);

                TextView explan3 = findViewById(R.id.explanation_3);
                if (radioButton3 != null) {
                    int typed = options3.indexOfChild(radioButton3);
                    explan3.setText(comments[2].split("\n")[typed]);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                builder.setTitle("Ваш результат:")
                        .setCancelable(false)
                        .setMessage("You have " + rightAnswers + "/43 right answers")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();

                recordRecentActivity();
            }
        });
    }

    private void checkEditTextAnswer(EditText answer, int position, String[] curQ, String[] curAns) {
        if (!answer.getText().toString().equals("")) {
            String typedAnswer = curQ[Integer.parseInt(answer.getText().toString()) - 1];
            String rightAnswer = curAns[position];
            if (typedAnswer.equals(rightAnswer)) {
                answer.setTextColor(getResources().getColor(R.color.right_answer));
                rightAnswers++;
            } else {
                answer.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
        }
    }

    private String checkSpinnerSelection(Spinner spinner, String ans) {
        String typed = spinner.getSelectedItem().toString();
        if (typed.equals(ans)) {
            TextView text = (TextView) spinner.getSelectedView();
            text.setTextColor(getResources().getColor(R.color.right_answer));
        } else {
            TextView text = (TextView) spinner.getSelectedView();
            text.setTextColor(getResources().getColor(R.color.wrong_answer));
        }
        return typed;
    }

    private void checkRadioButtonAnswer(RadioGroup options, RadioButton radioButton, int ans) {
        if (radioButton != null) {
            if (options.indexOfChild(radioButton) == ans) {
                rightAnswers++;
                radioButton.setTextColor(getResources().getColor(R.color.right_answer));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
        }
    }

    private void disableEditText(EditText editText) {
        editText.setHint("");
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void assignQuestion(int currentId) {
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
            } finally {
                cursor.close();
            }
        }
    }

    private void recordRecentActivity() {
        Cursor cursor;

        // forming the data to write
        int exp;
        int totalQuestions = 43;
        int dynamics = 0;
        String topicName = "Письмо";

        // if user does the task for the firs time, he gets more experience
        // TODO: return here later
//        if (currentCompletion == 50) {
//            exp /= 2;
//        } else if (currentCompletion == 100) {
//            exp = 0;
//        }
        exp = rightAnswers;

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

        // also, update completion if user did the task well
        // TODO: return here later [2]
//        if (rightAnswers / totalQuestions >= 0.6 && currentCompletion < 100) {
//            ContentValues v = new ContentValues();
//            v.put("completion", currentCompletion + 50);
//            String table;
//            switch (category) {
//                case 0:
//                    table = Tables.ReadingTask1.TABLE_NAME;
//                    break;
//                case 1:
//                    table = Tables.ReadingTask2.TABLE_NAME;
//                    break;
//                default:
//                    table = null;
//            }
//            db.update(table, v, "_id=" + currentID, null);
//        }

        // add collected experience to user's level
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(EXPERIENCE_WRITING_KEY)) {
            int collectedXP = preferences.getInt(EXPERIENCE_WRITING_KEY, 0);
            editor.putInt(EXPERIENCE_WRITING_KEY, collectedXP + exp);
        } else {
            editor.putInt(EXPERIENCE_WRITING_KEY, exp);
        }
        editor.apply();
    }

    private void addInstruction() {
        LinearLayout addInfo = new LinearLayout(this);
        addInfo.setOrientation(LinearLayout.VERTICAL);

        // "Don't show" checkbox goes with the instructions
        View view = getLayoutInflater().inflate(R.layout.dont_show_checkbox, addInfo, false);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    storeDialogStatus(true);
                } else {
                    storeDialogStatus(false);
                }
            }
        });

        TextView link = new TextView(this);
        SpannableString string = new SpannableString("Ссылка на теорию");
        string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        link.setPadding(64, 0, 0, 0);
        link.setText(string);
        link.setTextSize(16);
        link.setTextColor(Color.BLUE);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritingActivity.this, TheoryItemActivity.class);
                intent.putExtra("position", 0);
                intent.putExtra("category", 2);
                startActivity(intent);
            }
        });

        addInfo.addView(view);
        addInfo.addView(link);

        AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
        builder.setTitle("Инструкция")
                .setCancelable(false)
                .setMessage("Данная тренировка проверяет отдельные навыки, необходимые для " +
                        "написания письма:\n1. Знание структуры письма\n2. Использование фраз-клише\n" +
                        "3. Использование слов-связок.\n4. Умение дать полный ответ на вопрос.\n" +
                        "Для получения подробной информации обратитесь к соответствующим разделам Теории")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setView(addInfo);
        AlertDialog alert = builder.create();
        if (getDialogStatus()) {
            alert.hide();
        } else {
            alert.show();
        }
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Dont_show_writing", isChecked);
        editor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("Dont_show_writing", false);
    }
}
