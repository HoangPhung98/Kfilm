package com.kingphung.kfilm.presenter.deleteDownloadedMovie;

import android.content.Context;

import com.kingphung.kfilm.model.deleteDownloadedMovie.M_DeleteDownloadMovieFromExternalStorage;
import com.kingphung.kfilm.model.deleteDownloadedMovie.M_DeleteDownloadedMovieFromSQLite;
import com.kingphung.kfilm.view.deleteDownloadedMovie.V_I_DeleteDownloadedMovie;

public class P_DeleteDownloadedMovie implements P_I_DeleteDownloadedMovie{
    Context context;
    V_I_DeleteDownloadedMovie v_i_deleteDownloadedMovie;
    int position;
    String movieName;

    public P_DeleteDownloadedMovie(Context context, V_I_DeleteDownloadedMovie v_i_deleteDownloadedMovie, int position, String movieName){
        this.context = context;
        this.v_i_deleteDownloadedMovie = v_i_deleteDownloadedMovie;
        this.position = position;
        this.movieName = movieName;
    }

    public void delete(){
        //delete form external storage firstly, then come the sqlite
        deleteFromExternalStorage();
    }

    private void deleteFromExternalStorage() {
        M_DeleteDownloadMovieFromExternalStorage m_deleteDownloadMovieFromExternalStorage
                = new M_DeleteDownloadMovieFromExternalStorage(context, this, movieName);
        m_deleteDownloadMovieFromExternalStorage.delete();
    }

    @Override
    public void onCompleteDeleteFromExternalStorage(boolean isDeleteSuccessfully) {
        if(isDeleteSuccessfully) deleteFromSqlite();
        else{
            v_i_deleteDownloadedMovie.onCompleteDeleteDownloadedMovie(false, -1, 0);
        }
    }

    private void deleteFromSqlite() {
        M_DeleteDownloadedMovieFromSQLite m_deleteDownloadedMovieFromSQLite
                = new M_DeleteDownloadedMovieFromSQLite(context, this, position, movieName);
        m_deleteDownloadedMovieFromSQLite.delete();
    }

    @Override
    public void onCompleteDeleteFromSQLite(boolean isDeleteSuccessfully, int position, int numRows) {
        v_i_deleteDownloadedMovie.onCompleteDeleteDownloadedMovie(isDeleteSuccessfully, position, numRows);
    }


}
