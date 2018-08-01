package com.eduapps.edumage.oge_app;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class AudioTaskActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean ifAudioPlaying;
    private ImageView playPauseIcon;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener changeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                setPauseMode();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                setPauseMode();
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                setPlayMode();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        final int currentQuestion = getRandomQuestion(category);

        if (category == 0 || category == 1) {
            setContentView(R.layout.audio_tasks_1_2);
            TextView question = findViewById(R.id.audio_question);
            question.setText(currentQuestion);
        } else {
            setContentView(R.layout.audio_tasks_3_8);
            String[] question = getResources().getString(currentQuestion).split("\n");

            TextView question1 = findViewById(R.id.question1);
            question1.setText(question[0].split("/option/")[0]);
            String[] options1 = question[0].split("/option/");
            RadioButton option1_1 = findViewById(R.id.question1_option1);
            option1_1.setText(options1[1]);
            RadioButton option1_2 = findViewById(R.id.question1_option2);
            option1_2.setText(options1[2]);
            RadioButton option1_3 = findViewById(R.id.question1_option3);
            option1_3.setText(options1[3]);

            TextView question2 = findViewById(R.id.question2);
            question2.setText(question[1].split("/option/")[0]);
            String[] options2 = question[1].split("/option/");
            RadioButton option2_1 = findViewById(R.id.question2_option1);
            option2_1.setText(options2[1]);
            RadioButton option2_2 = findViewById(R.id.question2_option2);
            option2_2.setText(options2[2]);
            RadioButton option2_3 = findViewById(R.id.question2_option3);
            option2_3.setText(options2[3]);

            TextView question3 = findViewById(R.id.question3);
            question3.setText(question[2].split("/option/")[0]);
            String[] options3 = question[2].split("/option/");
            RadioButton option3_1 = findViewById(R.id.question3_option1);
            option3_1.setText(options3[1]);
            RadioButton option3_2 = findViewById(R.id.question3_option2);
            option3_2.setText(options3[2]);
            RadioButton option3_3 = findViewById(R.id.question3_option3);
            option3_3.setText(options3[3]);

            TextView question4 = findViewById(R.id.question4);
            question4.setText(question[3].split("/option/")[0]);
            String[] options4 = question[3].split("/option/");
            RadioButton option4_1 = findViewById(R.id.question4_option1);
            option4_1.setText(options4[1]);
            RadioButton option4_2 = findViewById(R.id.question4_option2);
            option4_2.setText(options4[2]);
            RadioButton option4_3 = findViewById(R.id.question4_option3);
            option4_3.setText(options4[3]);

            TextView question5 = findViewById(R.id.question5);
            question5.setText(question[4].split("/option/")[0]);
            String[] options5 = question[4].split("/option/");
            RadioButton option5_1 = findViewById(R.id.question5_option1);
            option5_1.setText(options5[1]);
            RadioButton option5_2 = findViewById(R.id.question5_option2);
            option5_2.setText(options5[2]);
            RadioButton option5_3 = findViewById(R.id.question5_option3);
            option5_3.setText(options5[3]);

            TextView question6 = findViewById(R.id.question6);
            question6.setText(question[5].split("/option/")[0]);
            String[] options6 = question[5].split("/option/");
            RadioButton option6_1 = findViewById(R.id.question6_option1);
            option6_1.setText(options6[1]);
            RadioButton option6_2 = findViewById(R.id.question6_option2);
            option6_2.setText(options6[2]);
            RadioButton option6_3 = findViewById(R.id.question6_option3);
            option6_3.setText(options6[3]);

        }

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);


        String topic = getResources().getStringArray(R.array.audio_topics)[category];
        setTitle(topic);

        ifAudioPlaying = false;

        ImageView audioButton = findViewById(R.id.play_pause_button);
        playPauseIcon = findViewById(R.id.play_pause_icon);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifAudioPlaying = !ifAudioPlaying;
                Log.v("AudioTaskActivity", "" + ifAudioPlaying);
                if (mediaPlayer == null) {
                    int res = audioManager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(AudioTaskActivity.this, getAudio(currentQuestion));
                        setPlayMode();
                        mediaPlayer.setOnCompletionListener(completionListener);
                    }
                } else {
                    if (ifAudioPlaying) {
                        setPauseMode();
                    } else {
                        setPlayMode();
                    }
                }
            }
        });
    }

    private void setPlayMode() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        playPauseIcon.setImageResource(R.drawable.pause_icon);
    }

    private void setPauseMode() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        playPauseIcon.setImageResource(R.drawable.play_triangle);
    }

    private int getAudio(int question) {
        // TODO: query audio file from DB
        return R.raw.audio_topic1_task1;
    }

    private int getRandomQuestion(int category) {
        // TODO: query question from DB
        switch(category) {
            case 0:
                return R.string.audio_topic1_task1;
            case 1:
                return R.string.audio_topic2_task1;
            case 2:
                return R.string.audio_topics_3_8_task1;
            default:
                return 0;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(changeListener);
        }
    }
}
