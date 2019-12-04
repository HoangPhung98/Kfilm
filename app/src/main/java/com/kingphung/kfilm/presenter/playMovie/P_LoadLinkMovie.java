package com.kingphung.kfilm.presenter.playMovie;

import android.content.Context;

import com.kingphung.kfilm.model.api.API_LoadLinkMovie;
import com.kingphung.kfilm.view.playMovie.V_imp_PlayMovie;

public class P_LoadLinkMovie implements P_Imp_LoadLinkMovie {
    private String ID;
    private Context context;
    private V_imp_PlayMovie v_imp_playMovie;

    public P_LoadLinkMovie(String ID, Context context, V_imp_PlayMovie v_imp_playMovie){
        this.ID = ID;
        this.context = context;
        this.v_imp_playMovie = v_imp_playMovie;

    }
    public void load(){
        API_LoadLinkMovie api = new API_LoadLinkMovie(this, ID, context);
        api.Load_Link_GGDrive_and_Subtitle(ID);
    }


    @Override
    public void onAPIReturnLink(String link_video, String linkSub) {
        v_imp_playMovie.play(link_video, linkSub);
    }
}
