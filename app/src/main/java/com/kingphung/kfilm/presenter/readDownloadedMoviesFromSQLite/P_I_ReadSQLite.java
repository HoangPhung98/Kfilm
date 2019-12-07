package com.kingphung.kfilm.presenter.readDownloadedMoviesFromSQLite;

import com.kingphung.kfilm.model.Movie;

import java.util.ArrayList;

public interface P_I_ReadSQLite {
    void onCompleteReadSQLite(ArrayList<Movie> listDownloadedMovie);
}
