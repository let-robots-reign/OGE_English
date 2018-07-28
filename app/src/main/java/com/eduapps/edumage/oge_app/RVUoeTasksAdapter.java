package com.eduapps.edumage.oge_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVUoeTasksAdapter extends RecyclerView.Adapter<RVUoeTasksAdapter.UoeTasksViewHolder> {
    private List<UoeTask> tasks;
    private String[] answersTyped;
    private boolean ifCheck;

    private OnSubmitButtonClick onSubmitButtonClick;

    RVUoeTasksAdapter(OnSubmitButtonClick onSubmitButtonClick, List<UoeTask> tasks, String[] answers, boolean check) {
        this.ifCheck = check;
        this.tasks = tasks;
        this.answersTyped = answers;
        this.onSubmitButtonClick = onSubmitButtonClick;
    }

    public static class UoeTasksViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView taskCard;
        TextView question;
        EditText answer;
        Button exitButton;
        Button submitButton;

        UoeTasksViewHolder(View itemView, int itemOrButton) {
            super(itemView);
            if (itemOrButton == 0) {
                layout = itemView.findViewById(R.id.layout_uoe_task);
                // taskCard = itemView.findViewById(R.id.uoe_task_card);
                question = itemView.findViewById(R.id.uoe_task);
                answer = itemView.findViewById(R.id.uoe_answer);
            } else {
                exitButton = itemView.findViewById(R.id.exit_button);
                submitButton = itemView.findViewById(R.id.submit_button);
            }
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == tasks.size()) ? R.layout.uoe_buttons : R.layout.uoe_task;
    }

    @NonNull
    @Override
    public UoeTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == R.layout.uoe_task) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uoe_task, parent, false);
            return new UoeTasksViewHolder(view, 0);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uoe_buttons, parent, false);
            return new UoeTasksViewHolder(view, 1);
        }
    }

    @Override
    public void onBindViewHolder(final @NonNull UoeTasksViewHolder holder, int position) {
        Log.v("UoeTaskActivity", ifCheck + "");
        if (position == tasks.size()) {
            holder.exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.exitButton.getContext(), UoeActivity.class);
                    holder.exitButton.getContext().startActivity(intent);
                }
            });

            holder.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String[] rightAnswersList = holder.submitButton.getContext().getResources().getStringArray(R.array.uoe_answers);
//
//                    int rightAnswers = 0;
//                    for (int i = 0; i < 10; i++) {
//                        if (answersTyped[i].equals(rightAnswersList[i])) {
//                            rightAnswers += 1;
//                        }
//                    }
//
//                    Toast.makeText(holder.submitButton.getContext(), "You have " + rightAnswers + "/"
//                            + "10 right answers", Toast.LENGTH_SHORT).show();

                    onSubmitButtonClick.onSubmitClick();
                }
            });
        } else {
            holder.question.setText(tasks.get(position).getQuestion());

            holder.answer.setHint(tasks.get(position).getOrigin());
            holder.answer.setTextIsSelectable(false);

            /* if user tapped Submit, checking their answers */
            if (ifCheck) {
                if (answersTyped[position].equals(holder.answer.getContext().getString(tasks.get(position).getAnswer()))) {
                    holder.answer.setTextColor(holder.answer.getContext().getResources().getColor(R.color.right_answer));
                } else {
                    holder.answer.setTextColor(holder.answer.getContext().getResources().getColor(R.color.wrong_answer));
                }
            }

            holder.answer.setText(answersTyped[position]);

            holder.answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    answersTyped[holder.getAdapterPosition()] = s.toString();
                    holder.answer.setTextColor(holder.answer.getContext().getResources().getColor(R.color.colorPrimaryText));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (!holder.answer.getText().toString().equals("cat")) {
                            hideKeyboard(holder.answer.getContext(), holder.answer);
                        }
                        return true;
                    }
                    return false;
                }
            });

            if (position == getItemCount() - 1) {
                holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                        holder.layout.getPaddingRight(), 10);
            }
        }
    }

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //view.clearFocus();
    }
}
