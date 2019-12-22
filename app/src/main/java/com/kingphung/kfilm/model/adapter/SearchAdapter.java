package com.kingphung.kfilm.model.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.ultils.Constant;
import com.kingphung.kfilm.view.fragment.MovieDetail;
import com.kingphung.kfilm.view.showMovieDetail.V_ShowMovieDetail;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
        implements Filterable {

    Context context;
    ArrayList<Movie> listAllMovie;
    ArrayList<Movie> listAllMovie_preserve;

    public SearchAdapter(Context context, ArrayList<Movie> listAllMovie){
        this.context = context;
        this.listAllMovie = listAllMovie;
        listAllMovie_preserve = new ArrayList<>(listAllMovie);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_downloaded_movie, parent, false);
        return new SearchAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMovieName.setText(listAllMovie.get(position).getName());
        holder.tvMovieIMDB.setText(Constant.IMDB + listAllMovie.get(position).getIMDB());
        holder.tvMovieDirector.setText(Constant.DIRECTOR + listAllMovie.get(position).getDirector());
        holder.tvMovieProductionYear.setText(Constant.YEAR + listAllMovie.get(position).getProduct_year());
        holder.tvMovieDescription.setText(Constant.DESCRIPTION + listAllMovie.get(position).getDescription());

        Picasso.get().load(listAllMovie.get(position).getImg_url()).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //play this from local
//                showMovieDetail(listAllMovie.get(position), context);
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                MovieDetail movieDetail = new MovieDetail();
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", listAllMovie.get(position));
                movieDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, movieDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void showMovieDetail(Movie movie, Context context) {
        V_ShowMovieDetail v_showMovieDetail1 = new V_ShowMovieDetail(movie, context);
        v_showMovieDetail1.showDetail();
    }

    @Override
    public int getItemCount() {
        return listAllMovie.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Movie> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(listAllMovie_preserve);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Movie movie: listAllMovie_preserve){
                    if(movie.getName().toLowerCase().contains(filterPattern)
                        || movie.getDirector().toLowerCase().contains(filterPattern)
                        || movie.getIMDB().toLowerCase().contains(filterPattern)){
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listAllMovie.clear();
            listAllMovie.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

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
