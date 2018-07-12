package com.eduapps.edumage.oge_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class RVTheoryAdapter extends RecyclerView.Adapter<RVTheoryAdapter.TheoryViewHolder> {
    List<TheoryCard> theory;

    RVTheoryAdapter(List<TheoryCard> theory) {
        this.theory = theory;
    }

    public static class TheoryViewHolder extends RecyclerView.ViewHolder {
        CardView theoryCard;
        TextView theoryCardName;
        TextView cardsWatched;
        ImageView theoryCardIcon;
        ProgressBar progressBar;

        TheoryViewHolder(View itemView) {
            super(itemView);
            theoryCard = itemView.findViewById(R.id.theory_card);
            theoryCardName = itemView.findViewById(R.id.theory_card_name);
            cardsWatched = itemView.findViewById(R.id.cards_watched);
            theoryCardIcon = itemView.findViewById(R.id.theory_icon);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    @Override
    public int getItemCount() {
        return theory.size();
    }

    @NonNull
    @Override
    public TheoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theory_item, parent, false);
        return new TheoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheoryViewHolder holder, int position) {
        holder.theoryCardName.setText(theory.get(position).getCardName());
        // making progress bar
        int watched = theory.get(position).getCardsWatched();
        int total = theory.get(position).getCardsTotal();
        String progress = "просмотрено " + watched + "/" + total;
        holder.cardsWatched.setText(progress);
        holder.progressBar.setProgress(watched);
        holder.progressBar.setMax(total);
        //
        holder.theoryCardIcon.setImageResource(theory.get(position).getIconResourse());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}