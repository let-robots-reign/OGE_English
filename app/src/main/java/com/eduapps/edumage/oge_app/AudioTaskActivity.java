package com.eduapps.edumage.oge_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class AudioTaskActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean ifAudioPlaying;
    private ImageView playPauseIcon;

    private List<String> rightAnswersList;
    private int category;

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
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        final int currentQuestion = getRandomQuestion(category);

        if (category == 0 || category == 1) {
            // the layout depends on the type of task
            setContentView(R.layout.audio_tasks_1_2);
            TextView question = findViewById(R.id.audio_question);
            // if the question is too big
            question.setText(currentQuestion);
            if (getResources().getString(currentQuestion).length() >= 250) {
                question.setTextSize(14);
            }

            // category 0 consists of 4 answers cells so we'll delete the last one
            if (category == 0) {
                findViewById(R.id.letter_e).setVisibility(INVISIBLE);
                findViewById(R.id.letter_e_view).setVisibility(GONE);
                findViewById(R.id.audio_cell_5).setVisibility(GONE);
            }

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

        Button exitButton = findViewById(R.id.exit_button);
        Button submitButton = findViewById(R.id.submit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseMediaPlayer();
                Intent intent = new Intent(AudioTaskActivity.this, AudioActivity.class);
                startActivity(intent);
            }
        });

        // also, behavior of submit button depends on category
        if (category == 0 || category == 1) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rightAnswers = 0;
                    int maxRightAnswers;
                    if (category == 0) {
                        maxRightAnswers = 4;
                    } else {
                        maxRightAnswers = 5;
                    }

                    final EditText answer1 = findViewById(R.id.audio_cell_1);
                    if (answer1.getText().toString().equals(rightAnswersList.get(0))) {
                        answer1.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer1.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer2 = findViewById(R.id.audio_cell_2);
                    if (answer2.getText().toString().equals(rightAnswersList.get(1))) {
                        answer2.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer2.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer3 = findViewById(R.id.audio_cell_3);
                    if (answer3.getText().toString().equals(rightAnswersList.get(2))) {
                        answer3.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer3.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer4 = findViewById(R.id.audio_cell_4);
                    if (answer4.getText().toString().equals(rightAnswersList.get(3))) {
                        answer4.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer4.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer5 = findViewById(R.id.audio_cell_5);
                    if (category == 1) {
                        if (answer5.getText().toString().equals(rightAnswersList.get(4))) {
                            answer5.setTextColor(getResources().getColor(R.color.right_answer));
                            rightAnswers += 1;
                        } else {
                            answer5.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    //Toast.makeText(AudioTaskActivity.this, "You have " + rightAnswers + "/"
                     //       + maxRightAnswers + " right answers", Toast.LENGTH_SHORT).show();

                    // TODO: addTextChangedListener
                    // TODO: AlertDialog

                    AlertDialog.Builder builder = new AlertDialog.Builder(AudioTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                                    .setMessage("You have " + rightAnswers + "/"
                                            + maxRightAnswers + " right answers")
                                    .setCancelable(false)
                                    .setNegativeButton("Попробовать снова",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    releaseMediaPlayer();
                                                    setPauseMode();
                                                    answer1.setText("");
                                                    answer1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                                    answer2.setText("");
                                                    answer2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                                    answer3.setText("");
                                                    answer3.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                                    answer4.setText("");
                                                    answer4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                                    if (category == 1) {
                                                        answer5.setText("");
                                                        answer5.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                                    }
                                                }
                                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        } else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rightAnswers = 0;

                    RadioGroup options1 = findViewById(R.id.options1);
                    final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
                    if (options1.indexOfChild(radioButton1) == Integer.parseInt(rightAnswersList.get(0)) - 1) {
                        rightAnswers += 1;
                        radioButton1.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton1.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    RadioGroup options2 = findViewById(R.id.options2);
                    final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
                    if (options2.indexOfChild(radioButton2) == Integer.parseInt(rightAnswersList.get(1)) - 1) {
                        rightAnswers += 1;
                        radioButton2.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton2.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    RadioGroup options3 = findViewById(R.id.options3);
                    final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
                    if (options3.indexOfChild(radioButton3) == Integer.parseInt(rightAnswersList.get(2)) - 1) {
                        rightAnswers += 1;
                        radioButton3.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton3.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    RadioGroup options4 = findViewById(R.id.options4);
                    final RadioButton radioButton4 = options4.findViewById(options4.getCheckedRadioButtonId());
                    if (options4.indexOfChild(radioButton4) == Integer.parseInt(rightAnswersList.get(3)) - 1) {
                        rightAnswers += 1;
                        radioButton4.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton4.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    RadioGroup options5 = findViewById(R.id.options5);
                    final RadioButton radioButton5 = options5.findViewById(options5.getCheckedRadioButtonId());
                    if (options5.indexOfChild(radioButton5) == Integer.parseInt(rightAnswersList.get(4)) - 1) {
                        rightAnswers += 1;
                        radioButton5.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton5.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    RadioGroup options6 = findViewById(R.id.options6);
                    final RadioButton radioButton6 = options6.findViewById(options6.getCheckedRadioButtonId());
                    if (options6.indexOfChild(radioButton6) == Integer.parseInt(rightAnswersList.get(5)) - 1) {
                        rightAnswers += 1;
                        radioButton6.setTextColor(getResources().getColor(R.color.right_answer));
                    } else {
                        radioButton6.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(AudioTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/8 right answers")
                            .setCancelable(false)
                            .setNegativeButton("Попробовать снова",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            releaseMediaPlayer();
                                            setPauseMode();
                                            radioButton1.setChecked(false);
                                            radioButton1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            radioButton2.setChecked(false);
                                            radioButton2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            radioButton3.setChecked(false);
                                            radioButton3.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            radioButton4.setChecked(false);
                                            radioButton4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            radioButton5.setChecked(false);
                                            radioButton5.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            radioButton6.setChecked(false);
                                            radioButton6.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
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

    @Override
    public void onBackPressed() {
        // when user exits via "back" we also need to stop the audio
        releaseMediaPlayer();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // the same behavior for the "up/home" button in the Action Bar
        switch(item.getItemId()) {
            case android.R.id.home:
                releaseMediaPlayer();
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        switch (question) {
            case R.string.audio_topic1_task1:
                return R.raw.audio_topic1_task1;
            case R.string.audio_topic2_task1:
                return R.raw.audio_topic2_task1;
            case R.string.audio_topics_3_8_task1:
                return R.raw.audio_topic3_task1;
            default:
                return 0;
        }
    }

    private int getRandomQuestion(int category) {
        rightAnswersList = new ArrayList<>();
        // TODO: query question from DB
        switch(category) {
            case 0:
                rightAnswersList.add("4");
                rightAnswersList.add("2");
                rightAnswersList.add("5");
                rightAnswersList.add("1");
                rightAnswersList.add(""); // dummy
                return R.string.audio_topic1_task1;
            case 1:
                rightAnswersList.add("3");
                rightAnswersList.add("5");
                rightAnswersList.add("1");
                rightAnswersList.add("6");
                rightAnswersList.add("2");
                return R.string.audio_topic2_task1;
            case 2:
                rightAnswersList.add("1");
                rightAnswersList.add("3");
                rightAnswersList.add("1");
                rightAnswersList.add("2");
                rightAnswersList.add("3");
                rightAnswersList.add("1");
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

    private int getHeightOfView(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //contentview.getMeasuredWidth();
        return view.getMeasuredHeight();
    }
}
