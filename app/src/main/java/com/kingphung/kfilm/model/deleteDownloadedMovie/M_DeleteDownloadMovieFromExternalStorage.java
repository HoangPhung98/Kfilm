package com.kingphung.kfilm.model.deleteDownloadedMovie;

import android.content.Context;
import android.os.Environment;

import com.kingphung.kfilm.presenter.deleteDownloadedMovie.P_I_DeleteDownloadedMovie;

import java.io.File;

public class M_DeleteDownloadMovieFromExternalStorage {

    Context context;
    P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie;
    String movieName;

    public M_DeleteDownloadMovieFromExternalStorage(Context context, P_I_DeleteDownloadedMovie p_i_deleteDownloadedMovie, String movieName){
        this.context = context;
        this.p_i_deleteDownloadedMovie = p_i_deleteDownloadedMovie;
        this.movieName = movieName;
    }

    public void delete(){
        Boolean isDeleteSuccessfully = recursiveDelete(new File(getDirPath(movieName)));
        p_i_deleteDownloadedMovie.onCompleteDeleteFromExternalStorage(isDeleteSuccessfully);
    }

    private boolean recursiveDelete(File path){
        if(path.exists()){
            File[] files = path.listFiles();

            if(files == null) return true;

            for(int i=0; i<files.length; i++){
                if (files[i].isDirectory()) recursiveDelete(files[i]);
                else files[i].delete();
            }
        }
        return path.delete();
    }

    private String getDirPath(String movieName) {
        String folderPath = Environment.getExternalStorageDirectory()+
                File.separator +
                "Kfilm" +
                File.separator +
                movieName;
        return folderPath;
    }


}
