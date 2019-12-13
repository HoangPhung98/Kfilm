package com.kingphung.kfilm.presenter.updateNewPositionToDownloadedMovie;

import android.content.Context;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.updatePositionDownloadedMovie.M_UpdatePositionDownloadedMovie;
import com.kingphung.kfilm.view.updatePositionDownloadedMovie.V_I_UpdatePositionDownloadedMovie;

public class P_UpdatePositionDownloadedMovie
        implements P__I_UpdatePositionDownloadedMovie{
    Context context;
    V_I_UpdatePositionDownloadedMovie v_i_updatePositionDownloadedMovie;
    Movie movie;
    public P_UpdatePositionDownloadedMovie(Context context, V_I_UpdatePositionDownloadedMovie v_i_updatePositionDownloadedMovie, Movie movie){
        this.context = context;
        this.v_i_updatePositionDownloadedMovie = v_i_updatePositionDownloadedMovie;
        this.movie = movie;
    }

    public void update(){
        M_UpdatePositionDownloadedMovie m_updatePositionDownloadedMovie = new M_UpdatePositionDownloadedMovie(context, this, movie);
        m_updatePositionDownloadedMovie.update();
    }
    @Override
    public void onCompleteUpdate(boolean isSuccessfullyUpdate) {
        v_i_updatePositionDownloadedMovie.onCompleteUpdate(isSuccessfullyUpdate);
    }
}
