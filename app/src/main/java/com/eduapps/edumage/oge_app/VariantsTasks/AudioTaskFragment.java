package com.eduapps.edumage.oge_app.VariantsTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduapps.edumage.oge_app.R;
import com.eduapps.edumage.oge_app.data.Tables;
import com.eduapps.edumage.oge_app.VariantTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;
import static com.eduapps.edumage.oge_app.AudioTaskActivity.hideKeyboard;

public class AudioTaskFragment extends TaskFragment {
    private int position;
    private int number;

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean ifAudioPlaying;
    private ImageView playPauseIcon;

    private List<String> rightAnswersList;
    private int rightAnswers;
    private int retriesCount;

    private SQLiteDatabase db;

    private String currentQuestion;
    private String currentAudioFile;

    private View rootView;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (retriesCount == 1) {
                Toast.makeText(getActivity(), "Вы можете прослушать запись второй раз",
                        Toast.LENGTH_SHORT).show();
            }
            retriesCount -= 1;
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
                //releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                setPlayMode();
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        setPauseMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        setPlayMode();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onDestroy() {
        releaseMediaPlayer();
        super.onDestroy();
    }

    private View.OnTouchListener clickListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            return false;
        }
    };

    public AudioTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        number = getArguments().getInt("number"); // variant number;
        position = getArguments().getInt("position"); // type of the audio task
        setAudioPlayingStatus(false);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        int layout = position < 2 ? R.layout.audio_tasks_1_2 : R.layout.audio_tasks_3_8;
        rootView = inflater.inflate(layout, container, false);

        int deviceHeight = getDeviceHeight();

        CardView audioCard = rootView.findViewById(R.id.audio_task);
        if (position < 2) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) audioCard.getLayoutParams();
            if (deviceHeight < 1000) {
                lp.setMargins(8, 8, 8, 8);
            } else {
                lp.setMargins(16, 8, 16, 8);
            }
            audioCard.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) audioCard.getLayoutParams();
            lp.setMargins(16, 8, 16, 8);
            audioCard.setLayoutParams(lp);
        }

        db = VariantTask.getDb();
        //db = new DbHelper(getActivity()).getReadableDatabase();
        retriesCount = 1; // in варианты user can retry only once
        assignQuestionAndAudio();

        if (position < 2) {
            TextView question = rootView.findViewById(R.id.audio_question);
            // if the question is too big
            question.setText(currentQuestion);
            if (currentQuestion.length() >= 250) {
                if (deviceHeight < 1000) {
                    question.setTextSize(12);
                } else {
                    question.setTextSize(14);
                }
            }
            // category 0 consists of 4 answers cells, not 5, so we'll delete the last one
            if (position == 0) {
                rootView.findViewById(R.id.letter_e).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.letter_e_view).setVisibility(View.GONE);
                rootView.findViewById(R.id.audio_cell_5).setVisibility(View.GONE);
            }

            EditText answer1 = rootView.findViewById(R.id.audio_cell_1);
            EditText answer2 = rootView.findViewById(R.id.audio_cell_2);
            EditText answer3 = rootView.findViewById(R.id.audio_cell_3);
            EditText answer4 = rootView.findViewById(R.id.audio_cell_4);
            EditText answer5 = rootView.findViewById(R.id.audio_cell_5);
//            // change the question color to black again when user changes answer
//            // also, hide keyboard when user wrote a number
            applyTextListener(answer1);
            applyTextListener(answer2);
            applyTextListener(answer3);
            applyTextListener(answer4);
            applyTextListener(answer5);

            answer1.setOnTouchListener(clickListener);
            answer2.setOnTouchListener(clickListener);
            answer3.setOnTouchListener(clickListener);
            answer4.setOnTouchListener(clickListener);
            answer5.setOnTouchListener(clickListener);
        } else {
            String[] question = currentQuestion.split("\n");

            TextView question1 = rootView.findViewById(R.id.question1);
            question1.setText(question[0].split("/option/")[0]);
            String[] options1 = question[0].split("/option/");
            RadioButton option1_1 = rootView.findViewById(R.id.question1_option1);
            option1_1.setText(options1[1]);
            RadioButton option1_2 = rootView.findViewById(R.id.question1_option2);
            option1_2.setText(options1[2]);
            RadioButton option1_3 = rootView.findViewById(R.id.question1_option3);
            option1_3.setText(options1[3]);

            TextView question2 = rootView.findViewById(R.id.question2);
            question2.setText(question[1].split("/option/")[0].trim());
            String[] options2 = question[1].split("/option/");
            RadioButton option2_1 = rootView.findViewById(R.id.question2_option1);
            option2_1.setText(options2[1]);
            RadioButton option2_2 = rootView.findViewById(R.id.question2_option2);
            option2_2.setText(options2[2]);
            RadioButton option2_3 = rootView.findViewById(R.id.question2_option3);
            option2_3.setText(options2[3]);

            TextView question3 = rootView.findViewById(R.id.question3);
            question3.setText(question[2].split("/option/")[0].trim());
            String[] options3 = question[2].split("/option/");
            RadioButton option3_1 = rootView.findViewById(R.id.question3_option1);
            option3_1.setText(options3[1]);
            RadioButton option3_2 = rootView.findViewById(R.id.question3_option2);
            option3_2.setText(options3[2]);
            RadioButton option3_3 = rootView.findViewById(R.id.question3_option3);
            option3_3.setText(options3[3]);

            TextView question4 = rootView.findViewById(R.id.question4);
            question4.setText(question[3].split("/option/")[0].trim());
            String[] options4 = question[3].split("/option/");
            RadioButton option4_1 = rootView.findViewById(R.id.question4_option1);
            option4_1.setText(options4[1]);
            RadioButton option4_2 = rootView.findViewById(R.id.question4_option2);
            option4_2.setText(options4[2]);
            RadioButton option4_3 = rootView.findViewById(R.id.question4_option3);
            option4_3.setText(options4[3]);

            TextView question5 = rootView.findViewById(R.id.question5);
            question5.setText(question[4].split("/option/")[0].trim());
            String[] options5 = question[4].split("/option/");
            RadioButton option5_1 = rootView.findViewById(R.id.question5_option1);
            option5_1.setText(options5[1]);
            RadioButton option5_2 = rootView.findViewById(R.id.question5_option2);
            option5_2.setText(options5[2]);
            RadioButton option5_3 = rootView.findViewById(R.id.question5_option3);
            option5_3.setText(options5[3]);

            TextView question6 = rootView.findViewById(R.id.question6);
            question6.setText(question[5].split("/option/")[0].trim());
            String[] options6 = question[5].split("/option/");
            RadioButton option6_1 = rootView.findViewById(R.id.question6_option1);
            option6_1.setText(options6[1]);
            RadioButton option6_2 = rootView.findViewById(R.id.question6_option2);
            option6_2.setText(options6[2]);
            RadioButton option6_3 = rootView.findViewById(R.id.question6_option3);
            option6_3.setText(options6[3]);
        }

        // we hide "Проверка" and "Выход" buttons
        rootView.findViewById(R.id.exit_button).setVisibility(View.GONE);
        rootView.findViewById(R.id.submit_button).setVisibility(View.GONE);

        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        ifAudioPlaying = false;

        ImageView audioButton = rootView.findViewById(R.id.play_pause_button);
        playPauseIcon = rootView.findViewById(R.id.play_pause_icon);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retriesCount < 0) {
                    Toast.makeText(getActivity(), "Вы больше не можете слушать запись",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (mediaPlayer == null) {
                        if (getAudioPlayingStatus()) {
                            Toast.makeText(getActivity(), "Вы не можете слушать несколько " +
                                    "записей одновременно", Toast.LENGTH_SHORT).show();
                        } else {
                            int res = audioManager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC,
                                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                            if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                                mediaPlayer = MediaPlayer.create(getActivity(),
                                        getResources().getIdentifier(currentAudioFile,
                                                "raw", getActivity().getPackageName()));
                                mediaPlayer.setOnCompletionListener(completionListener);
                                setPlayMode();
                            }
                        }
                    } else {
                        if (ifAudioPlaying) {
                            Toast.makeText(getActivity(), "Вы не можете останавливать запись",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            setPlayMode();
                        }
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public int checkAudio0_1() {
        releaseMediaPlayer();
        setPauseMode();

        rightAnswers = 0;

        EditText answer1 = rootView.findViewById(R.id.audio_cell_1);
        EditText answer2 = rootView.findViewById(R.id.audio_cell_2);
        EditText answer3 = rootView.findViewById(R.id.audio_cell_3);
        EditText answer4 = rootView.findViewById(R.id.audio_cell_4);
        EditText answer5 = rootView.findViewById(R.id.audio_cell_5);

        // color the question green or red
        checkEditTextAnswer(answer1, 0);
        checkEditTextAnswer(answer2, 1);
        checkEditTextAnswer(answer3, 2);
        checkEditTextAnswer(answer4, 3);

        disableEditText(answer1);
        disableEditText(answer2);
        disableEditText(answer3);
        disableEditText(answer4);

        if (position == 1) {
            checkEditTextAnswer(answer5, 4);
            disableEditText(answer5);
        }

        return rightAnswers;
    }

    @Override
    public int checkAudio2() {
        releaseMediaPlayer();
        setPauseMode();

        rightAnswers = 0;

        final RadioGroup options1 = rootView.findViewById(R.id.options1);
        final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options1, radioButton1, 0);

        final RadioGroup options2 = rootView.findViewById(R.id.options2);
        final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options2, radioButton2, 1);

        final RadioGroup options3 = rootView.findViewById(R.id.options3);
        final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options3, radioButton3, 2);

        final RadioGroup options4 = rootView.findViewById(R.id.options4);
        final RadioButton radioButton4 = options4.findViewById(options4.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options4, radioButton4, 3);

        final RadioGroup options5 = rootView.findViewById(R.id.options5);
        final RadioButton radioButton5 = options5.findViewById(options5.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options5, radioButton5, 4);

        final RadioGroup options6 = rootView.findViewById(R.id.options6);
        final RadioButton radioButton6 = options6.findViewById(options6.getCheckedRadioButtonId());
        checkRadioButtonAnswer(options6, radioButton6, 5);

        return rightAnswers;
    }

    public void checkEditTextAnswer(EditText answer, int position) {
        if (answer.getText().toString().equals(rightAnswersList.get(position))) {
            answer.setTextColor(getResources().getColor(R.color.right_answer));
            rightAnswers += 1;
        } else {
            answer.setTextColor(getResources().getColor(R.color.wrong_answer));
        }
    }

    public void checkRadioButtonAnswer(RadioGroup options, RadioButton radioButton, int position) {
        if (radioButton != null) {
            if (options.indexOfChild(radioButton) == Integer.parseInt(rightAnswersList.get(position)) - 1) {
                rightAnswers += 1;
                radioButton.setTextColor(getResources().getColor(R.color.right_answer));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
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
                    hideKeyboard(getActivity());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void disableEditText(EditText editText) {
        editText.setHint("");
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void assignQuestionAndAudio() {
        rightAnswersList = new ArrayList<>();
        Cursor cursor;
        String table;
        switch (position) {
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

        String selection = Tables.AudioTask1.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(number + 1)};
        cursor = db.query(table, null, selection, selectionArgs, null,
                null, null, null);
        if (cursor != null) {
            try {
                // Figure out the index of each column
                int taskColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_TASK);
                int answerColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_ANSWER);
                int audioColumnIndex = cursor.getColumnIndex(Tables.AudioTask1.COLUMN_AUDIO);

                cursor.moveToFirst();
                currentQuestion = cursor.getString(taskColumnIndex);
                String currentAnswer = cursor.getString(answerColumnIndex);
                currentAudioFile = cursor.getString(audioColumnIndex);

                rightAnswersList.addAll(Arrays.asList(currentAnswer.split(" ")));
                if (position == 0) {
                    rightAnswersList.add(""); // dummy
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void setPlayMode() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            ifAudioPlaying = true;
            playPauseIcon.setImageResource(R.drawable.pause_icon);
            setAudioPlayingStatus(true);
        }
    }

    private void setPauseMode() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        ifAudioPlaying = false;
        playPauseIcon.setImageResource(R.drawable.play_triangle);
        setAudioPlayingStatus(false);
    }

    private void setAudioPlayingStatus(boolean status) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ifAudioPlaying", status);
        editor.apply();
    }

    private boolean getAudioPlayingStatus() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getBoolean("ifAudioPlaying", false);
    }

    private int getDeviceHeight() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(changeListener);
        }
    }
}