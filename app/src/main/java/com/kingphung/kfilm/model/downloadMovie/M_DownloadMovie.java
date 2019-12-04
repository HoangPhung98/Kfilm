package com.kingphung.kfilm.model.downloadMovie;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.downloadMovie.P_I_DownloadMovie;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class M_DownloadMovie {
    //for debug
    String url_test = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
    Movie movie;
    P_I_DownloadMovie p_i_downloadMovie;
    public M_DownloadMovie(Movie movie, P_I_DownloadMovie p_i_downloadMovie){
        this.movie = movie;
        this.p_i_downloadMovie = p_i_downloadMovie;
    }

    public void startDownload(){
        new DownloadMovieAsyncTask().execute(url_test);
    }

    private class DownloadMovieAsyncTask extends AsyncTask<String, String, String>{

        private String fileName;
        private String folderName;
        private boolean isDownloadSuccessfully;

        @Override
        protected String doInBackground(String... strings) {
            int count;
            long total;
            int lengthOfFile;

            try {
                //set up url
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                //get length of mp file
                lengthOfFile = connection.getContentLength();

                //set up input stream to fetch data from connection
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                //uri for file to store in device
                //time stamp
                //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                //extract file name from url
                fileName = strings[0].substring(strings[0].lastIndexOf('/')+1);
                //test
                fileName = "testname";
                fileName = fileName + "_"+".mp4";
                //create folder name
                folderName = Environment.getExternalStorageDirectory()+ File.separator + "Kfilm" + File.separator;

                //create folder Kfilm if not exist
                File folderDir = new File(folderName);
                if(!folderDir.exists()){
                    folderDir.mkdir();
                }

                //output stream  to write data to device
                OutputStream outToWrite = new FileOutputStream(folderName + fileName);
                total = 0;
                byte data[] = new byte[1024];

                while((count = inputStream.read(data))!= -1){
                    total += count;
                    outToWrite.write(data,0, count);
                    publishProgress(""+(total*100/lengthOfFile));
                }
                outToWrite.flush();
                outToWrite.close();
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                this.isDownloadSuccessfully = false;
                p_i_downloadMovie.onCompleteDownloadMovie(isDownloadSuccessfully, movie);
            }
            return folderName;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("KingPhung", values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            this.isDownloadSuccessfully = true;
            p_i_downloadMovie.onCompleteDownloadMovie(isDownloadSuccessfully, movie);
        }
    }

}
