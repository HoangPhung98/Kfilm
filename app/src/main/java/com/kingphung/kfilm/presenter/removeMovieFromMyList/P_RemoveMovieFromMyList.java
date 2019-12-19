package com.kingphung.kfilm.presenter.removeMovieFromMyList;

import android.content.Context;

import com.kingphung.kfilm.model.removeMovieFromMyList.M_RemoveMovieFromMyList;
import com.kingphung.kfilm.view.removeMovieFromMyList.V_I_RemoveMovieFromMyList;

public class P_RemoveMovieFromMyList
    implements P_I_RemoveMovieFromMyList {
    Context context;
    V_I_RemoveMovieFromMyList v_i_removeMovieFromMyList;

    public P_RemoveMovieFromMyList(Context context, V_I_RemoveMovieFromMyList v_i_removeMovieFromMyList) {
        this.context = context;
        this.v_i_removeMovieFromMyList = v_i_removeMovieFromMyList;
    }

    public void remove(String movieName){
        M_RemoveMovieFromMyList m_removeMovieFromMyList = new M_RemoveMovieFromMyList(context, this);
        m_removeMovieFromMyList.remove(movieName);
    }

    @Override
    public void onCompleteRemoveFromMyList(boolean isSuccessfullyRemoveFromMyList) {
        v_i_removeMovieFromMyList.onCompleteRemoveMovieFromMyList(isSuccessfullyRemoveFromMyList);
    }
}
