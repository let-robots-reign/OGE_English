package com.eduapps.edumage.oge_app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TrainingsViewHolder>{
    List<Training> trainings;

    RVAdapter(List<Training> trainings){
        this.trainings = trainings;
    }

    public static class TrainingsViewHolder extends RecyclerView.ViewHolder {
        CardView training;
        TextView trainingName;
        TextView progress;
        ImageView trainingIcon;

        TrainingsViewHolder(View itemView) {
            super(itemView);
            training = itemView.findViewById(R.id.training_page);
            trainingName = itemView.findViewById(R.id.training_name);
            progress = itemView.findViewById(R.id.progress);
            trainingIcon = itemView.findViewById(R.id.training_icon);
        }
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    @Override
    public TrainingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainings_item, parent, false);
        return new TrainingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainingsViewHolder holder, int position) {
        holder.trainingName.setText(trainings.get(position).getTrainingName());
        holder.progress.setText(trainings.get(position).getProgress());
        holder.trainingIcon.setImageResource(trainings.get(position).getIconResource());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}