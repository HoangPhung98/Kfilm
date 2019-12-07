package com.kingphung.kfilm.presenter.playMovie;

import android.content.Context;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.api.API_LoadLinkMovie;
import com.kingphung.kfilm.presenter.downloadMovie.P_I_DownloadMovie;
import com.kingphung.kfilm.view.playMovieOnline.V_I_LoadLinkMovie;

public class P_LoadLinkMovie
        implements P_Imp_LoadLinkMovie{
    private String ID;
    private Movie movie;
    private Context context;
    private V_I_LoadLinkMovie v_i_loadLinkMovie = null;
    private P_I_DownloadMovie p_i_downloadMovie = null;

    //load link movie to play
    public P_LoadLinkMovie(String ID, Context context, V_I_LoadLinkMovie v_i_loadLinkMovie){
        this.ID = ID;
        this.context = context;
        this.v_i_loadLinkMovie = v_i_loadLinkMovie;
    }

//    //load link movie to download
//    public P_LoadLinkMovie(String ID, Context context, P_I_DownloadMovie p_i_downloadMovie){
//        this.ID = ID;
//        this.context = context;
//        this.p_i_downloadMovie = p_i_downloadMovie;
//    }
    public void load(){
        API_LoadLinkMovie api = new API_LoadLinkMovie(this, ID, context);
        api.Load_Link_GGDrive_and_Subtitle(ID);
    }


    @Override
    public void onAPIReturnLink(String link_video, String linkSub) {
            v_i_loadLinkMovie.onCompleteLoadLink(link_video, linkSub);
    }
}
