package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
// import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVVariantsAdapter extends RecyclerView.Adapter<RVVariantsAdapter.VariantsViewHolder> {
    List<VariantCard> variants;

    RVVariantsAdapter(List<VariantCard> variants) {
        this.variants = variants;
    }

    public static class VariantsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView variantCard;
        TextView variantNumber;
        TextView ifSolved;
        ImageView tick;

        VariantsViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_variant_card);
            // variantCard = itemView.findViewById(R.id.variant_card);
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
    public void onBindViewHolder(final @NonNull VariantsViewHolder holder, int position) {
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
        // alter paddingBottom of the last element
        if (position == getItemCount() - 1) {
            holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                    holder.layout.getPaddingRight(), 10);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.layout.getContext();
                Intent intent = new Intent(context, VariantTask.class);
                intent.putExtra("number", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
