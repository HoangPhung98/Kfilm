package com.kingphung.kfilm.model.updateSqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.updateSqliteDatabase.P_I_UpdateSqliteDatabase;
import com.kingphung.kfilm.sqlite.DownloadedMovieDBHelper;
import com.kingphung.kfilm.ultils.Constant;

public class M_UpdateSqliteDatabase {
    Context context;
    Movie movie;
    P_I_UpdateSqliteDatabase p_i_updateSqliteDatabase;
    String size;
    public M_UpdateSqliteDatabase(Context context, Movie movie, P_I_UpdateSqliteDatabase p_i_updateSqliteDatabase, String size){
        this.context = context;
        this.movie = movie;
        this.p_i_updateSqliteDatabase = p_i_updateSqliteDatabase;
        this.size = size;
    }
    public void insert(){
        DownloadedMovieDBHelper downloadedMovieDBHelper = new DownloadedMovieDBHelper(context);
        SQLiteDatabase db = downloadedMovieDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.COLUMN_NAME, movie.getName());
        values.put(Constant.COLUMN_IMDB, movie.getIMDB());
        values.put(Constant.COLUMN_DIRECTOR, movie.getDirector());
        values.put(Constant.COLUMN_PRODUCTION_YEAR, movie.getProduct_year());
        values.put(Constant.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(Constant.COLUMN_SIZE, size);
        values.put(Constant.COLUMN_CURRENT_POSITION, "0");

        long rowID = db.insert(Constant.DB_TABLE_NAME, null, values);
        p_i_updateSqliteDatabase.onCompleteUpdateSqliteDatabase(true);
    }

}
