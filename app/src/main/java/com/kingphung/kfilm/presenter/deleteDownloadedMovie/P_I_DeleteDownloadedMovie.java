package com.kingphung.kfilm.presenter.deleteDownloadedMovie;

public interface P_I_DeleteDownloadedMovie {
    void onCompleteDeleteFromSQLite(boolean isDeleteSuccessfully, int position);
    void onCompleteDeleteFromExternalStorage(boolean isDeleteSuccessfully);
}
