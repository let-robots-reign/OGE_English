package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class RVTheoryAdapter extends RecyclerView.Adapter<RVTheoryAdapter.TheoryViewHolder> {
    List<TheoryCard> theory;

    RVTheoryAdapter(List<TheoryCard> theory) {
        this.theory = theory;
    }

    public static class TheoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView theoryCardName;
        ImageView theoryCardIcon;

        TheoryViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_theory_card);
            theoryCardName = itemView.findViewById(R.id.theory_card_name);
            theoryCardIcon = itemView.findViewById(R.id.theory_icon);
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
    public void onBindViewHolder(final @NonNull TheoryViewHolder holder, int position) {
        holder.theoryCardName.setText(theory.get(position).getCardName());
        holder.theoryCardIcon.setImageResource(theory.get(position).getIconResourse());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Context context = holder.layout.getContext();
                Intent intent;
                intent = new Intent(context, TheoryItemsActivity.class);
                intent.putExtra("category", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}