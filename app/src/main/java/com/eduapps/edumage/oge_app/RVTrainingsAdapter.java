package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
// import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class RVTrainingsAdapter extends RecyclerView.Adapter<RVTrainingsAdapter.TrainingsViewHolder> {
    List<Training> trainings;

    RVTrainingsAdapter(List<Training> trainings){
        this.trainings = trainings;
    }

    public static class TrainingsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView training;
        TextView trainingName;
        TextView progress;
        ProgressBar progressBar;
        ImageView trainingIcon;

        TrainingsViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_trainings_card);
            // training = itemView.findViewById(R.id.training_page);
            trainingName = itemView.findViewById(R.id.training_name);
            progress = itemView.findViewById(R.id.progress);
            progressBar = itemView.findViewById(R.id.progress_bar);
            trainingIcon = itemView.findViewById(R.id.training_icon);
        }
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    @NonNull
    @Override
    public TrainingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainings_item, parent, false);
        return new TrainingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull TrainingsViewHolder holder, int position) {
        holder.trainingName.setText(trainings.get(position).getTrainingName());
//        if (trainings.get(position).getTrainingName() == R.string.individual) {
//            holder.trainingName.setTextSize(14);
//        }
        // make a progress bar
        String progress = "прогресс " + trainings.get(position).getProgress() + "%";
        holder.progress.setText(progress);
        holder.progressBar.setProgress(trainings.get(position).getProgress());
        //

        holder.trainingIcon.setImageResource(trainings.get(position).getIconResource());
        // alter paddingBottom of the last element
        if (position == getItemCount() - 1) {
            holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                    holder.layout.getPaddingRight(), 10);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Context context = holder.layout.getContext();
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, IndividualTask.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, AudioTask.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, ReadingTask.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, UoeActivity.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context, WritingTask.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}