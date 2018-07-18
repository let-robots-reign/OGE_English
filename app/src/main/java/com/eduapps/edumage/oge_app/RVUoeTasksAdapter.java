package com.eduapps.edumage.oge_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVUoeTasksAdapter extends RecyclerView.Adapter<RVUoeTasksAdapter.UoeTasksViewHolder> {
    private List<UoeTask> tasks;
    private String[] answersTyped;

    RVUoeTasksAdapter(List<UoeTask> tasks, String[] answers) {
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
        //Log.v("RVUoeTasksAdapter", tasks.get(0).getQuestion() + " " + tasks.get(1).getQuestion());
        //Log.v("RVUoeTasksAdapter", "" + position);
        holder.question.setText(tasks.get(position).getQuestion());

        holder.answer.setHint(tasks.get(position).getOrigin());
        holder.answer.setTextIsSelectable(false);
        holder.layout.requestFocus();
        holder.answer.setText(answersTyped[position]);

        holder.answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                answersTyped[holder.getAdapterPosition()] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (position == getItemCount() - 1) {
            holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                    holder.layout.getPaddingRight(), 10);
        }
    }
}
