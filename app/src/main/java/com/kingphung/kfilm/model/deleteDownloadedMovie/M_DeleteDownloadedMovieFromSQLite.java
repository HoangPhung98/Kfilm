package com.kingphung.kfilm.model.deleteDownloadedMovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kingphung.kfilm.presenter.deleteDownloadedMovie.P_I_DeleteDownloadedMovie;
import com.kingphung.kfilm.sqlite.DownloadedMovieDBHelper;
import com.kingphung.kfilm.ultils.Constant;

public class M_DeleteDownloadedMovieFromSQLite {

    Context context;
    P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie;
    String movieName;

    public M_DeleteDownloadedMovieFromSQLite(Context context,
                                             P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie,
                                             String movieName){
        this.context = context;
        this.p_i_deleteDownloadedMovie = p_i_deleteDownloadedMovie;
        this.movieName = movieName;
    }

    public void delete(){

        DownloadedMovieDBHelper downloadedMovieDBHelper = new DownloadedMovieDBHelper(context);
        SQLiteDatabase db = downloadedMovieDBHelper.getWritableDatabase();

        long i  = db.delete(Constant.DB_TABLE_NAME, Constant.COLUMN_NAME + " = '" + movieName + "'", null);

        if(i > -1) {
            p_i_deleteDownloadedMovie.onCompleteDeleteFromSQLite(true, (int)i);
        }
        else{
            p_i_deleteDownloadedMovie.onCompleteDeleteFromSQLite(false, (int)i);
        }
    }
}

