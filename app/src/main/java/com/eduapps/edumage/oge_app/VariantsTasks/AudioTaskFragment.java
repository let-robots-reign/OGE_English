package com.eduapps.edumage.oge_app.VariantsTasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduapps.edumage.oge_app.DbHelper;
import com.eduapps.edumage.oge_app.R;
import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;

public class AudioTaskFragment extends Fragment {
    private int position;
    private int number;

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private boolean ifAudioPlaying;
    private ImageView playPauseIcon;

    private List<String> rightAnswersList;
    private List<String> typedAnswers;
    private int rightAnswers;
    private int retriesCount;

    private SQLiteDatabase db;

    private int currentID;
    private String currentQuestion;
    private String currentAudioFile;

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
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                setPlayMode();
            }
        }
    };

    public AudioTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        number = getArguments().getInt("number"); // variant number;
        position = getArguments().getInt("position"); // type of the audio task
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        releaseMediaPlayer();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        int layout = position < 2 ? R.layout.audio_tasks_1_2 : R.layout.audio_tasks_3_8;
        View rootView = inflater.inflate(layout, container, false);

        CardView audioCard = rootView.findViewById(R.id.audio_task);
        if (position < 2) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) audioCard.getLayoutParams();
            lp.setMargins(16, 8, 16, 8);
            audioCard.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) audioCard.getLayoutParams();
            lp.setMargins(16, 8, 16, 8);
            audioCard.setLayoutParams(lp);
        }

        db = new DbHelper(getActivity()).getReadableDatabase();
        retriesCount = 1; // in варианты user can retry only once
        assignQuestionAndAudio();

        if (position < 2) {
            TextView question = rootView.findViewById(R.id.audio_question);
            // if the question is too big
            question.setText(currentQuestion);
            if (currentQuestion.length() >= 250) {
                question.setTextSize(14);
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
//            applyTextListener(answer1);
//            applyTextListener(answer2);
//            applyTextListener(answer3);
//            applyTextListener(answer4);
//            applyTextListener(answer5);
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
                        int res = audioManager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC,
                                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                        if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            mediaPlayer = MediaPlayer.create(getActivity(),
                                    getResources().getIdentifier(currentAudioFile,
                                            "raw", getActivity().getPackageName()));
                            setPlayMode();
                            mediaPlayer.setOnCompletionListener(completionListener);
                        }
                    } else {
                        if (ifAudioPlaying) {
                            //setPauseMode();
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

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(changeListener);
        }
    }
}