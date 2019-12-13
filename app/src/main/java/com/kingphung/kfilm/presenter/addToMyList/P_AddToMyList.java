package com.kingphung.kfilm.presenter.addToMyList;

import android.content.Context;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.addToMyList.M_AddToMyList;
import com.kingphung.kfilm.view.addToMyList.V_I_AddToMyList;

public class P_AddToMyList
    implements P_I_AddToMyList{

    Context context;
    V_I_AddToMyList v_i_addToMyList;
    Movie movie;

    public P_AddToMyList(Context context, V_I_AddToMyList v_i_addToMyList, Movie movie){
        this.context = context;
        this.v_i_addToMyList = v_i_addToMyList;
        this.movie = movie;
    }

    public void add(){
        M_AddToMyList m_addToMyList = new M_AddToMyList(context, this, movie);
        m_addToMyList.addToFireBase();
    }
    @Override
    public void onCompleteAddToMyList(boolean isSuccessfullyAddToMyList) {
        v_i_addToMyList.onCompleteAddToMyList(isSuccessfullyAddToMyList);
    }
}
