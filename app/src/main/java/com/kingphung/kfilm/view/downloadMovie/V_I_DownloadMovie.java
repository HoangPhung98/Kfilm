package com.kingphung.kfilm.view.downloadMovie;

import com.kingphung.kfilm.model.Movie;

public interface V_I_DownloadMovie {
    void onCompleteDownload(boolean isDownloadSuccessfully, Movie movie);
}
