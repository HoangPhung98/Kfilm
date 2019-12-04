package com.kingphung.kfilm.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.kingphung.kfilm.ultils.Constant;

public class DownloadedMovieDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "KFILM";
    private static final int DB_VERSION = 1;

    private static String QUERY_CREATE_DATABASE =
            "CREATE TABLE MOVIES (" +
                    Constant.COLUMN_ID + " text primary key, " +
                    Constant.COLUMN_NAME + " text, " +
                    Constant.COLUMN_IMDB + " text, " +
                    Constant.COLUMN_DIRECTOR + " text, " +
                    Constant.COLUMN_PRODUCTION_YEAR + " text, " +
                    Constant.COLUMN_DESCRIPTION + " text, " +
                    Constant.COLUMN_IMAGE + " blob)";

    private static String QUERY_DROP_TABLE ="DROP TABLE IF EXISTS " + DB_NAME;

    public DownloadedMovieDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DROP_TABLE);
        onCreate(db);
    }
}
