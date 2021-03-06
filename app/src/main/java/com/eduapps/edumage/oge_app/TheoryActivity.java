package com.eduapps.edumage.oge_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_list);

        RecyclerView theoryList = findViewById(R.id.theory_list);
        // optimize recycler view for better performance
        theoryList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        theoryList.setLayoutManager(layoutManager);
        // collect all trainings in a list
        List<TheoryCard> theory = new ArrayList<>();

        //theory.add(new TheoryCard(R.string.audio, R.drawable.ic_audio, 5, 12));
        //theory.add(new TheoryCard(R.string.reading, R.drawable.ic_reading, 13, 15));
        theory.add(new TheoryCard(R.string.exam_info, R.drawable.ic_exam));
        theory.add(new TheoryCard(R.string.use_of_english, R.drawable.ic_use_of_english));
        theory.add(new TheoryCard(R.string.writing, R.drawable.ic_writing));

        RVTheoryAdapter adapter = new RVTheoryAdapter(theory);
        theoryList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TheoryActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
