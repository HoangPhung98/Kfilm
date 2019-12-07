package com.kingphung.kfilm.model.adapter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.presenter.deleteDownloadedMovie.P_DeleteDownloadedMovie;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.deleteDownloadedMovie.V_I_DeleteDownloadedMovie;
import com.kingphung.kfilm.view.playMovieOffline.V_PlayMovieOffline;
import com.kingphung.kfilm.view.showPopupDeleteDownloadedMovie.V_I_ShowPopupDeleteDownloadedMovie;
import com.kingphung.kfilm.view.showPopupDeleteDownloadedMovie.V_ShowPopupDeleteDownloadedMovie;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class DownloadedMovieAdapter extends RecyclerView.Adapter<DownloadedMovieAdapter.ViewHolder>
        implements V_I_ShowPopupDeleteDownloadedMovie,
        V_I_DeleteDownloadedMovie {

    ArrayList<Movie> listDownloadedMovie;
    Context context;

    public DownloadedMovieAdapter(Context context, ArrayList<Movie> listDownloadedMovie){
        this.context = context;
        this.listDownloadedMovie = listDownloadedMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_downloaded_movie, parent, false);
        return new DownloadedMovieAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMovieName.setText(listDownloadedMovie.get(position).getName());
        holder.tvMovieIMDB.setText(Constant.IMDB + listDownloadedMovie.get(position).getIMDB());
        holder.tvMovieDirector.setText(Constant.DIRECTOR + listDownloadedMovie.get(position).getDirector());
        holder.tvMovieProductionYear.setText(Constant.YEAR + listDownloadedMovie.get(position).getProduct_year());
        holder.tvMovieDescription.setText(Constant.DESCRIPTION + listDownloadedMovie.get(position).getDescription());

        final String uri_movieFolder = Environment.getExternalStorageDirectory()+
                File.separator +
                "Kfilm" +
                File.separator +
                listDownloadedMovie.get(position).getName();
        final String uri_moviePoster =
                uri_movieFolder +
                File.separator +
                listDownloadedMovie.get(position).getName() +
                Constant.JPEG_EXTENSION;
        Log.d("KingPhung", uri_moviePoster+"===============");
        Picasso.get().load("file://"+uri_moviePoster).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //play this from local
                playMovie(context, uri_movieFolder);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupDeleteDownloadedMovie(position, listDownloadedMovie.get(position).getName(), uri_moviePoster);
                return true;
            }
        });
    }

    private void showPopupDeleteDownloadedMovie(int position, String movieName, String uri_MoviePoster) {
        V_ShowPopupDeleteDownloadedMovie v_showPopupDeleteDownloadedMovie = new V_ShowPopupDeleteDownloadedMovie(context, this, position, movieName, uri_MoviePoster);
        v_showPopupDeleteDownloadedMovie.show();
    }

    @Override
    public void onCompleteSelected(boolean wantToDelete, int position, String movieName) {
        if(wantToDelete){
            deleteDownloadedMovie(movieName);
        }
    }

    private void deleteDownloadedMovie(String movieName) {
        P_DeleteDownloadedMovie p_deleteDownloadedMovie = new P_DeleteDownloadedMovie(context, this, movieName);
        p_deleteDownloadedMovie.delete();
    }

    @Override
    public void onCompleteDeleteDownloadedMovie(boolean isDeleteSuccessfully, int position) {
        if(isDeleteSuccessfully) {
            Toast.makeText(context, "Delete successfully: ", Toast.LENGTH_LONG).show();
            listDownloadedMovie.remove(position);
            notifyDataSetChanged();
        }
        else{
            Toast.makeText(context, "Delete failed!: ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public int getItemCount() {
        return listDownloadedMovie.size();
    }

    private void playMovie(Context context, String uri_movieFolder){
        V_PlayMovieOffline v_playMovieOffline = new V_PlayMovieOffline(context, uri_movieFolder);
        v_playMovieOffline.play();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMoviePoster;
        TextView tvMovieName, tvMovieIMDB, tvMovieProductionYear, tvMovieDirector, tvMovieDescription, tvSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.ivDownloadedMovie);
            tvMovieName = itemView.findViewById(R.id.tvNameDownloadedMovie);
            tvMovieIMDB = itemView.findViewById(R.id.tvIMDBDownloadedMovie);
            tvMovieDirector = itemView.findViewById(R.id.tvDirectorDownloadedMovie);
            tvMovieProductionYear = itemView.findViewById(R.id.tvProductionYearDownloadedMovie);
            tvMovieDescription = itemView.findViewById(R.id.tvDescriptionDownloadedMovie);
            tvSize = itemView.findViewById(R.id.tvSizeDownloadedMovie);
        }
    }
}
