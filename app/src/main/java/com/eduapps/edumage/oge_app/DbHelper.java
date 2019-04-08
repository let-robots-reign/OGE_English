package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "tasks.db";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //super.setForcedUpgrade();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL("UPDATE UseOfEnglish SET answer='AREMADE' WHERE _id=726");
            db.execSQL("UPDATE UseOfEnglish SET answer='ISIMPROVING' WHERE _id=449");
            db.execSQL("UPDATE UseOfEnglish SET answer='GEOGRAPHIC/GEOGRAPHICAL' WHERE _id=754");
            db.execSQL("UPDATE UseOfEnglish SET task='\"Give it to me anyway,\" Bella said. \"If " +
                    "I don’t find a proper pen, I __________________ in green ink. I hope it won’t " +
                    "affect my grades.\"' WHERE _id=402");
            db.execSQL("UPDATE UseOfEnglish SET task='My mum calls me Snow White. It was " +
                    "my __________________  nickname but it stuck to me forever.' WHERE _id=92");
            db.execSQL("UPDATE Writing SET task='Thank you for your recent letter. I was glad to hear from you again.\n" +
                    "Sasha\n" +
                    "May 24th, 2019\n" +
                    "In your letter you asked me about my future job. Well, I’d like to become a programmer as I believe it’s one of the most important professions today. Besides learning how to code, I will need to use English a lot because with the help of English I can get access to information for programmers written in English. Also, I will be able to cooperate with people from all over the world. However, my parents don’t agree with my choice. They advise me to be an accountant as they believe it brings much more money.\n" +
                    "Hope to hear from you soon.\n" +
                    "Moscow, Russia\n" +
                    "Dear Ben,\n" +
                    "Best wishes,' WHERE _id=7");
            db.execSQL("UPDATE Writing SET answer='Moscow, Russia\n" +
                    "May 24th, 2019\n" +
                    "Dear Ben,\n" +
                    "Thank you for your recent letter. I was glad to hear from you again.\n" +
                    "In your letter you asked me about my future job. Well, I’d like to become a programmer as I believe it’s one of the most important professions today. Besides learning how to code, I will need to use English a lot because with the help of English I can get access to information for programmers written in English. Also, I will be able to cooperate with people from all over the world. However, my parents don’t agree with my choice. They advise me to be an accountant as they believe it brings much more money.\n" +
                    "Hope to hear from you soon.\n" +
                    "Best wishes,\n" +
                    "Sasha' WHERE _id=7");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Dont_show_uoe", false);
            editor.apply();
        }
        //super.onUpgrade(db, oldVersion, newVersion);
    }
}
