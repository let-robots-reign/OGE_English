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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eduapps.edumage.oge_app.data.Tables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    final String EXPERIENCE_KEY = "Experience";
    final String NAME_KEY = "UserName";
    final String GRADE_KEY = "UserGrade";
    final int EXP_PER_LEVEL = 300;  // ~10500 exp in total, 35 levels     (10574 (3824) exp int total, (16) 17 levels)
    //final int TOTAL_EXP = 10574;
    final int EXP_FOR_3 = 4500;
    final int EXP_FOR_4 = 7500;
    final int EXP_FOR_5 = 9000;
    private int collectedXP;
    private int planProgress;
    private int userGrade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        db = new DbHelper(this).getWritableDatabase();

        TextView username = findViewById(R.id.username);
        TextView goal = findViewById(R.id.goal);
        TextView deadline = findViewById(R.id.deadline);
        TextView plan = findViewById(R.id.plan);

        ProgressBar levelBar = findViewById(R.id.level_bar);
        levelBar.setProgress(getUserProgress());
        levelBar.setMax(EXP_PER_LEVEL);

        username.setText(getUserName());
        goal.setText(getUserGoal());
        deadline.setText(getUserDeadline());
        plan.setText(getUserPlanPercentage());
        plan.setTextColor(getResources().getColor(colorUserPlan()));

        TextView userLevel = findViewById(R.id.user_level);
        userLevel.setText(getUserLevel());

        List<ActivityItem> activities = getLatestActivities();

        ScrollDisabledListView activitiesListView = findViewById(R.id.activities_list);
        ActivitiesAdapter adapter = new ActivitiesAdapter(ProfileActivity.this, activities);
        activitiesListView.setAdapter(adapter);
        // adjust listView's height
        RelativeLayout listBox = findViewById(R.id.listview_box);
        int width = FrameLayout.LayoutParams.WRAP_CONTENT;
        int height = convertDpsToPixels(68 * activities.size());  // one activity takes up 68 dps
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(NAME_KEY, "Имя не указано");
    }

    private String getUserGoal() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userGrade = preferences.getInt(GRADE_KEY, 0);
        if (userGrade == 0) {
            return "Оценка не была указана";
        }
        return "Цель: оценка \"" + userGrade + "\"";
    }

    private SpannableString getUserDeadline() {
        long days = getDaysTillExam();
        String baseString;
        SpannableString spanText;
        int start = 0;
        if (days == 0) {
            baseString = "экзамен уже сегодня";
        } else if (days < 0) {
            baseString = "экзамен уже прошел";
        } else {
            baseString = "до экзамена " + days + " " + getDeclension(days);
            start = 12;
        }
        spanText = new SpannableString(baseString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), start, baseString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }

    private String getUserPlanPercentage() {
        int total = 0;
        if (userGrade == 0) {
            return "оценка не была указана";
        } else if (userGrade == 3) {
            total = EXP_FOR_3;
        } else if (userGrade == 4) {
            total = EXP_FOR_4;
        } else if (userGrade == 5) {
            total = EXP_FOR_5;
        }
        planProgress = (int)Math.round((double)collectedXP / total * 100);
        return "план выполнен на " + planProgress + "%";
    }

    private int colorUserPlan() {
        if (planProgress < 20) {
            return R.color.wrong_answer;
        } else if (planProgress < 75) {
            return R.color.middling;
        } else {
            return R.color.right_answer;
        }
    }

    private long getDaysTillExam() {
        long diffMillis = new GregorianCalendar(2019, 4, 24).getTimeInMillis()
                - Calendar.getInstance().getTimeInMillis();
        return (int)Math.ceil((double)diffMillis / 1000 / 60 / 60 / 24);
    }

    private String getDeclension(long days) {
        String declension = "дней";
        if (days % 100 == 11 || days % 100 == 12 || days % 100 == 13 || days % 100 == 14) {
            declension = "дней";
        } else if (days % 10 == 2 || days % 10 == 3 || days % 10 == 4) {
            declension = "дня";
        } else if (days % 10 > 4) {
            declension = "дней";
        } else if (days % 10 == 1) {
            declension = "день";
        }
        return declension;
    }

    private int getUserProgress() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        collectedXP = preferences.getInt(EXPERIENCE_KEY, 0);;
        return collectedXP % EXP_PER_LEVEL;
    }

    private String getUserLevel() {
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
