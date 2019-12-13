package com.kingphung.kfilm.view.playMovieOffline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.activity.MoviePlayActivity;

public class V_PlayMovieOffline {
    String uri_movieFolder;
    Context context;
    public static Movie movie;
    public V_PlayMovieOffline(Context context, String uri_movieFolder, Movie movie){
        this.uri_movieFolder = uri_movieFolder;
        this.context = context;
        this.movie = movie;
    }

    public void play(){
        Log.v("PLAY***", uri_movieFolder);
        Intent intent = new Intent(context, MoviePlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("IS_PLAY_ONLINE", Constant.OFFLINE);
        bundle.putString("URI_MOVIE_FOLDER",uri_movieFolder);
        bundle.putParcelable("MOVIE", movie);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
