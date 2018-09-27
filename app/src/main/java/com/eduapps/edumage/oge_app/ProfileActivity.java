package com.eduapps.edumage.oge_app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        TextView username = findViewById(R.id.username);
        TextView goal = findViewById(R.id.goal);
        TextView deadline = findViewById(R.id.deadline);
        TextView plan = findViewById(R.id.plan);

        username.setText(getUserName());
        goal.setText(getUserGoal());
        deadline.setText(getUserDeadline());
        plan.setText(getUserPlanPercentage());
        plan.setTextColor(colorUserPlan());

        ProgressBar levelBar = findViewById(R.id.level_bar);
        levelBar.setProgress(getUserProgress());

        TextView userLevel = findViewById(R.id.user_level);
        userLevel.setText(getUserLevel());

        List<ActivityItem> activities = getLatestActivities();

        ListView activitiesListView = findViewById(R.id.activities_list);
        ActivitiesAdapter adapter = new ActivitiesAdapter(ProfileActivity.this, activities);
        activitiesListView.setAdapter(adapter);
        //setListViewHeightBasedOnChildren(activitiesListView);
    }

//    /**** Method for Setting the Height of the ListView dynamically.
//     **** Hack to fix the issue of not showing all the items of the ListView
//     **** when placed inside a ScrollView  ****/
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    private String getUserName() {
        return "Алексей Зотов";
    }

    private String getUserGoal() {
        return "Цель: 57 баллов, оценка \"4\"";
    }

    private SpannableString getUserDeadline() {
        String baseString = "до экзамена " + getDaysTillExam() + " дней";
        SpannableString spanText = new SpannableString(baseString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), 12, baseString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }

    private String getUserPlanPercentage() {
        return "план выполнен на 20%";
    }

    private int colorUserPlan() {
        return R.color.wrong_answer;
    }

    private int getDaysTillExam() {
        return 11;
    }

    private int getUserProgress() {
        return 42;
    }

    private String getUserLevel() {
        return "" + 1;
    }

    private List<ActivityItem> getLatestActivities() {
        List<ActivityItem> activitiesList = new ArrayList<>();
        activitiesList.add(new ActivityItem("Задания 3-8", 10, 5, 6));
        activitiesList.add(new ActivityItem("Задание 9", 6, 3, 8));
        activitiesList.add(new ActivityItem("Задание 1", 1, 1, 4));
        activitiesList.add(new ActivityItem("Задания 3-8", 10, 5, 6));
        activitiesList.add(new ActivityItem("Задание 9", 6, 4, 8));
        activitiesList.add(new ActivityItem("Задание 1", 1, 1, 4));
        return activitiesList;
    }
}
