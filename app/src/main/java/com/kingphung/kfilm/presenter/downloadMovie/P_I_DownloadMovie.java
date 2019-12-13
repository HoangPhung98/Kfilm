package com.kingphung.kfilm.presenter.downloadMovie;

import com.kingphung.kfilm.model.Movie;

public interface P_I_DownloadMovie {
    void onCompleteDownloadMovie(boolean isDownloadSuccessfully, Movie movie, String size);
}
