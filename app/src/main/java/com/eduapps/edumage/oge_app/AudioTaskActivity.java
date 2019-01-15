package com.eduapps.edumage.oge_app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class AudioTaskActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean ifAudioPlaying;
    private ImageView playPauseIcon;

    private List<String> rightAnswersList;
    private List<String> typedAnswers;
    private int category;
    private int rightAnswers;
    private boolean canRetry;

    private SQLiteDatabase db;
    final String EXPERIENCE_KEY = "Experience";
    final String AUDIO_FULLY_COMPLETED = "AudioFullCompletion";

    private int currentID;
    private String currentQuestion;
    private String currentAudioFile;
    private int currentCompletion;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            setPauseMode();
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

        db = new DbHelper(this).getWritableDatabase();

        canRetry = true;
        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        assignRandomQuestionAndAudio();

        if (category == 0 || category == 1) {
            // the layout depends on the type of task
            setContentView(R.layout.audio_tasks_1_2);
            TextView question = findViewById(R.id.audio_question);
            // if the question is too big
            question.setText(currentQuestion);
            if (currentQuestion.length() >= 250) {
                question.setTextSize(14);
            }

            // category 0 consists of 4 answers cells, not 5, so we'll delete the last one
            if (category == 0) {
                findViewById(R.id.letter_e).setVisibility(INVISIBLE);
                findViewById(R.id.letter_e_view).setVisibility(GONE);
                findViewById(R.id.audio_cell_5).setVisibility(GONE);
            }

            EditText answer1 = findViewById(R.id.audio_cell_1);
            EditText answer2 = findViewById(R.id.audio_cell_2);
            EditText answer3 = findViewById(R.id.audio_cell_3);
            EditText answer4 = findViewById(R.id.audio_cell_4);
            EditText answer5 = findViewById(R.id.audio_cell_5);
            // change the question color to black again when user changes answer
            // also, hide keyboard when user wrote a number
            applyTextListener(answer1);
            applyTextListener(answer2);
            applyTextListener(answer3);
            applyTextListener(answer4);
            applyTextListener(answer5);
        } else {
            setContentView(R.layout.audio_tasks_3_8);
            String[] question = currentQuestion.split("\n");

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
            question2.setText(question[1].split("/option/")[0].trim());
            String[] options2 = question[1].split("/option/");
            RadioButton option2_1 = findViewById(R.id.question2_option1);
            option2_1.setText(options2[1]);
            RadioButton option2_2 = findViewById(R.id.question2_option2);
            option2_2.setText(options2[2]);
            RadioButton option2_3 = findViewById(R.id.question2_option3);
            option2_3.setText(options2[3]);

            TextView question3 = findViewById(R.id.question3);
            question3.setText(question[2].split("/option/")[0].trim());
            String[] options3 = question[2].split("/option/");
            RadioButton option3_1 = findViewById(R.id.question3_option1);
            option3_1.setText(options3[1]);
            RadioButton option3_2 = findViewById(R.id.question3_option2);
            option3_2.setText(options3[2]);
            RadioButton option3_3 = findViewById(R.id.question3_option3);
            option3_3.setText(options3[3]);

            TextView question4 = findViewById(R.id.question4);
            question4.setText(question[3].split("/option/")[0].trim());
            String[] options4 = question[3].split("/option/");
            RadioButton option4_1 = findViewById(R.id.question4_option1);
            option4_1.setText(options4[1]);
            RadioButton option4_2 = findViewById(R.id.question4_option2);
            option4_2.setText(options4[2]);
            RadioButton option4_3 = findViewById(R.id.question4_option3);
            option4_3.setText(options4[3]);

            TextView question5 = findViewById(R.id.question5);
            question5.setText(question[4].split("/option/")[0].trim());
            String[] options5 = question[4].split("/option/");
            RadioButton option5_1 = findViewById(R.id.question5_option1);
            option5_1.setText(options5[1]);
            RadioButton option5_2 = findViewById(R.id.question5_option2);
            option5_2.setText(options5[2]);
            RadioButton option5_3 = findViewById(R.id.question5_option3);
            option5_3.setText(options5[3]);

            TextView question6 = findViewById(R.id.question6);
            question6.setText(question[5].split("/option/")[0].trim());
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

        typedAnswers = new ArrayList<>();
        // also, behavior of submit button depends on category
        if (category == 0 || category == 1) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseMediaPlayer();
                    setPauseMode();

                    rightAnswers = 0;
                    final int maxRightAnswers;
                    if (category == 0) {
                        maxRightAnswers = 4;
                    } else {
                        maxRightAnswers = 5;
                    }

                    EditText answer1 = findViewById(R.id.audio_cell_1);
                    EditText answer2 = findViewById(R.id.audio_cell_2);
                    EditText answer3 = findViewById(R.id.audio_cell_3);
                    EditText answer4 = findViewById(R.id.audio_cell_4);
                    EditText answer5 = findViewById(R.id.audio_cell_5);
                    // change the question color to black again when user changes answer
                    // also, hide keyboard when user wrote a number
                    applyTextListener(answer1);
                    applyTextListener(answer2);
                    applyTextListener(answer3);
                    applyTextListener(answer4);
                    applyTextListener(answer5);
                    // color the question green or red
                    checkEditTextAnswer(answer1, 0);
                    checkEditTextAnswer(answer2, 1);
                    checkEditTextAnswer(answer3, 2);
                    checkEditTextAnswer(answer4, 3);
                    if (category == 1) {
                        checkEditTextAnswer(answer5, 4);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(AudioTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                                    .setMessage("You have " + rightAnswers + "/"
                                            + maxRightAnswers + " right answers")
                                    .setCancelable(false)
                                    .setPositiveButton("Смотреть ошибки",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(AudioTaskActivity.this, MistakesActivity.class);
                                                    // put category to know the key-value pairs
                                                    intent.putExtra("task_category", "task_" + (category + 1));
                                                    // answers user typed (transforming to String[] array)
                                                    String[] answersArray = typedAnswers.toArray(new String[typedAnswers.size()]);
                                                    intent.putExtra("typed_answers", answersArray);
                                                    // right answers indices (transforming to String[] array)
                                                    String[] rightAnswersArray = rightAnswersList.toArray(new String[rightAnswersList.size()]);
                                                    intent.putExtra("right_answers", rightAnswersArray);
                                                    // options (to get right answers by indices)
                                                    intent.putExtra("question", currentQuestion);
                                                    // id of the task
                                                    intent.putExtra("id", currentID);

                                                    // the result should appear in 'recent activities'
                                                    recordRecentActivity(maxRightAnswers);

                                                    startActivity(intent);
                                                }
                                            });

                    if (canRetry) {
                        builder.setNegativeButton("Попробовать снова",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        typedAnswers.clear();
                                    }
                                });
                        canRetry = false;
                    }

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        } else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseMediaPlayer();
                    setPauseMode();

                    rightAnswers = 0;

                    final RadioGroup options1 = findViewById(R.id.options1);
                    final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options1, radioButton1, 0);

                    final RadioGroup options2 = findViewById(R.id.options2);
                    final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options2, radioButton2, 1);

                    final RadioGroup options3 = findViewById(R.id.options3);
                    final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options3, radioButton3, 2);

                    final RadioGroup options4 = findViewById(R.id.options4);
                    final RadioButton radioButton4 = options4.findViewById(options4.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options4, radioButton4, 3);

                    final RadioGroup options5 = findViewById(R.id.options5);
                    final RadioButton radioButton5 = options5.findViewById(options5.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options5, radioButton5, 4);

                    final RadioGroup options6 = findViewById(R.id.options6);
                    final RadioButton radioButton6 = options6.findViewById(options6.getCheckedRadioButtonId());
                    checkRadioButtonAnswer(options6, radioButton6, 5);

                    applyRadioListener(options1, radioButton1);
                    applyRadioListener(options2, radioButton2);
                    applyRadioListener(options3, radioButton3);
                    applyRadioListener(options4, radioButton4);
                    applyRadioListener(options5, radioButton5);
                    applyRadioListener(options6, radioButton6);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AudioTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/6 right answers")
                            .setCancelable(false)
                            .setPositiveButton("Смотреть ошибки",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(AudioTaskActivity.this, MistakesActivity.class);
                                            // put category to know the key-value pairs
                                            intent.putExtra("task_category", "task_3_8");
                                            // answers user typed (transforming to String[] array)
                                            String[] answersArray = typedAnswers.toArray(new String[typedAnswers.size()]);
                                            intent.putExtra("typed_answers", answersArray);
                                            // right answers indices (transforming to String[] array)
                                            String[] rightAnswersArray = rightAnswersList.toArray(new String[rightAnswersList.size()]);
                                            intent.putExtra("right_answers", rightAnswersArray);
                                            // options (to get right answers by indices)
                                            intent.putExtra("question", currentQuestion);
                                            // id of the task
                                            intent.putExtra("id", currentID);

                                            // the result should appear in 'recent activities'
                                            recordRecentActivity(6);

                                            startActivity(intent);
                                        }
                                    });

                    if (canRetry) {
                        builder.setNegativeButton("Попробовать снова",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        typedAnswers.clear();
                                    }
                                });
                        canRetry = false;
                    }

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
                if (mediaPlayer == null) {
                    int res = audioManager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(AudioTaskActivity.this,
                                getResources().getIdentifier(currentAudioFile, "raw", getPackageName()));
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

        // "Don't show" checkbox goes with the instructions
        View view = getLayoutInflater().inflate(R.layout.dont_show_checkbox, null);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    storeDialogStatus(true);
                } else {
                    storeDialogStatus(false);
                }
            }
        });

        // when a user enters, he should see an instruction to the task
        AlertDialog.Builder builder = new AlertDialog.Builder(AudioTaskActivity.this);
        builder.setTitle("Инструкция")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        switch(category) {
            case 0:
                builder.setMessage("Вы два раза услышите четыре коротких диалога, обозначенных буквами " +
                        "А, B, C, D. Установите соответствие между диалогами и местами, где они " +
                        "происходят: к каждому диалогу подберите соответствующее место действия, " +
                        "обозначенное цифрами. Используйте каждое место действия из списка 1–5 " +
                        "только один раз. В задании есть одно лишнее место действия.");
                break;
            case 1:
                builder.setMessage("Вы два раза услышите пять высказываний, обозначенных буквами " +
                        "А, В, С, D, Е. Установите соответствие между высказываниями и утверждениями " +
                        "из следующего списка: к каждому высказыванию подберите соответствующее " +
                        "утверждение, обозначенное цифрами. Используйте каждое утверждение из списка " +
                        "1-6 только один раз. В задании есть одно лишнее утверждение.");
                break;
            case 2:
                builder.setMessage("Вы услышите разговор двух собеседников. В заданиях 3-8 в поле " +
                        "ответа запишите одну цифру, которая соответствует номеру правильного " +
                        "ответа.");
                break;
        }

        builder.setView(view);
        AlertDialog alert = builder.create();
        if (getDialogStatus()) {
            alert.hide();
        } else {
            alert.show();
        }
    }

    private void storeDialogStatus(boolean isChecked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (category == 0) {
            editor.putBoolean("Dont_show_listening1", isChecked);
        } else if (category == 1) {
            editor.putBoolean("Dont_show_listening2", isChecked);
        } else {
            editor.putBoolean("Dont_show_listening3", isChecked);
        }
        editor.apply();
    }

    private boolean getDialogStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (category == 0) {
            return preferences.getBoolean("Dont_show_listening1", false);
        } else if (category == 1) {
            return preferences.getBoolean("Dont_show_listening2", false);
        } else {
            return preferences.getBoolean("Dont_show_listening3", false);
        }
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

    private void checkEditTextAnswer(EditText answer, int position) {
        if (answer.getText().toString().equals(rightAnswersList.get(position))) {
            answer.setTextColor(getResources().getColor(R.color.right_answer));
            rightAnswers += 1;
        } else {
            answer.setTextColor(getResources().getColor(R.color.wrong_answer));
        }
        typedAnswers.add(answer.getText().toString());
    }

    private void checkRadioButtonAnswer(RadioGroup options, RadioButton radioButton, int position) {
        if (radioButton != null) {
            if (options.indexOfChild(radioButton) == Integer.parseInt(rightAnswersList.get(position)) - 1) {
                rightAnswers += 1;
                radioButton.setTextColor(getResources().getColor(R.color.right_answer));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
            typedAnswers.add("" + options.indexOfChild(radioButton));
        } else {
            typedAnswers.add("-1");
        }
    }

    private void applyTextListener(final EditText answer) {
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                answer.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                if (s.toString().length() > 0) {
                    hideKeyboard(AudioTaskActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void applyRadioListener(RadioGroup options, final RadioButton radioButton) {
        if (radioButton != null) {
            options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioButton.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                }
            });
        }
    }

    private void setPlayMode() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        ifAudioPlaying = true;
        playPauseIcon.setImageResource(R.drawable.pause_icon);
    }

    private void setPauseMode() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        ifAudioPlaying = false;
        playPauseIcon.setImageResource(R.drawable.play_triangle);
    }

    private void assignRandomQuestionAndAudio() {
        rightAnswersList = new ArrayList<>();
        Cursor cursor;
        String table;
        switch(category) {
            case 0:
                table = Tables.AudioTask1.TABLE_NAME;
                break;
            case 1:
                table = Tables.AudioTask2.TABLE_NAME;
                break;
            case 2:
                table = Tables.AudioTask3.TABLE_NAME;
                break;
            default:
                table = null;
        }

        // select the tasks that were done less than twice
        String selection = Tables.AudioTask1.COLUMN_COMPLETION + " < ?";
        String[] selectionArgs = new String[]{"100"};

        cursor = db.query(table, null, selection, selectionArgs, null,
                null, "RANDOM()", "1");

        if (cursor == null) {
            cursor = db.query(table, null, null, null, null,
                    null, "RANDOM()", "1");
        }

        if (cursor != null) {
            try {
                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_ID);
                int taskColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_TASK);
                int answerColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_ANSWER);
                int audioColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_AUDIO);
                int completionColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_COMPLETION);

                cursor.moveToFirst();
                currentID = cursor.getInt(idColumnIndex);
                currentQuestion = cursor.getString(taskColumnIndex);
                String currentAnswer = cursor.getString(answerColumnIndex);
                currentAudioFile = cursor.getString(audioColumnIndex);
                currentCompletion = cursor.getInt(completionColumnIndex);

                rightAnswersList.addAll(Arrays.asList(currentAnswer.split(" ")));
                if (category == 0) {
                    rightAnswersList.add(""); // dummy
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void recordRecentActivity(int totalQuestions) {
        Cursor cursor;

        // forming the data to write
        String topicName = getResources().getStringArray(R.array.audio_topics)[category];
        int exp;
        int dynamics = 0;
        if (category == 0 || category == 1) {
            exp = rightAnswers * 2 * 10;
        } else {
            exp = rightAnswers * 10;
        }

        // if user does the task for the first time, they get more experience
        if (currentCompletion == 50) {
            exp /= 2;
        } else if (currentCompletion == 100) {
            exp = 0;
        }

        // searching for records of the same topic to define dynamics
        String selection = Tables.RecentActivities.COLUMN_TOPIC + " = ?";
        String[] selectionArgs = new String[]{topicName};
        cursor = db.query(Tables.RecentActivities.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    int idRightAnswersColumn = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_RIGHT);
                    int lastResult = cursor.getInt(idRightAnswersColumn);
                    if (rightAnswers > lastResult) {
                        dynamics = 1;
                    } else if (rightAnswers < lastResult) {
                        dynamics = -1;
                    }
                }
            } finally {
                cursor.close();
            }
        }

        // putting all the data in the db
        ContentValues values = new ContentValues();
        values.put(Tables.RecentActivities.COLUMN_TOPIC, topicName);
        values.put(Tables.RecentActivities.COLUMN_RIGHT, rightAnswers);
        values.put(Tables.RecentActivities.COLUMN_TOTAL, totalQuestions);
        values.put(Tables.RecentActivities.COLUMN_EXP, exp);
        values.put(Tables.RecentActivities.COLUMN_DYNAMICS, dynamics);
        db.insert(Tables.RecentActivities.TABLE_NAME, null, values);

        // also, update completion if user did the task well
        if (rightAnswers / totalQuestions >= 0.6 && currentCompletion < 100) {
            if (currentCompletion + 50 == 100) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                if (preferences.contains(AUDIO_FULLY_COMPLETED)) {
                    int full = preferences.getInt(AUDIO_FULLY_COMPLETED, 0);
                    editor.putInt(AUDIO_FULLY_COMPLETED, full + 1);
                    editor.apply();
                } else {
                    editor.putInt(AUDIO_FULLY_COMPLETED, 1);
                }
                editor.apply();
            }

            ContentValues v = new ContentValues();
            v.put("completion", currentCompletion + 50);
            String table;
            switch (category) {
                case 0:
                    table = Tables.AudioTask1.TABLE_NAME;
                    break;
                case 1:
                    table = Tables.AudioTask2.TABLE_NAME;
                    break;
                case 2:
                    table = Tables.AudioTask3.TABLE_NAME;
                    break;
                default:
                    table = null;
            }
            db.update(table, v, "_id=" + currentID, null);
        }

        // add collected experience to user's level
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(EXPERIENCE_KEY)) {
            int collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);
            editor.putInt(EXPERIENCE_KEY, collectedXP + exp);
        } else {
            editor.putInt(EXPERIENCE_KEY, exp);
        }
        editor.apply();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(changeListener);
        }
    }
}
