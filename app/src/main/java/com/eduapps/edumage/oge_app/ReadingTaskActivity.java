package com.eduapps.edumage.oge_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReadingTaskActivity extends AppCompatActivity {

    private List<String> rightAnswersList;
    private int category;
    private String heading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrieving the tasks' category passed from adapter class
        Bundle extras = getIntent().getExtras();
        category = 0;
        if (extras != null) {
            category = extras.getInt("category");
        }

        final int currentText = getRandomText(category);
        final int currentQuestion = getRandomQuestion(category);

        String[] question = new String[]{};
        // the layout depends on the type of the task
        if (category == 0) {
            setContentView(R.layout.reading_task_9);
            setTitle(R.string.reading_topic1);
            TextView headings = findViewById(R.id.headings_list);
            headings.setText(currentQuestion);

            question = getResources().getString(currentText).split("\n");

        } else if (category == 1) {
            setContentView(R.layout.reading_tasks_10_17);
            setTitle(R.string.reading_topic2);
            TextView headingView = findViewById(R.id.heading);
            headingView.setText(heading);
            TextView text = findViewById(R.id.reading_text);
            text.setText(currentText);

            question = getResources().getString(currentQuestion).split("\n");
        }

        TextView question1 = findViewById(R.id.question1);
        question1.setText(question[0]);

        TextView question2 = findViewById(R.id.question2);
        question2.setText(question[1].trim());

        TextView question3 = findViewById(R.id.question3);
        question3.setText(question[2].trim());

        TextView question4 = findViewById(R.id.question4);
        question4.setText(question[3].trim());

        TextView question5 = findViewById(R.id.question5);
        question5.setText(question[4].trim());

        TextView question6 = findViewById(R.id.question6);
        question6.setText(question[5].trim());

        TextView question7 = findViewById(R.id.question7);
        question7.setText(question[6].trim());

        if (category == 1) {
            TextView question8 = findViewById(R.id.question8);
            question8.setText(question[7].trim());
        }

        Button exitButton = findViewById(R.id.exit_button);
        Button submitButton = findViewById(R.id.submit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadingTaskActivity.this, ReadingActivity.class);
                startActivity(intent);
            }
        });

        if (category == 0) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rightAnswers = 0;

                    final EditText answer1 = findViewById(R.id.reading_cell_1);
                    if (answer1.getText().toString().equals(rightAnswersList.get(0))) {
                        answer1.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer1.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer2 = findViewById(R.id.reading_cell_2);
                    if (answer2.getText().toString().equals(rightAnswersList.get(1))) {
                        answer2.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer2.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer3 = findViewById(R.id.reading_cell_3);
                    if (answer3.getText().toString().equals(rightAnswersList.get(2))) {
                        answer3.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer3.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer4 = findViewById(R.id.reading_cell_4);
                    if (answer4.getText().toString().equals(rightAnswersList.get(3))) {
                        answer4.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer4.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer5 = findViewById(R.id.reading_cell_5);
                    if (answer5.getText().toString().equals(rightAnswersList.get(4))) {
                        answer5.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer5.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer6 = findViewById(R.id.reading_cell_6);
                    if (answer6.getText().toString().equals(rightAnswersList.get(5))) {
                        answer6.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer6.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    final EditText answer7 = findViewById(R.id.reading_cell_7);
                    if (answer7.getText().toString().equals(rightAnswersList.get(6))) {
                        answer7.setTextColor(getResources().getColor(R.color.right_answer));
                        rightAnswers += 1;
                    } else {
                        answer7.setTextColor(getResources().getColor(R.color.wrong_answer));
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReadingTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/7 right answers")
                            .setCancelable(false)
                            .setNegativeButton("Попробовать снова",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            answer1.setText("");
                                            answer1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer2.setText("");
                                            answer2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer3.setText("");
                                            answer3.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer4.setText("");
                                            answer4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer5.setText("");
                                            answer5.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer6.setText("");
                                            answer6.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            answer7.setText("");
                                            answer7.setTextColor(getResources().getColor(R.color.colorPrimaryText));
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

                    final RadioGroup options1 = findViewById(R.id.options1);
                    final RadioButton radioButton1 = options1.findViewById(options1.getCheckedRadioButtonId());
                    if (radioButton1 != null) {
                        if (options1.indexOfChild(radioButton1) == Integer.parseInt(rightAnswersList.get(0)) - 1) {
                            rightAnswers += 1;
                            radioButton1.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton1.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options2 = findViewById(R.id.options2);
                    final RadioButton radioButton2 = options2.findViewById(options2.getCheckedRadioButtonId());
                    if (radioButton2 != null) {
                        if (options2.indexOfChild(radioButton2) == Integer.parseInt(rightAnswersList.get(1)) - 1) {
                            rightAnswers += 1;
                            radioButton2.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton2.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options3 = findViewById(R.id.options3);
                    final RadioButton radioButton3 = options3.findViewById(options3.getCheckedRadioButtonId());
                    if (radioButton3 != null) {
                        if (options3.indexOfChild(radioButton3) == Integer.parseInt(rightAnswersList.get(2)) - 1) {
                            rightAnswers += 1;
                            radioButton3.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton3.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options4 = findViewById(R.id.options4);
                    final RadioButton radioButton4 = options4.findViewById(options4.getCheckedRadioButtonId());
                    if (radioButton4 != null) {
                        if (options4.indexOfChild(radioButton4) == Integer.parseInt(rightAnswersList.get(3)) - 1) {
                            rightAnswers += 1;
                            radioButton4.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton4.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options5 = findViewById(R.id.options5);
                    final RadioButton radioButton5 = options5.findViewById(options5.getCheckedRadioButtonId());
                    if (radioButton5 != null) {
                        if (options5.indexOfChild(radioButton5) == Integer.parseInt(rightAnswersList.get(4)) - 1) {
                            rightAnswers += 1;
                            radioButton5.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton5.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options6 = findViewById(R.id.options6);
                    final RadioButton radioButton6 = options6.findViewById(options6.getCheckedRadioButtonId());
                    if (radioButton6 != null) {
                        if (options6.indexOfChild(radioButton6) == Integer.parseInt(rightAnswersList.get(5)) - 1) {
                            rightAnswers += 1;
                            radioButton6.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton6.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options7 = findViewById(R.id.options7);
                    final RadioButton radioButton7 = options7.findViewById(options7.getCheckedRadioButtonId());
                    if (radioButton7 != null) {
                        if (options7.indexOfChild(radioButton7) == Integer.parseInt(rightAnswersList.get(6)) - 1) {
                            rightAnswers += 1;
                            radioButton7.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton7.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    final RadioGroup options8 = findViewById(R.id.options8);
                    final RadioButton radioButton8 = options8.findViewById(options8.getCheckedRadioButtonId());
                    if (radioButton8 != null) {
                        if (options8.indexOfChild(radioButton8) == Integer.parseInt(rightAnswersList.get(7)) - 1) {
                            rightAnswers += 1;
                            radioButton8.setTextColor(getResources().getColor(R.color.right_answer));
                        } else {
                            radioButton8.setTextColor(getResources().getColor(R.color.wrong_answer));
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReadingTaskActivity.this);
                    builder.setTitle("Ваш результат:")
                            .setMessage("You have " + rightAnswers + "/8 right answers")
                            .setCancelable(false)
                            .setNegativeButton("Попробовать снова",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (radioButton1 != null) {
                                                options1.clearCheck();
                                                radioButton1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton2 != null) {
                                                options2.clearCheck();
                                                radioButton2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton3 != null) {
                                                options3.clearCheck();
                                                radioButton3.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton4 != null) {
                                                options4.clearCheck();
                                                radioButton4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton5 != null) {
                                                options5.clearCheck();
                                                radioButton5.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton6 != null) {
                                                options6.clearCheck();
                                                radioButton6.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton7 != null) {
                                                options7.clearCheck();
                                                radioButton7.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                            if (radioButton8 != null) {
                                                options8.clearCheck();
                                                radioButton8.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                                            }
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

    }

    private int getRandomText(int category) {
        switch (category) {
            case 0:
                return R.string.task9_task1_texts1;
            case 1:
                return R.string.reading_topic2_text1;
            default:
                return 0;
        }
    }

    private int getRandomQuestion(int category) {
        rightAnswersList = new ArrayList<>();
        // TODO: query question from DB
        switch (category) {
            case 0:
                rightAnswersList.add("1");
                rightAnswersList.add("3");
                rightAnswersList.add("6");
                rightAnswersList.add("5");
                rightAnswersList.add("8");
                rightAnswersList.add("2");
                rightAnswersList.add("4");
                return R.string.task9_task1_headings;
            case 1:
                rightAnswersList.add("2");
                rightAnswersList.add("1");
                rightAnswersList.add("2");
                rightAnswersList.add("3");
                rightAnswersList.add("1");
                rightAnswersList.add("3");
                rightAnswersList.add("2");
                rightAnswersList.add("2");
                heading = "Two sports brands";
                return R.string.reading_topic2_task1_real;
            default:
                return 0;
        }
    }
}
