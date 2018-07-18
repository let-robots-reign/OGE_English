package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVUoeTopicsAdapter extends RecyclerView.Adapter<RVUoeTopicsAdapter.UoeViewHolder> {
    List<UoeTopic> uoeTopicList;

    RVUoeTopicsAdapter(List<UoeTopic> topicsList) {
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
    public void onBindViewHolder(final @NonNull UoeViewHolder holder, int position) {
        holder.topicCardName.setText(uoeTopicList.get(position).getTopicName());

        if (position == getItemCount()) {
            holder.layout.setPadding(holder.layout.getPaddingLeft(), holder.layout.getPaddingTop(),
                    holder.layout.getPaddingRight(), 10);
        }

        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Context context = holder.layout.getContext();
                Intent intent;
                switch(position) {
                    case 0:
                        intent = new Intent(context, UoeTaskActivity.class);
                        intent.putExtra("category", 0);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, UoeTaskActivity.class);
                        intent.putExtra("category", 1);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }
}
