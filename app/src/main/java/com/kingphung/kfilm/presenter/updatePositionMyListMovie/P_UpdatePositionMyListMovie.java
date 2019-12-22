package com.kingphung.kfilm.presenter.updatePositionMyListMovie;

import android.content.Context;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.updatePositionMyListMovie.M_UpdatePositionMyListMovie;

public class P_UpdatePositionMyListMovie {
    Context context;
    Movie movie;

    public P_UpdatePositionMyListMovie(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
    }
    public void update(){
        M_UpdatePositionMyListMovie m_updatePositionMyListMovie = new M_UpdatePositionMyListMovie(context, movie);
        m_updatePositionMyListMovie.update();
    }
}
