package com.eduapps.edumage.oge_app;

import android.app.Activity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVUoeTasksAdapter extends RecyclerView.Adapter<RVUoeTasksAdapter.UoeTasksViewHolder> {
    private List<UoeTask> tasks;
    private String[] answersTyped;
    private boolean ifCheck;

    RVUoeTasksAdapter(List<UoeTask> tasks, String[] answers, boolean check) {
        this.ifCheck = check;
        this.tasks = tasks;
        this.answersTyped = answers;
    }

    public static class UoeTasksViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView taskCard;
        TextView question;
        EditText answer;

        UoeTasksViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_uoe_task);
            // taskCard = itemView.findViewById(R.id.uoe_task_card);
            question = itemView.findViewById(R.id.uoe_task);
            answer = itemView.findViewById(R.id.uoe_answer);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @NonNull
    @Override
    public UoeTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uoe_task, parent, false);
        return new UoeTasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull UoeTasksViewHolder holder, int position) {
        holder.question.setText(tasks.get(position).getQuestion());

        holder.answer.setHint(tasks.get(position).getOrigin());
        holder.answer.setTextIsSelectable(false);

        /* if user tapped Submit, checking their answers */
        if (ifCheck) {
            if (answersTyped[position].equals(tasks.get(position).getAnswer())) {
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

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //view.clearFocus();
    }
}
