package com.eduapps.edumage.oge_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class AudioTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_task);

        Log.v("AudioTaskActivity", "HERE!!");

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        String topic = getResources().getStringArray(R.array.audio_topics)[category];
        setTitle(topic);

        TextView question = findViewById(R.id.audio_question);
        question.setText(R.string.audio_topic1_task1);

//        switch(category) {
//            case 0:
//                TextView question = findViewById(R.id.audio_question);
//                question.setText(R.string.audio_topic1_task1);
//                break;
//        }
    }
}
