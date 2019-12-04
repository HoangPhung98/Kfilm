package com.kingphung.kfilm.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.ultils.Constant;

import java.util.ArrayList;

public class DownloadedMovieAdapter extends RecyclerView.Adapter<DownloadedMovieAdapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMovieName.setText(listDownloadedMovie.get(position).getName());
        holder.tvMovieIMDB.setText(Constant.IMDB + listDownloadedMovie.get(position).getIMDB());
        holder.tvMovieDirector.setText(Constant.DIRECTOR + listDownloadedMovie.get(position).getDirector());
        holder.tvMovieProductionYear.setText(Constant.YEAR + listDownloadedMovie.get(position).getProduct_year());
        holder.tvMovieDescription.setText(Constant.DESCRIPTION + listDownloadedMovie.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return listDownloadedMovie.size();
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
