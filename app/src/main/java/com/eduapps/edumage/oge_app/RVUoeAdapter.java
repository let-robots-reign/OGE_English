package com.eduapps.edumage.oge_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVUoeAdapter extends RecyclerView.Adapter<RVUoeAdapter.UoeViewHolder> {
    List<UoeTopic> uoeTopicList;

    RVUoeAdapter(List<UoeTopic> topicsList) {
        this.uoeTopicList = topicsList;
    }

    public static class UoeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView topicCard;
        TextView topicCardName;

        UoeViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_uoe_card);
            // topicCard = itemView.findViewById(R.id.uoe_topic_card);
            topicCardName = itemView.findViewById(R.id.uoe_topic_card_name);
        }
    }

    @Override
    public int getItemCount() {
        return uoeTopicList.size();
    }

    @NonNull
    @Override
    public UoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uoe_topic_item, parent, false);
        return new UoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UoeViewHolder holder, int position) {
        holder.topicCardName.setText(uoeTopicList.get(position).getTopicName());

        if (position == getItemCount()) {
            holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                    holder.layout.getPaddingRight(), 10);
        }
    }
}
