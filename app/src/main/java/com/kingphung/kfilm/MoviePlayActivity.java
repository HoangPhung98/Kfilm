package com.kingphung.kfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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

public class MoviePlayActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private String link_GGDRIVE;
    private String link_Subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);

        setFullScreen();

        //get link gg drive from main activity when a movie is click
        Movie movie = (Movie)getIntent().getSerializableExtra("MOVIE");
        link_GGDRIVE = movie.getLink_drive();
        link_Subtitle = "https://www.googleapis.com/drive/v3/files/1NdELzcTjW3fbAgTcSNBZW8uZ1aAeI8zh?alt=media&key=AIzaSyBbgWECKniqq5g9qdrqz1KOtnn0Zhu8tKs";
        //Init movie player using "exoplayer" library
        initExo();
    }

    private void initExo() {
        playerView = findViewById(R.id.exo_moviePlay);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "kfilm"));

        MediaSource[] mediaSourcesArray = new MediaSource[2];
        //video source
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(Uri.parse(link_GGDRIVE));
        mediaSourcesArray[0] = mediaSource;

        //subtitle source
         SingleSampleMediaSource subtitleSource =
                new SingleSampleMediaSource(
                        Uri.parse(link_Subtitle),
                        dataSourceFactory,
                        Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "vi", null),
                        C.TIME_UNSET);
        mediaSourcesArray[1] = subtitleSource;

        //merge video and subtitle
        MediaSource mediaSourceMerged = new MergingMediaSource(mediaSourcesArray);

        simpleExoPlayer.prepare(mediaSourceMerged);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void setFullScreen() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
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
