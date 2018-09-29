package com.eduapps.edumage.oge_app.data;

import android.provider.BaseColumns;

public final class Tables {

    public static abstract class AudioTask1 implements BaseColumns {
        public static final String TABLE_NAME = "Audio_task1";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_AUDIO = "audio";
    }

    public static abstract class AudioTask2 implements BaseColumns {
        public static final String TABLE_NAME = "Audio_task2";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_AUDIO = "audio";
    }

    public static abstract class AudioTask3 implements BaseColumns {
        public static final String TABLE_NAME = "Audio_task3";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_AUDIO = "audio";
    }

    public static abstract class ReadingTask1 implements BaseColumns {
        public static final String TABLE_NAME = "Reading_task1";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_EXPLANATION = "explanation";
    }

    public static abstract class ReadingTask2 implements BaseColumns {
        public static final String TABLE_NAME = "Reading_task2";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_HEADING = "heading";
    }

    public static abstract class UseOfEnglishTask implements BaseColumns {
        public static final String TABLE_NAME = "UseOfEnglish";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_ORIGIN = "origin";
        public static final String COLUMN_ANSWER = "answer";
    }

    public static abstract class RecentActivities implements BaseColumns {
        public static final String TABLE_NAME = "Recent_activities";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_TOPIC = "topic";
        public static final String COLUMN_RIGHT = "right_answers";
        public static final String COLUMN_TOTAL = "total_questions";
        public static final String COLUMN_EXP = "experience";
        public static final String COLUMN_DYNAMICS = "dynamics";
    }
}
