package com.eduapps.edumage.oge_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.List;

public class TrainingsActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    SQLiteDatabase db;

    private final int AUDIO_TASKS_COUNT = 30;
    private final int READING_TASKS_COUNT = 20;
    private final int UOE_TASKS_COUNT = 1196;
    //private final int WRITING_TASKS_COUNT = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_list);

        db = new DbHelper(this).getReadableDatabase();

        RecyclerView trainingsList = findViewById(R.id.trainings_list);
        // optimize recycler view for better performance
        trainingsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        trainingsList.setLayoutManager(layoutManager);
        // collect all trainings in a list
        List<Training> trainings = new ArrayList<>();

        trainings.add(new Training(R.string.audio, getProgress("audio"), R.drawable.ic_audio));
        trainings.add(new Training(R.string.reading, getProgress("reading"), R.drawable.ic_reading));
        trainings.add(new Training(R.string.use_of_english, getProgress("uoe"), R.drawable.ic_use_of_english));
        trainings.add(new Training(R.string.writing, 0, R.drawable.ic_writing));

        RVTrainingsAdapter adapter = new RVTrainingsAdapter(trainings);
        trainingsList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TrainingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private int getProgress(String category) {
        double progress = 0;
        int tasksCount = 1;
        Cursor cursor;
        List<String> tables = new ArrayList<>();
        String key = "";
        switch (category) {
            case "audio":
                tables.add(Tables.AudioTask1.TABLE_NAME);
                tables.add(Tables.AudioTask2.TABLE_NAME);
                tables.add(Tables.AudioTask3.TABLE_NAME);
                tasksCount = AUDIO_TASKS_COUNT;
                key = "AudioFullCompletion";
                break;
            case "reading":
                tables.add(Tables.ReadingTask1.TABLE_NAME);
                tables.add(Tables.ReadingTask2.TABLE_NAME);
                tasksCount = READING_TASKS_COUNT;
                key = "ReadingFullCompletion";
                break;
            case "uoe":
                tables.add(Tables.UseOfEnglishTask.TABLE_NAME);
                tasksCount = UOE_TASKS_COUNT;
                key = "UoeFullCompletion";
                break;
        }

        // select the tasks that have "completion" = 100
        String selection = "completion = ?";
        String[] selectionArgs = new String[]{"50"};

        for (String table : tables) {
            cursor = db.query(table, null, selection, selectionArgs, null,
                    null, null, null);
            if (cursor != null) {
                try {
                    progress += ((double)cursor.getCount() / 2);
                } finally {
                    cursor.close();
                }
            }
        }

        // and add those that have "completion" = 100
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.contains(key)) {
            progress += preferences.getInt(key, 0);
        }

        return (int)Math.round(progress / tasksCount * 100);
    }
}
