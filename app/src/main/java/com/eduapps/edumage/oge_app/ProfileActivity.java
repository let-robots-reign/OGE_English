package com.eduapps.edumage.oge_app;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    final String EXPERIENCE_KEY = "Experience";
    final int EXP_PER_LEVEL = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        db = new DbHelper(this).getWritableDatabase();

        TextView username = findViewById(R.id.username);
        TextView goal = findViewById(R.id.goal);
        TextView deadline = findViewById(R.id.deadline);
        TextView plan = findViewById(R.id.plan);

        username.setText(getUserName());
        goal.setText(getUserGoal());
        deadline.setText(getUserDeadline());
        plan.setText(getUserPlanPercentage());
        plan.setTextColor(getResources().getColor(colorUserPlan()));

        ProgressBar levelBar = findViewById(R.id.level_bar);
        levelBar.setProgress(getUserProgress());
        levelBar.setMax(EXP_PER_LEVEL);

        TextView userLevel = findViewById(R.id.user_level);
        userLevel.setText(getUserLevel());

        List<ActivityItem> activities = getLatestActivities();

        ScrollDisabledListView activitiesListView = findViewById(R.id.activities_list);
        ActivitiesAdapter adapter = new ActivitiesAdapter(ProfileActivity.this, activities);
        activitiesListView.setAdapter(adapter);
        // adjust listView's height
        RelativeLayout listBox = findViewById(R.id.listview_box);
        int width = FrameLayout.LayoutParams.WRAP_CONTENT;
        int height = convertDpsToPixels(64 * activities.size());  // one activity takes up 64 dps
        listBox.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        // make scrollView and listView work together
        activitiesListView.setFocusable(false);
        ScrollView scroll = findViewById(R.id.scroll);
        scroll.requestFocus();
    }

    private int convertDpsToPixels(int dps) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);
        //Log.v("ProfileActivity", collectedXP+"");
        return collectedXP % EXP_PER_LEVEL;
    }

    private String getUserLevel() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);
        return String.valueOf(collectedXP / EXP_PER_LEVEL + 1);
    }

    private List<ActivityItem> getLatestActivities() {
        List<ActivityItem> activitiesList = new ArrayList<>();
        activitiesList.add(new ActivityItem("Недавняя активность", 0, 0, 0,
                0));

        Cursor cursor;
        cursor = db.query(Tables.RecentActivities.TABLE_NAME, null, null,
                null, null, null, null);
        if (cursor != null) {
            try {
                // cut the list if it has more than 10 items
                if (cursor.getCount() > 10) {
                    activitiesList.addAll(deleteAndRewriteActivities());
                } else {
                    int topicColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_TOPIC);
                    int rightColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_RIGHT);
                    int totalColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_TOTAL);
                    int expColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_EXP);
                    int dynamicsColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_DYNAMICS);

                    if (cursor.getCount() == 0) {
                        activitiesList.add(new ActivityItem("Ничего не найдено", 0, 0,
                                0, 0));
                    } else {
                        for (int i = 0; i < cursor.getCount(); i++) {
                            cursor.moveToPosition(cursor.getCount() - i - 1);
                            String currentTopic = cursor.getString(topicColumnIndex);
                            int currentRight = cursor.getInt(rightColumnIndex);
                            int currentTotal = cursor.getInt(totalColumnIndex);
                            int currentExp = cursor.getInt(expColumnIndex);
                            int currentDynamics = cursor.getInt(dynamicsColumnIndex);
                            activitiesList.add(new ActivityItem(currentTopic, currentExp, currentRight,
                                    currentTotal, currentDynamics));
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return activitiesList;
    }

    private List<ActivityItem> deleteAndRewriteActivities() {
        List<ActivityItem> activitiesList = new ArrayList<>();

        Cursor cursor;
        cursor = db.query(Tables.RecentActivities.TABLE_NAME, null, null,
                null, null, null, null);
        if (cursor != null) {
            try {
                int topicColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_TOPIC);
                int rightColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_RIGHT);
                int totalColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_TOTAL);
                int expColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_EXP);
                int dynamicsColumnIndex = cursor.getColumnIndex(Tables.RecentActivities.COLUMN_DYNAMICS);

                for (int i = 0; i < 10; i++) {
                    cursor.moveToPosition(cursor.getCount() - i - 1);
                    String currentTopic = cursor.getString(topicColumnIndex);
                    int currentRight = cursor.getInt(rightColumnIndex);
                    int currentTotal = cursor.getInt(totalColumnIndex);
                    int currentExp = cursor.getInt(expColumnIndex);
                    int currentDynamics = cursor.getInt(dynamicsColumnIndex);
                    activitiesList.add(new ActivityItem(currentTopic, currentExp, currentRight,
                            currentTotal, currentDynamics));
                }
            } finally {
                cursor.close();
            }
        }
        db.execSQL("DELETE FROM " + Tables.RecentActivities.TABLE_NAME + " ;");
        cursor = db.query(Tables.RecentActivities.TABLE_NAME, null, null,
                null, null, null, null);
        Log.v("AudioTaskActivity", ""+cursor.getCount());
        // putting all the data in the dbRecent
        ContentValues values = new ContentValues();
        for (int j = 0; j < activitiesList.size(); j++) {
            values.put(Tables.RecentActivities.COLUMN_TOPIC, activitiesList.get(activitiesList.size() - j - 1).getTopicName());
            values.put(Tables.RecentActivities.COLUMN_RIGHT, activitiesList.get(activitiesList.size() - j - 1).getRightAnswers());
            values.put(Tables.RecentActivities.COLUMN_TOTAL, activitiesList.get(activitiesList.size() - j - 1).getTotalPoints());
            values.put(Tables.RecentActivities.COLUMN_EXP, activitiesList.get(activitiesList.size() - j - 1).getExpCollected());
            values.put(Tables.RecentActivities.COLUMN_DYNAMICS, activitiesList.get(activitiesList.size() - j - 1).getDynamics());
            db.insert(Tables.RecentActivities.TABLE_NAME, null, values);
        }
        return activitiesList;
    }
}
