package com.eduapps.edumage.oge_app;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ActivitiesAdapter extends ArrayAdapter<ActivityItem> {

    public ActivitiesAdapter(Activity context, List<ActivityItem> activities) {
        super(context, 0, activities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.recent_activity_item,
                    parent, false);
        }

        ActivityItem currentActivity = getItem(position);
        if (currentActivity != null) {
            TextView activityName = listItemView.findViewById(R.id.category_name);
            activityName.setText(currentActivity.getTopicName());

            TextView experience = listItemView.findViewById(R.id.experience);
            String desc = currentActivity.getExpCollected() + " ед. опыта";
            experience.setText(desc);

            TextView summary = listItemView.findViewById(R.id.summary);
            desc = currentActivity.getRightAnswers() + "/" + currentActivity.getTotalPoints() + " верно";
            summary.setText(desc);
            int color;
            if ((float) currentActivity.getRightAnswers() / currentActivity.getTotalPoints() > 0.75) {
                color = R.color.right_answer;
            } else if ((float) currentActivity.getRightAnswers() / currentActivity.getTotalPoints() >= 0.5) {
                color = R.color.middling;
            } else {
                color = R.color.wrong_answer;
            }
            summary.setTextColor(getContext().getResources().getColor(color));

            ImageView dynamics = listItemView.findViewById(R.id.dynamics);
            if (currentActivity.getDynamics() == 1) {
                dynamics.setImageResource(R.drawable.positive_dynamics);
            } else if (currentActivity.getDynamics() == -1) {
                dynamics.setImageResource(R.drawable.negative_dynamics);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) dynamics.getLayoutParams();
                marginParams.setMargins(0, 48, 0, 0);
            }

            // making topicName smaller if it's too big for a card
            if (currentActivity.getTopicName().length() > 20) {
                activityName.setTextSize(8);
                activityName.setMaxWidth(300);
            }

            if (currentActivity.getTopicName().equals("Недавняя активность")
                    || currentActivity.getTopicName().equals("Ничего не найдено")) {
                experience.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);
                dynamics.setVisibility(View.GONE);
                activityName.setTextSize(20);
                if (currentActivity.getTopicName().equals("Ничего не найдено")) {
                    activityName.setTextSize(16);
                    activityName.setTypeface(null, Typeface.ITALIC);
                }
            }
        }
        return listItemView;
    }
}
