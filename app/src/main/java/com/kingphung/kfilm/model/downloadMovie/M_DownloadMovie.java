package com.kingphung.kfilm.model.downloadMovie;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.DownloadedMovieAdapter;
import com.kingphung.kfilm.presenter.downloadMovie.P_I_DownloadMovie;
import com.kingphung.kfilm.ultils.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class M_DownloadMovie {
    //for debug
    String url_test = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
    Movie movie;
    String url_video;
    String url_sub;
    P_I_DownloadMovie p_i_downloadMovie;
    boolean flag_isTooLarge = false;
    public M_DownloadMovie(Movie movie, String url_video, String url_sub, P_I_DownloadMovie p_i_downloadMovie){
        this.movie = movie;
        this.url_video = url_video;
        this.url_sub = url_sub;
        this.p_i_downloadMovie = p_i_downloadMovie;
    }

    public void startDownload(){
        new DownloadMovieAsyncTask(Constant.MP4_EXTENSION).execute(url_test);
        new DownloadMovieAsyncTask(Constant.SRT_EXTENSION).execute(url_sub);
        new DownloadMovieAsyncTask(Constant.JPEG_EXTENSION).execute(movie.getImg_url());
    }

    private class DownloadMovieAsyncTask extends AsyncTask<String, String, String>{

        private String fileName;
        private String folderNameKfilm, folderNameMovie;
        private boolean isDownloadSuccessfully;
        private String typeOfExtension;


        public DownloadMovieAsyncTask(String typeOfExtension){
            this.typeOfExtension = typeOfExtension;
        }

        @Override
        protected String doInBackground(String... strings) {
            int count;
            long total;
            float lengthOfFile = 0;

            try {
                //set up url
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                //get length of mp file
                lengthOfFile = connection.getContentLength();
                File external = Environment.getExternalStorageDirectory();

                if(!flag_isTooLarge && lengthOfFile < external.getFreeSpace()){
                //set up input stream to fetch data from connection
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                //create file name base on movie name
                fileName = movie.getName();
                fileName = concateFileExtension(fileName, typeOfExtension);

                //create folder name
//                folderName = Environment.getExternalStorageDirectory()+
//                            File.separator +
//                            "Kfilm" +
//                            File.separator +
//                            movie.getName() +
//                            File.separator;


                    Log.d("KingPhung:length", lengthOfFile+"");
                    Log.d("KingPhung:lengthF", external.getFreeSpace()+"");

                    //create folder Kfilm if not exist
                    folderNameKfilm = Constant.EXTERNAL_STORAGE_PATH;
                    File folderDirKfilm = new File(folderNameKfilm);
                    if(!folderDirKfilm.exists()){
                        folderDirKfilm.mkdir();
                    }

                    //create folder Kfilm/John Wick/
                    folderNameMovie = folderNameKfilm + movie.getName() +"/";
                    File folderDirMovie = new File(folderNameMovie);
                    if(!folderDirMovie.exists()){
                        folderDirMovie.mkdir();
                    }

                    //output stream  to write data to device
                    OutputStream outToWrite = new FileOutputStream(folderNameMovie + fileName);
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
                }else{
                    flag_isTooLarge = true;
                    this.isDownloadSuccessfully = false;
                    p_i_downloadMovie.onCompleteDownloadMovie(isDownloadSuccessfully, movie, "0");
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.isDownloadSuccessfully = false;
                p_i_downloadMovie.onCompleteDownloadMovie(isDownloadSuccessfully, movie, "0");
            }
            return new DecimalFormat("####.##").format(lengthOfFile/(2<<20))+"";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("KingPhung", values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(typeOfExtension == Constant.MP4_EXTENSION && s!=null){
                this.isDownloadSuccessfully = true;
                movie.setSize(s);
                p_i_downloadMovie.onCompleteDownloadMovie(isDownloadSuccessfully, movie, s);
            }
        }
        private String concateFileExtension(String fileName, String typeOfExtension){
            return fileName + typeOfExtension;
        }
    }

}
