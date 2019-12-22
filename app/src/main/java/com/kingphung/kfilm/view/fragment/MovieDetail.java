package com.kingphung.kfilm.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.addToMyList.P_AddToMyList;
import com.kingphung.kfilm.presenter.downloadMovie.P_CheckWriteExternalPermission;
import com.kingphung.kfilm.presenter.downloadMovie.P_DownloadMovie;
import com.kingphung.kfilm.presenter.playMovie.P_LoadLinkMovie;
import com.kingphung.kfilm.presenter.removeMovieFromMyList.P_RemoveMovieFromMyList;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.activity.MainActivity;
import com.kingphung.kfilm.view.activity.MoviePlayActivity;
import com.kingphung.kfilm.view.addToMyList.V_I_AddToMyList;
import com.kingphung.kfilm.view.downloadMovie.V_I_CheckWriteExternalPermission;
import com.kingphung.kfilm.view.downloadMovie.V_I_DownloadMovie;
import com.kingphung.kfilm.view.playMovieOnline.V_I_LoadLinkMovie;
import com.kingphung.kfilm.view.removeMovieFromMyList.V_I_RemoveMovieFromMyList;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetail extends Fragment implements View.OnClickListener, V_I_LoadLinkMovie, V_I_CheckWriteExternalPermission, V_I_DownloadMovie, V_I_AddToMyList, V_I_RemoveMovieFromMyList {
    Context context;
    private static final String MOVIE = "movie";
    private ImageView banner;
    private Button playBtn;
    private Button downloadBtn;
    private Button addtolistBtn;
    private ImageButton backBtn;

    TextView title, imdb, year, director, description;

    private static Movie movie;

    boolean isThisMovieInMyList;
    boolean isThisMovieDownloaded;


    public MovieDetail() {


    }

    public static MovieDetail newInstance(Movie movie) {
        MovieDetail fragment = new MovieDetail();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            movie = (Movie) getArguments().get(MOVIE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        // Gán 3 nút
        setdata(view);
        setupButton();
        return view;

    }


    private void setdata(View view) {
        isThisMovieDownloaded = MainActivity.listDownloadedMovie.contains(movie);
        isThisMovieInMyList = MainActivity.listMyMovie.contains(movie);

        title = view.findViewById(R.id.movieTitle);
        imdb = view.findViewById(R.id.imdbMovie);
        year = view.findViewById(R.id.movieYear);
        director = view.findViewById(R.id.movieDirector);
        description = view.findViewById(R.id.movieDecription);
        playBtn = view.findViewById(R.id.playBtn);
        downloadBtn = view.findViewById(R.id.downloadBtn);
        addtolistBtn = view.findViewById(R.id.addtolistBtn);
        backBtn = view.findViewById(R.id.backBtn);
        banner = view.findViewById(R.id.background_banner);

        title.setText(movie.getName());
        year.setText(movie.getProduct_year());
        imdb.setText(movie.getIMDB());
        director.setText(movie.getDirector());
        description.setText(movie.getDescription());
        String urlToLargeImg = changeurl(movie.getImg_url());
        Picasso.get().load(urlToLargeImg).into(banner);

        if (isThisMovieDownloaded) {
            downloadBtn.setText("Downloaded");
            downloadBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_downloaded, 0, 0, 0);
        }

        if (isThisMovieInMyList) {
            downloadBtn.setText("Remove");
            downloadBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_added, 0, 0, 0);

        }
    }

    private void setupButton() {
        backBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        addtolistBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int button = v.getId();
        switch (button) {
            case R.id.playBtn:
                handle_playMovie();
                break;
            case R.id.downloadBtn:
                handle_downloadMovie();
                break;
            case R.id.addtolistBtn:
                handle_addToList();
                break;
            case R.id.backBtn:
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }

    private void handle_playMovie() {
        Toast.makeText(context, "Play Movie", Toast.LENGTH_LONG).show();
        P_LoadLinkMovie loadLinkMovie = new P_LoadLinkMovie(movie.getId(), context, getImpLoadLinkMovie());
        loadLinkMovie.load();
    }

    private V_I_LoadLinkMovie getImpLoadLinkMovie() {
        return this;
    }

    @Override
    public void onCompleteLoadLink(String url_video, String url_sub) {
        Intent intent = new Intent(context, MoviePlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("IS_PLAY_ONLINE", true);
        bundle.putParcelable("MOVIE", movie);
        bundle.putString("URL_VIDEO", url_video);
        bundle.putString("URL_SUB", url_sub);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    private void handle_downloadMovie() {
        if (isThisMovieDownloaded) {
            Toast.makeText(context, "Movie downloaded!", Toast.LENGTH_SHORT).show();
        } else {
            P_CheckWriteExternalPermission checkPermission = new P_CheckWriteExternalPermission(context, this);
            checkPermission.check();
        }
    }

    @Override
    public void onCompleteCheckExternalPermission(boolean isPermissionGranted) {
        if (isPermissionGranted) {
            P_DownloadMovie downloader = new P_DownloadMovie(movie, this, context);
            downloader.startDownload();
        } else {
            Toast.makeText(context, "Need permission to download", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onCompleteDownload(boolean isDownloadSuccessfully, Movie movie) {
        if (isDownloadSuccessfully) {
            MainActivity.listDownloadedMovie.add(movie);
            downloadBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_downloaded, 0, 0, 0);
            downloadBtn.setText("Downloaded");
        }
    }

    private void handle_addToList() {
        if (MyFireBase.checkLoggedIn()) {
            if (!isThisMovieInMyList) {
                P_AddToMyList addToMyListP = new P_AddToMyList(context, this, movie);
                addToMyListP.add();
            } else {
                P_RemoveMovieFromMyList removeMovieFromMyListP = new P_RemoveMovieFromMyList(context, this);
                removeMovieFromMyListP.remove(movie.getName());
            }
        } else {
            Toast.makeText(context, "Please login!", Toast.LENGTH_SHORT).show();
        }
    }

    private String changeurl(String url) {
        if (url.indexOf("/medium-cover.jpg") > 0) {
            String result;
            StringBuilder sb = new StringBuilder();
            sb.append(url.substring(0, url.indexOf("/medium-cover.jpg")));
            sb.append("/large-cover.jpg");
            result = sb.toString();
            return result;
        } else return url;
    }


    @Override
    public void onCompleteAddToMyList(boolean isSuccessfullyAddToMyList) {
        if (isSuccessfullyAddToMyList) {
            Toast.makeText(context, "Movie added!", Toast.LENGTH_SHORT).show();
            downloadBtn.setText("Remove");
            downloadBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_added, 0, 0, 0);
        }
    }

    @Override
    public void onCompleteRemoveMovieFromMyList(boolean isSuccessfullyRemove) {
        if (isSuccessfullyRemove) {
            Toast.makeText(context, "Movie added!", Toast.LENGTH_SHORT).show();
            downloadBtn.setText("Remove");
            downloadBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_added, 0, 0, 0);
        }
    }
}
