package com.eduapps.edumage.oge_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrainingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_list);

        RecyclerView trainingsList = findViewById(R.id.trainings_list);
        // optimize recycler view for better performance
        trainingsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trainingsList.setLayoutManager(layoutManager);
        // collect all trainings in a list
        List<Training> trainings = new ArrayList<>();

        trainings.add(new Training(R.string.individual, R.string.progress, R.drawable.ic_individual));
        trainings.add(new Training(R.string.audio, R.string.progress, R.drawable.ic_audio));
        trainings.add(new Training(R.string.reading, R.string.progress, R.drawable.ic_reading));
        trainings.add(new Training(R.string.use_of_english, R.string.progress, R.drawable.ic_use_of_english));
        trainings.add(new Training(R.string.writing, R.string.progress, R.drawable.ic_writing));

        RVAdapter adapter = new RVAdapter(trainings);
        trainingsList.setAdapter(adapter);
    }
}
