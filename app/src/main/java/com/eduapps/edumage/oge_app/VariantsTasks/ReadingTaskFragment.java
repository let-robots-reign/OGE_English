package com.eduapps.edumage.oge_app.VariantsTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.DbHelper;
import com.eduapps.edumage.oge_app.R;
import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadingTaskFragment extends Fragment {
    private int number;
    private int position;

    private List<String> rightAnswersList;
    private List<String> typedAnswers;
    private int rightAnswers;
    private String heading;
    private SQLiteDatabase db;
    private String currentText;
    private String currentQuestion;


    public ReadingTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        number = getArguments().getInt("number"); // variant number;
        position = getArguments().getInt("position"); // type of the reading task
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

        int layout = position == 0 ? R.layout.reading_task_9 : R.layout.reading_tasks_10_17;
        View rootView = inflater.inflate(layout, container, false);

        db = new DbHelper(getActivity()).getReadableDatabase();

        assignQuestion();

        String[] question = new String[]{};
        if (position == 0) {
            ArrayAdapter<String> adapter; // adapter for spinners

            TextView headings = rootView.findViewById(R.id.headings_list);
            headings.setText(currentQuestion.split("Выберите заголовок\n")[1]);

            // spinner options are the list of headings
            String[] spinnerOptions = currentQuestion.split("\n");
            // Spinners for task9
            Spinner spinner1 = rootView.findViewById(R.id.spinner1);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);

            Spinner spinner2 = rootView.findViewById(R.id.spinner2);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);

            Spinner spinner3 = rootView.findViewById(R.id.spinner3);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter);

            Spinner spinner4 = rootView.findViewById(R.id.spinner4);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner4.setAdapter(adapter);

            Spinner spinner5 = rootView.findViewById(R.id.spinner5);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner5.setAdapter(adapter);

            Spinner spinner6 = rootView.findViewById(R.id.spinner6);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapter);

            Spinner spinner7 = rootView.findViewById(R.id.spinner7);
            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, spinnerOptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner7.setAdapter(adapter);

            question = currentText.split("\n");
        } else if (position == 1) {
            TextView headingView = rootView.findViewById(R.id.heading);
            headingView.setText(heading);
            TextView text = rootView.findViewById(R.id.reading_text);
            text.setText(currentText);

            question = currentQuestion.split("\n");
        }

        // first category has 7 questions, 2nd - 8
        TextView question1 = rootView.findViewById(R.id.question1);
        question1.setText(question[0]);

        TextView question2 = rootView.findViewById(R.id.question2);
        question2.setText(question[1].trim());

        TextView question3 = rootView.findViewById(R.id.question3);
        question3.setText(question[2].trim());

        TextView question4 = rootView.findViewById(R.id.question4);
        question4.setText(question[3].trim());

        TextView question5 = rootView.findViewById(R.id.question5);
        question5.setText(question[4].trim());

        TextView question6 = rootView.findViewById(R.id.question6);
        question6.setText(question[5].trim());

        TextView question7 = rootView.findViewById(R.id.question7);
        question7.setText(question[6].trim());

        if (position == 1) {
            TextView question8 = rootView.findViewById(R.id.question8);
            question8.setText(question[7].trim());
        }

        // we hide "Проверка" and "Выход" buttons
        rootView.findViewById(R.id.exit_button).setVisibility(View.GONE);
        rootView.findViewById(R.id.submit_button).setVisibility(View.GONE);

        return rootView;
    }

    private void assignQuestion() {
        rightAnswersList = new ArrayList<>();
        Cursor cursor;
        String selection = Tables.ReadingTask1.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(number + 1)};
        switch (position) {
            case 0:
                cursor = db.query(Tables.ReadingTask1.TABLE_NAME, null, selection,
                        selectionArgs, null, null, "RANDOM()", "1");
                break;
            case 1:
                cursor = db.query(Tables.ReadingTask2.TABLE_NAME, null, selection,
                        selectionArgs, null, null, "RANDOM()", "1");
                int headingColumnIndex = cursor.getColumnIndex(Tables.ReadingTask2.COLUMN_HEADING);
                cursor.moveToFirst();
                heading = cursor.getString(headingColumnIndex);
                break;
            default:
                cursor = null;
        }

        if (cursor != null) {
            try {
                int textColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_TEXT);
                int taskColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_TASK);
                int answerColumnIndex = cursor.getColumnIndex(Tables.ReadingTask1.COLUMN_ANSWER);

                cursor.moveToFirst();
                currentText = cursor.getString(textColumnIndex);
                currentQuestion = cursor.getString(taskColumnIndex);
                String currentAnswer = cursor.getString(answerColumnIndex);

                rightAnswersList.addAll(Arrays.asList(currentAnswer.split(" ")));
            } finally {
                cursor.close();
            }
        }
    }
}
