package com.eduapps.edumage.oge_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVVariantsAdapter extends RecyclerView.Adapter<RVVariantsAdapter.VariantsViewHolder> {
    List<VariantCard> variants;

    RVVariantsAdapter(List<VariantCard> variants) {
        this.variants = variants;
    }

    public static class VariantsViewHolder extends RecyclerView.ViewHolder {
        CardView variantCard;
        TextView variantNumber;
        TextView ifSolved;
        ImageView tick;

        VariantsViewHolder(View itemView) {
            super(itemView);
            variantCard = itemView.findViewById(R.id.variant_card);
            variantNumber = itemView.findViewById(R.id.variant_card_name);
            ifSolved = itemView.findViewById(R.id.variant_solved);
            tick = itemView.findViewById(R.id.tick);
        }
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    @NonNull
    @Override
    public VariantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variants_item, parent, false);
        return new VariantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantsViewHolder holder, int position) {
        String variantNumber = "Вариант " + variants.get(position).getVariantNumber();
        holder.variantNumber.setText(variantNumber);
        holder.tick.setImageResource(R.drawable.ic_tick);
        boolean ifSolved = variants.get(position).getHasSolved();
        if (!ifSolved) {
            holder.tick.setVisibility(View.GONE);
            holder.ifSolved.setText(R.string.not_solved);
        } else {
            holder.ifSolved.setText(R.string.solved);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
