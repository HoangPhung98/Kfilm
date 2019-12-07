package com.kingphung.kfilm.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kingphung.kfilm.ultils.Constant;

public class DownloadedMovieDBHelper extends SQLiteOpenHelper {
    private static String testQuery = "CREATE TABLE MOVIES ( id VARCHAR PRIMARY KEY );";
    private String SQLQuery = "CREATE TABLE CHITIEU " +
            "(ID_CHITIEU INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NGAY VARCHAR, THANG VARCHAR, NAM VARCHAR , " +
            " SOTIEN DOUBLE , TENGIAODICH VARCHAR, LOAIGIAODICH VARCHAR, LOAIVI VARCHAR );";
    private static String QUERY_CREATE_TABLE =
                    "CREATE TABLE " +
                    Constant.DB_TABLE_NAME +
                    "(" +
                    Constant.COLUMN_NAME + " text primary key, " +
                    Constant.COLUMN_IMDB + " text, " +
                    Constant.COLUMN_DIRECTOR + " text, " +
                    Constant.COLUMN_PRODUCTION_YEAR + " text, " +
                    Constant.COLUMN_DESCRIPTION + " text)";

    private static String QUERY_DROP_TABLE ="DROP TABLE IF EXISTS " + Constant.DB_TABLE_NAME;

    public DownloadedMovieDBHelper(@Nullable Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(QUERY_CREATE_TABLE);
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DROP_TABLE);
        onCreate(db);
    }
}
