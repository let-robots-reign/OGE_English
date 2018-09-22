package com.eduapps.edumage.oge_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.List;

public class UoeTaskActivity extends AppCompatActivity {

    String[] answersTyped = new String[10];
    private List<UoeTask> tasks;
    private List<String> rightAnswersList;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uoe_tasks_page);

        db = new DbHelper(this).getReadableDatabase();

        for (int i = 0; i < 10; i++) {
            answersTyped[i] = "";
        }

        final RecyclerView uoeTasksList = findViewById(R.id.uoe_tasks_list);

        uoeTasksList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        uoeTasksList.setLayoutManager(layoutManager);

        tasks = new ArrayList<>();
        rightAnswersList = new ArrayList<>();

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        setTitle(getResources().getStringArray(R.array.uoe_topics)[category]);

        generateRandomTasks(category);

        RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped, false);
        uoeTasksList.setAdapter(adapter);

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
                RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped, true);
                uoeTasksList.setAdapter(adapter);

                int rightAnswers = 0;
                for (int i = 0; i < 10; i++) {
                    if (answersTyped[i].equals(rightAnswersList.get(i))) {
                        rightAnswers += 1;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(UoeTaskActivity.this);
                builder.setTitle("Ваш результат:")
                                .setMessage("You have " + rightAnswers + "/" + "10 right answers")
                                .setCancelable(false)
                                .setNegativeButton("Попробовать снова",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                // clear the typed answers
//                                                for (int i = 0; i < 10; i++) {
//                                                    answersTyped[i] = "";
//                                                }
//                                                RVUoeTasksAdapter adapter = new RVUoeTasksAdapter(tasks, answersTyped, false);
//                                                uoeTasksList.setAdapter(adapter);
                                            }
                                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void generateRandomTasks(int category) {
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
                selectionArgs, null, null, "RANDOM()", "10");

        if (cursor != null) {
            try {
                int taskColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_TASK);
                int originColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ORIGIN);
                int answerColumnIndex = cursor.getColumnIndex(Tables.UseOfEnglishTask.COLUMN_ANSWER);

                cursor.moveToFirst();
                for (int i = 0; i < 10; i++) {
                    tasks.add(new UoeTask(cursor.getString(taskColumnIndex),
                            cursor.getString(originColumnIndex),
                            cursor.getString(answerColumnIndex)));
                    rightAnswersList.add(cursor.getString(answerColumnIndex));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }
    }
}
