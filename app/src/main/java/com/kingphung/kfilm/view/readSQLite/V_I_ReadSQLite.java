package com.kingphung.kfilm.view.readSQLite;

import com.kingphung.kfilm.model.Movie;

import java.util.ArrayList;

public interface V_I_ReadSQLite {
    void onCompleteReadSQLite(ArrayList<Movie> listDownloadedMovie);
}
