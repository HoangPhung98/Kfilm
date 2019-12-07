package com.kingphung.kfilm.view.showPopupDeleteDownloadedMovie;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.kingphung.kfilm.R;
import com.squareup.picasso.Picasso;

public class V_ShowPopupDeleteDownloadedMovie
    implements View.OnClickListener {
    Context context;
    V_I_ShowPopupDeleteDownloadedMovie v_i_showPopupDeleteDownloadedMovie;
    int position;
    String movieName, uri_moviePoster;

    //UI
    PopupWindow popupWindow;
    ImageView ivMoviePoster;
    TextView tvMovieName;
    ImageButton btExit, btDelete;

    public V_ShowPopupDeleteDownloadedMovie(Context context, V_I_ShowPopupDeleteDownloadedMovie v_i_showPopupDeleteDownloadedMovie, int position, String movieName, String uri_moviePoster){
        this.context = context;
        this.v_i_showPopupDeleteDownloadedMovie = v_i_showPopupDeleteDownloadedMovie;
        this.position = position;
        this.movieName = movieName;
        this.uri_moviePoster = uri_moviePoster;
    }

    public void show(){
        initUI();
        mapDataToUI();
        setOnClick();
    }

    private void initUI() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewLayoutPopupWindowDelete = inflater.inflate(R.layout.popupwindow_delete_downloaded_movie, null);
        popupWindow = new PopupWindow(
                viewLayoutPopupWindowDelete,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        ivMoviePoster = viewLayoutPopupWindowDelete.findViewById(R.id.ivMoviePoster);
        tvMovieName = viewLayoutPopupWindowDelete.findViewById(R.id.tvMovieName);

        btExit = viewLayoutPopupWindowDelete.findViewById(R.id.btCancelDelete);
        btDelete = viewLayoutPopupWindowDelete.findViewById(R.id.btDelete);

        //show popup window
        popupWindow.showAtLocation(viewLayoutPopupWindowDelete, Gravity.CENTER,0,0);

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
        Picasso.get().load("file://"+uri_moviePoster).into(ivMoviePoster);
        tvMovieName.setText(movieName);
    }
    private void setOnClick() {
        btExit.setOnClickListener(this);
        btDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCancelDelete:
                handle_ExitPopupWindow();
                break;
            case R.id.btDelete:
                handle_DeleteMovie();
                break;
        }
    }

    private void handle_DeleteMovie() {
        v_i_showPopupDeleteDownloadedMovie.onCompleteSelected(true, position, movieName);
        Toast.makeText(context, "Chose delete", Toast.LENGTH_SHORT).show();
        popupWindow.dismiss();
    }

    private void handle_ExitPopupWindow() {
        popupWindow.dismiss();
    }
}
