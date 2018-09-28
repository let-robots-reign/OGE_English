package com.eduapps.edumage.oge_app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            View dynamics = listItemView.findViewById(R.id.dynamics);

            TextView experience = listItemView.findViewById(R.id.experience);
            String desc = currentActivity.getExpCollected() + " ед. опыта";
            experience.setText(desc);

            TextView summary = listItemView.findViewById(R.id.summary);
            desc = currentActivity.getRightAnswers() + "/" + currentActivity.getTotalPoints() + " верно";
            summary.setText(desc);
            if ((float) currentActivity.getRightAnswers() / currentActivity.getTotalPoints() > 0.75) {
                summary.setTextColor(getContext().getResources().getColor(R.color.right_answer));
            } else if ((float) currentActivity.getRightAnswers() / currentActivity.getTotalPoints() >= 0.5) {
                summary.setTextColor(getContext().getResources().getColor(R.color.middling));
            } else {
                summary.setTextColor(getContext().getResources().getColor(R.color.wrong_answer));
            }

            if (currentActivity.getTopicName().equals("Недавняя активность")) {
                experience.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);
                dynamics.setVisibility(View.GONE);
            }
        }
        return listItemView;
    }
}
