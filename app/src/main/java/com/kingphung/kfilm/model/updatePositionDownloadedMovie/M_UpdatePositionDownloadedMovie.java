package com.kingphung.kfilm.model.updatePositionDownloadedMovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.updateNewPositionToDownloadedMovie.P__I_UpdatePositionDownloadedMovie;
import com.kingphung.kfilm.sqlite.DownloadedMovieDBHelper;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.playMovieOffline.V_PlayMovieOffline;

public class M_UpdatePositionDownloadedMovie {
    Context context;
    Movie movie;
    P__I_UpdatePositionDownloadedMovie p__i_updatePositionDownloadedMovie;
    public M_UpdatePositionDownloadedMovie(Context context, P__I_UpdatePositionDownloadedMovie p__i_updatePositionDownloadedMovie, Movie movie){
        this.context = context;
        this.movie = movie;
        this.p__i_updatePositionDownloadedMovie = p__i_updatePositionDownloadedMovie;
    }
    public void update(){
        DownloadedMovieDBHelper downloadedMovieDBHelper = new DownloadedMovieDBHelper(context);
        SQLiteDatabase db = downloadedMovieDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.COLUMN_NAME, movie.getName());
        values.put(Constant.COLUMN_IMDB, movie.getIMDB());
        values.put(Constant.COLUMN_DIRECTOR, movie.getDirector());
        values.put(Constant.COLUMN_PRODUCTION_YEAR, movie.getProduct_year());
        values.put(Constant.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(Constant.COLUMN_SIZE, movie.getSize());
        values.put(Constant.COLUMN_CURRENT_POSITION, movie.getCurrentPosition());

        long rowID = db.update(Constant.DB_TABLE_NAME, values, Constant.COLUMN_NAME+" = '"+movie.getName()+"'",null);
        Log.d("KingPhung","row "+rowID);
        if(rowID>-1){
            V_PlayMovieOffline.movie.setCurrentPosition(movie.getCurrentPosition());
            p__i_updatePositionDownloadedMovie.onCompleteUpdate(true);
        }

    }
}
