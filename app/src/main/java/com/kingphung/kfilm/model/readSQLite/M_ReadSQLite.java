package com.kingphung.kfilm.model.readSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.readDownloadedMoviesFromSQLite.P_I_ReadSQLite;
import com.kingphung.kfilm.sqlite.DownloadedMovieDBHelper;
import com.kingphung.kfilm.ultils.Constant;

import java.util.ArrayList;

public class M_ReadSQLite {

    Context context;
    P_I_ReadSQLite p_i_readSQLite;

    ArrayList<Movie> listDownloadedMovie;

    public M_ReadSQLite(Context context, P_I_ReadSQLite p_i_readSQLite){
        this.context = context;
        this.p_i_readSQLite = p_i_readSQLite;
    }

    public void read(){
        DownloadedMovieDBHelper downloadedMovieDBHelper = new DownloadedMovieDBHelper(context);

        SQLiteDatabase db = downloadedMovieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MOVIES", null);

        listDownloadedMovie = new ArrayList<>();
        while (cursor.moveToNext()){
            Movie movie = new Movie(
                    "",
            cursor.getString(cursor.getColumnIndex(Constant.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Constant.COLUMN_PRODUCTION_YEAR)),
                    "",
                    cursor.getString(cursor.getColumnIndex(Constant.COLUMN_IMDB)),
                    "",
                    cursor.getString(cursor.getColumnIndex(Constant.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(Constant.COLUMN_DIRECTOR))
                    );
            listDownloadedMovie.add(movie);
        }

        //return list movie back to presenter
        p_i_readSQLite.onCompleteReadSQLite(listDownloadedMovie);
    }
}
