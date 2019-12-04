package com.kingphung.kfilm.view.showMovieDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.downloadMovie.P_CheckWriteExternalPermission;
import com.kingphung.kfilm.presenter.downloadMovie.P_DownloadMovie;
import com.kingphung.kfilm.presenter.playMovie.P_LoadLinkMovie;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.activity.MoviePlayActivity;
import com.kingphung.kfilm.view.downloadMovie.V_I_CheckWriteExternalPermission;
import com.kingphung.kfilm.view.downloadMovie.V_I_DownloadMovie;
import com.kingphung.kfilm.view.playMovie.V_I_LoadLinkMovie;
import com.squareup.picasso.Picasso;

public class V_ShowMovieDetail
        implements View.OnClickListener,
        V_I_LoadLinkMovie,
        V_I_DownloadMovie,
        V_I_CheckWriteExternalPermission {

    Movie movie;
    Context context;

    //UI
    PopupWindow popupWindow;
    ImageView ivMoviePoster;
    TextView tvMovieName, tvMovieIMDB, tvMovieProductionYear, tvMovieDirector, tvMovieDescription;
    ImageButton btExit, btDownload, btPlay;
    public V_ShowMovieDetail(Movie movie, Context context){
        this.movie = movie;
        this.context = context;
    }

    public void showDetail(){
        //init UI of popup window to show movie detail
        initUI();
        mapDataToUI();
        setOnClick();
    }



    private void initUI(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewLayoutPopupWindowMovieDetail = inflater.inflate(R.layout.popupwindow_movie_detail, null);
        popupWindow = new PopupWindow(
                viewLayoutPopupWindowMovieDetail,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        ivMoviePoster = viewLayoutPopupWindowMovieDetail.findViewById(R.id.ivMoviePoster);
        tvMovieName = viewLayoutPopupWindowMovieDetail.findViewById(R.id.tvMovieName);
        tvMovieIMDB = viewLayoutPopupWindowMovieDetail.findViewById(R.id.tvIMDB);
        tvMovieDirector = viewLayoutPopupWindowMovieDetail.findViewById(R.id.tvMovieDirector);
        tvMovieProductionYear = viewLayoutPopupWindowMovieDetail.findViewById(R.id.tvProductionYear);
        tvMovieDescription = viewLayoutPopupWindowMovieDetail.findViewById(R.id.tvMovieDescription);

        btExit = viewLayoutPopupWindowMovieDetail.findViewById(R.id.btCancel);
        btDownload = viewLayoutPopupWindowMovieDetail.findViewById(R.id.btDownload);
        btPlay = viewLayoutPopupWindowMovieDetail.findViewById(R.id.btPlay);

        //show popup window
        popupWindow.showAtLocation(viewLayoutPopupWindowMovieDetail, Gravity.CENTER,0,0);

        //set dim behind when popup window show up
        View container = popupWindow.getContentView().getRootView();
        if(container != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }

    }
    private void mapDataToUI() {
        Picasso.get().load(movie.getImg_url()).into(ivMoviePoster);
        tvMovieName.setText(movie.getName());
        tvMovieIMDB.setText(Constant.IMDB + movie.getIMDB());
        tvMovieDirector.setText(Constant.DIRECTOR + movie.getDirector());
        tvMovieProductionYear.setText(Constant.YEAR + movie.getProduct_year());
        tvMovieDescription.setText(Constant.DESCRIPTION + movie.getDescription());
    }
    private void setOnClick() {
        btExit.setOnClickListener(this);
        btDownload.setOnClickListener(this);
        btPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCancel:
                handle_ExitPopupWindow();
                break;
            case R.id.btDownload:
                handle_DownloadMovie();
                break;
            case R.id.btPlay:
                handle_PlayMovie();
                break;
        }
    }

    private void handle_PlayMovie() {
        Toast.makeText(context, "Play Movie", Toast.LENGTH_LONG).show();
        P_LoadLinkMovie loadLinkMovie = new P_LoadLinkMovie(movie.getId(), context, getImplLoadLinkMovie());
        loadLinkMovie.load();
    }
    private V_I_LoadLinkMovie getImplLoadLinkMovie(){
        return this;
    }
    private void handle_DownloadMovie() {
        P_CheckWriteExternalPermission p_checkWriteExternalPermission =
                new P_CheckWriteExternalPermission(context, this);
        p_checkWriteExternalPermission.check();
    }

    @Override
    public void onCompleteCheckExternalPermission(boolean isPermissionGranted) {
        if(isPermissionGranted){
            Toast.makeText(context,"Permission granted, Download Movie", Toast.LENGTH_LONG).show();
            P_DownloadMovie p_downloadMovie = new P_DownloadMovie(movie, this, context);
            p_downloadMovie.startDownload();
        }else{
            Toast.makeText(context,"Please grant me permission to download video!", Toast.LENGTH_LONG).show();
        }

    }

    private void handle_ExitPopupWindow() {
        popupWindow.dismiss();
    }

    @Override
    public void onCompleteLoadLink(String url_video, String url_sub) {
        //play movie in new intent
        Log.v("PLAY***", url_video);
        Intent intent = new Intent(context, MoviePlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL_VIDEO",url_video);
        bundle.putString("URL_SUB",url_sub);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onCompleteDownload(boolean isDownloadSuccessfully, Movie movie) {
        if(isDownloadSuccessfully){
            Toast.makeText(context, "Download successfully!" + movie.getName(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Download failed!", Toast.LENGTH_LONG).show();
        }
    }


}
