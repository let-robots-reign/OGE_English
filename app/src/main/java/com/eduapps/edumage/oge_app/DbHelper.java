package com.eduapps.edumage.oge_app;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String ASSETS_PATH = "databases";
    private SQLiteDatabase db;
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * install or access db
     */
    public void createDB() {
        boolean ifExists = checkDB();
        if (!ifExists) {
            this.getReadableDatabase();
            try {
                installDatabase();
            } catch (IOException e) {
                throw new Error("Error installing DB", e);
            } finally {
                this.close();
            }
        }
    }

    /** This method open database for operations **/
    public boolean openDB() throws SQLException {
        String path = ASSETS_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db.isOpen();
    }

    /** This method close database connection and released occupied memory **/
    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    /**
     * checks if the database already exists
     * @return true if it does, false if it doesn't
     */
    private boolean checkDB() {
        SQLiteDatabase check;
        try {
            String dbPath = ASSETS_PATH + "/" + DATABASE_NAME;
            check = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            throw new Error("Error in checkDB()", e);
        }
        if (check != null) {
            check.close();
        }

        return check != null;
    }

    /**
     * Install database from the assets folder
     */
    private void installDatabase() throws IOException, RuntimeException {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = ASSETS_PATH + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
