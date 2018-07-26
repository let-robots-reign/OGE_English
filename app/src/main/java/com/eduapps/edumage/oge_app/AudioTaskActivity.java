package com.eduapps.edumage.oge_app;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
        setContentView(R.layout.audio_task);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        int category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        String topic = getResources().getStringArray(R.array.audio_topics)[category];
        setTitle(topic);

        // TODO: query from DB
        //String currentQuestion = getRandomQuestion();
        final int currentQuestion = R.string.audio_topic1_task1;
        TextView question = findViewById(R.id.audio_question);
        question.setText(currentQuestion);

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

//        switch(category) {
//            case 0:
//                TextView question = findViewById(R.id.audio_question);
//                question.setText(R.string.audio_topic1_task1);
//                break;
//        }
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

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(changeListener);
        }
    }
}
