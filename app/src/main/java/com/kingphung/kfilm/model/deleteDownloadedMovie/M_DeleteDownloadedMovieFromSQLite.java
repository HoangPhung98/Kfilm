package com.kingphung.kfilm.model.deleteDownloadedMovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kingphung.kfilm.presenter.deleteDownloadedMovie.P_I_DeleteDownloadedMovie;
import com.kingphung.kfilm.sqlite.DownloadedMovieDBHelper;
import com.kingphung.kfilm.ultils.Constant;

public class M_DeleteDownloadedMovieFromSQLite {

    Context context;
    P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie;
    int position;
    String movieName;

    public M_DeleteDownloadedMovieFromSQLite(Context context,
                                             P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie,
                                             int position,
                                             String movieName){
        this.context = context;
        this.p_i_deleteDownloadedMovie = p_i_deleteDownloadedMovie;
        this.position = position;
        this.movieName = movieName;
    }

    public void delete(){

        DownloadedMovieDBHelper downloadedMovieDBHelper = new DownloadedMovieDBHelper(context);
        SQLiteDatabase db = downloadedMovieDBHelper.getWritableDatabase();

        long i  = db.delete(Constant.DB_TABLE_NAME, Constant.COLUMN_NAME + " = '" + movieName + "'", null);

        if(i > -1) {
            Log.d("KingPhung","i in sql" + i);
            p_i_deleteDownloadedMovie.onCompleteDeleteFromSQLite(true, position, (int)(i));
        }
        else{
            p_i_deleteDownloadedMovie.onCompleteDeleteFromSQLite(false, position, (int)(i-1));
        }
    }
}

