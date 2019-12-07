package com.kingphung.kfilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.ultils.Constant;

import java.io.File;

public class MoviePlayActivity extends AppCompatActivity {
    //Global vars
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

    private boolean isPlayOnline;

    //link for playing online
    private String link_GGDRIVE;
    private String link_Subtitle;

    //uri for playing offline
    private String uri_movieFolder;
    private String uri_movieMP4;
    private String uri_movieSubtitle;

    //Global vars>>>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setFullScreen();
            }
        });
        setFullScreen();

        isPlayOnline = getIntent().getExtras().getBoolean("IS_PLAY_ONLINE");

        if(isPlayOnline == Constant.ONLINE){

            link_GGDRIVE = getIntent().getExtras().getString("URL_VIDEO");
            link_Subtitle = getIntent().getExtras().getString("URL_SUB");
            initExo(link_GGDRIVE, link_Subtitle);

        }else{

            uri_movieFolder = getIntent().getExtras().getString("URI_MOVIE_FOLDER");
            Log.d("KingPhung","--------------" + uri_movieFolder);
            String folderName = uri_movieFolder.substring(uri_movieFolder.lastIndexOf("/")+1);
            uri_movieMP4 =
                    "file://" +
                    uri_movieFolder +
                    File.separator +
                    folderName + Constant.MP4_EXTENSION;
            uri_movieSubtitle =
                    "file://" +
                    uri_movieFolder +
                    File.separator +
                    folderName + Constant.SRT_EXTENSION;

            initExo(uri_movieMP4, uri_movieSubtitle);
        }


        //Init movie player using "exoplayer" library
    }

    private void initExo(String linkVideo, String linkSub) {

        Log.d("KingPhung", "====="+linkVideo);
        Log.d("KingPhung", "====="+linkSub);


        playerView = findViewById(R.id.exo_moviePlay);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "kfilm"));

        //create an array of media source to contain videoSource and subtitleSource
        MediaSource[] mediaSourcesArray = new MediaSource[2];

        //extract video source and set to array[0]
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(Uri.parse(linkVideo));
        mediaSourcesArray[0] = videoSource;

        //extract subtitle source and set to array[1]
         SingleSampleMediaSource subtitleSource =
                new SingleSampleMediaSource(
                        Uri.parse(linkSub),
                        dataSourceFactory,
                        Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "vi", null),
                        C.TIME_UNSET);
        mediaSourcesArray[1] = subtitleSource;

        //merge videoSource and subtitleSource
        MediaSource mediaSourceMerged = new MergingMediaSource(mediaSourcesArray);

        //set merged source to exoPlayer and make it ready
        simpleExoPlayer.prepare(mediaSourceMerged);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
