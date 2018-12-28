package com.eduapps.edumage.oge_app.VariantsTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.DbHelper;
import com.eduapps.edumage.oge_app.R;
import com.eduapps.edumage.oge_app.UoeTask;
import com.eduapps.edumage.oge_app.data.Tables;
import com.eduapps.edumage.oge_app.VariantTask;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskFragment extends TaskFragment {
    private int number;
    private int position;

    private int rightAnswers;
    private List<String> rightAnswersList;
    private String[] typedAnswers;
    private SQLiteDatabase db;
    private List<UoeTask> tasks;

    private View rootView;
    private int numberOfQuestions;

    public UoeTaskFragment() {
        // required empty
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        number = getArguments().getInt("number"); // variant number;
        position = getArguments().getInt("position"); // type of the uoe task
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        numberOfQuestions = position == 0 ? 9 : 6;
        rootView = inflater.inflate(R.layout.uoe_tasks_page, container, false);

        db = VariantTask.getDb();

        tasks = new ArrayList<>();
        rightAnswersList = new ArrayList<>();
        typedAnswers = new String[numberOfQuestions];

        assignTasks();

        TextView question1 = rootView.findViewById(R.id.uoe_task1);
        final EditText origin1 = rootView.findViewById(R.id.uoe_answer1);
        TextView question2 = rootView.findViewById(R.id.uoe_task2);
        final EditText origin2 = rootView.findViewById(R.id.uoe_answer2);
        TextView question3 = rootView.findViewById(R.id.uoe_task3);
        final EditText origin3 = rootView.findViewById(R.id.uoe_answer3);
        TextView question4 = rootView.findViewById(R.id.uoe_task4);
        final EditText origin4 = rootView.findViewById(R.id.uoe_answer4);
        TextView question5 = rootView.findViewById(R.id.uoe_task5);
        final EditText origin5 = rootView.findViewById(R.id.uoe_answer5);
        TextView question6 = rootView.findViewById(R.id.uoe_task6);
        final EditText origin6 = rootView.findViewById(R.id.uoe_answer6);

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

        if (numberOfQuestions == 9) {
            TextView question7 = rootView.findViewById(R.id.uoe_task7);
            final EditText origin7 = rootView.findViewById(R.id.uoe_answer7);
            TextView question8 = rootView.findViewById(R.id.uoe_task8);
            final EditText origin8 = rootView.findViewById(R.id.uoe_answer8);
            TextView question9 = rootView.findViewById(R.id.uoe_task9);
            final EditText origin9 = rootView.findViewById(R.id.uoe_answer9);

            question7.setText(tasks.get(6).getQuestion());
            origin7.setHint(tasks.get(6).getOrigin());
            question8.setText(tasks.get(7).getQuestion());
            origin8.setHint(tasks.get(7).getOrigin());
            question9.setText(tasks.get(8).getQuestion());
            origin9.setHint(tasks.get(8).getOrigin());
        }

        rootView.findViewById(R.id.uoe_card10).setVisibility(View.GONE);
        if (numberOfQuestions == 6) {
            rootView.findViewById(R.id.uoe_card9).setVisibility(View.GONE);
            rootView.findViewById(R.id.uoe_card8).setVisibility(View.GONE);
            rootView.findViewById(R.id.uoe_card7).setVisibility(View.GONE);
        }

        // we hide "Проверка" and "Выход" buttons
        rootView.findViewById(R.id.exit_button).setVisibility(View.GONE);
        rootView.findViewById(R.id.submit_button).setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public int checkUoe() {
        rightAnswers = 0;

        final EditText origin1 = rootView.findViewById(R.id.uoe_answer1);
        final EditText origin2 = rootView.findViewById(R.id.uoe_answer2);
        final EditText origin3 = rootView.findViewById(R.id.uoe_answer3);
        final EditText origin4 = rootView.findViewById(R.id.uoe_answer4);
        final EditText origin5 = rootView.findViewById(R.id.uoe_answer5);
        final EditText origin6 = rootView.findViewById(R.id.uoe_answer6);

        checkEditTextAnswer(origin1, 0);
        checkEditTextAnswer(origin2, 1);
        checkEditTextAnswer(origin3, 2);
        checkEditTextAnswer(origin4, 3);
        checkEditTextAnswer(origin5, 4);
        checkEditTextAnswer(origin6, 5);

        if (numberOfQuestions == 9) {
            final EditText origin7 = rootView.findViewById(R.id.uoe_answer7);
            final EditText origin8 = rootView.findViewById(R.id.uoe_answer8);
            final EditText origin9 = rootView.findViewById(R.id.uoe_answer9);

            checkEditTextAnswer(origin7, 6);
            checkEditTextAnswer(origin8, 7);
            checkEditTextAnswer(origin9, 8);
        }

        return rightAnswers;
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

    private void assignTasks() {
        Cursor cursor;
        String selection = Tables.UseOfEnglishTask._ID + " = ?";
        int[] questionsIds = new int[]{};

        if (position == 0) {
            if (number == 0) {
                questionsIds = new int[]{293, 464, 641, 138, 511, 2, 438, 230, 50};
            } else if (number == 1) {
                questionsIds = new int[]{7, 299, 711, 387, 428, 234, 53, 142, 520};
            } else if (number == 2) {
                questionsIds = new int[]{390, 528, 147, 58, 238, 529, 301, 471, 680};
            } else if (number == 3) {
                questionsIds = new int[]{14, 246, 360, 447, 550, 727, 477, 687, 211};
            } else if (number == 4) {
                questionsIds = new int[]{411, 569, 20, 570, 366, 166, 726, 73, 481};
            } else if (number == 5) {
                questionsIds = new int[]{693, 171, 259, 23, 213, 324, 451, 581, 582};
            } else if (number == 6) {
                questionsIds = new int[]{327, 112, 657, 78, 695, 588, 262, 484, 506};
            } else if (number == 7) {
                questionsIds = new int[]{600, 601, 373, 334, 268, 81, 216, 486, 415};
            } else if (number == 8) {
                questionsIds = new int[]{613, 614, 337, 87, 276, 37, 491, 185, 227};
            } else if (number == 9) {
                questionsIds = new int[]{220, 728, 288, 634, 708, 347, 401, 435, 195};
            }
        } else if (position == 1) {
            questionsIds = new int[6];
            int start = 729 + 6 * number;
            int end = 729 + 6 * (number + 1);
            int idx = 0;
            for (int i = start; i < end; i++) {
                questionsIds[idx] = i;
                idx++;
            }
        }

        for (int i = 0; i < (position == 0 ? 9 : 6); i++) {
            String[] selectionArg = new String[]{String.valueOf(questionsIds[i])};
            cursor = db.query(Tables.UseOfEnglishTask.TABLE_NAME, null, selection,
                    selectionArg, null, null, null, null);

            if (cursor != null) {
                try {
                    int taskColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_TASK);
                    int originColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ORIGIN);
                    int answerColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ANSWER);

                    cursor.moveToFirst();
                    String task = cursor.getString(taskColumnIndex);
                    if (questionsIds[i] == 246) {
                        task = "Several benches __________________.";
                    } else if (questionsIds[i] == 259) {
                        task = "Lots of animals __________________ there.";
                    }
                    String origin = cursor.getString(originColumnIndex);
                    String answer = cursor.getString(answerColumnIndex);
                    UoeTask elem = new UoeTask(task, origin, answer);
                    tasks.add(elem);
                    rightAnswersList.add(cursor.getString(answerColumnIndex));
                } finally {
                    cursor.close();
                }
            }
        }
    }

}
