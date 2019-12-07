package com.kingphung.kfilm.presenter.readDownloadedMoviesFromSQLite;

import android.content.Context;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.readSQLite.M_ReadSQLite;
import com.kingphung.kfilm.view.readSQLite.V_I_ReadSQLite;

import java.util.ArrayList;

public class P_ReadSQLite implements P_I_ReadSQLite {
    Context context;
    V_I_ReadSQLite v_i_readSQLite;
    public P_ReadSQLite(Context context, V_I_ReadSQLite v_i_readSQLite){
        this.context = context;
        this.v_i_readSQLite = v_i_readSQLite;
    }

    public void read(){
        M_ReadSQLite m_readSQLite = new M_ReadSQLite(context, this);
        m_readSQLite.read();
    }

    @Override
    public void onCompleteReadSQLite(ArrayList<Movie> listDownloadedMovie) {
        //return list movie back to fragment
        v_i_readSQLite.onCompleteReadSQLite(listDownloadedMovie);
    }
}
