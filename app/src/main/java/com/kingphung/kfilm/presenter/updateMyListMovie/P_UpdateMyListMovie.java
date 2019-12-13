package com.kingphung.kfilm.presenter.updateMyListMovie;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.updateMyListMovie.M_UpdateMyListMovie;
import com.kingphung.kfilm.view.updateMyListMovie.V_I_UpdateMyListMovie;

import java.util.ArrayList;

public class P_UpdateMyListMovie
    implements  P_I_UpdateMyListMovie{
    V_I_UpdateMyListMovie v_i_updateMyListMovie;
    public P_UpdateMyListMovie(V_I_UpdateMyListMovie v_i_updateMyListMovie){
        this.v_i_updateMyListMovie = v_i_updateMyListMovie;
    }
    public void update(){
        M_UpdateMyListMovie m_updateMyListMovie = new M_UpdateMyListMovie(this);
        m_updateMyListMovie.update();
    }

    @Override
    public void onCompleteUpdateMyListMovie(ArrayList<Movie> myListMovie) {
        v_i_updateMyListMovie.onCompleteUpdateMyListMovie(myListMovie);
    }
}
