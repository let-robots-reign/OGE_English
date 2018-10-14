package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private int currentCategory;

    RVCategoryAdapter(List<Category> topicsList, int category) {
        this.categoryList = topicsList;
        this.currentCategory = category;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        // CardView topicCard;
        TextView topicCardName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_topic);
            // topicCard = itemView.findViewById(R.id.topic_card);
            topicCardName = itemView.findViewById(R.id.topic_card_name);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull CategoryViewHolder holder, int position) {
        holder.topicCardName.setText(categoryList.get(position).getTopicName());

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
                switch (currentCategory) {
                    case 0:
                        //listening
                        intent = new Intent(context, AudioTaskActivity.class);
                        break;
                    case 1:
                        // reading
                        intent = new Intent(context, ReadingTaskActivity.class);
                        break;
                    case 2:
                        // use of english
                        intent = new Intent(context, UoeTaskActivity.class);
                        break;
                    default:
                        intent = null;
                        break;
                }
                if (intent != null) {
                    // transferring info about the chosen topic
                    intent.putExtra("category", position);
                    context.startActivity(intent);
                }
            }
        });
    }
}
