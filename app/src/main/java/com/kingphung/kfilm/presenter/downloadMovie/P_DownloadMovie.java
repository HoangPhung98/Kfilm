package com.kingphung.kfilm.presenter.downloadMovie;

import android.content.Context;
import android.widget.Toast;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.downloadMovie.M_DownloadMovie;
import com.kingphung.kfilm.presenter.playMovie.P_LoadLinkMovie;
import com.kingphung.kfilm.view.downloadMovie.V_I_DownloadMovie;
import com.kingphung.kfilm.view.playMovie.V_I_LoadLinkMovie;

public class P_DownloadMovie
        implements P_I_DownloadMovie,
        V_I_LoadLinkMovie {
    Movie movie;
    V_I_DownloadMovie v_i_downloadMovie;
    Context context;
    public P_DownloadMovie(Movie movie, V_I_DownloadMovie v_i_downloadMovie, Context context){
        this.movie = movie;
        this.v_i_downloadMovie = v_i_downloadMovie;
        this.context = context;
    }
    public void startDownload(){
        loadLinkMovie();
    }

    private void loadLinkMovie() {
        P_LoadLinkMovie p_loadLinkMovie = new P_LoadLinkMovie(movie.getId(), context, this);
        p_loadLinkMovie.load();
    }

    @Override
    public void onCompleteDownloadMovie(boolean isDownloadSuccessfully, Movie movie) {
        v_i_downloadMovie.onCompleteDownload(isDownloadSuccessfully, movie);
    }

    private void downloadMovieFromUrl(String url) {
        M_DownloadMovie m_downloadMovie = new M_DownloadMovie(movie, this);
        m_downloadMovie.startDownload();
    }
    @Override
    public void onCompleteLoadLink(String url_video, String url_sub) {
        downloadMovieFromUrl(url_video);
    }
}
