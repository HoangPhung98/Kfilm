package com.kingphung.kfilm.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.view.fragment.MoreFragment;
import com.kingphung.kfilm.view.fragment.MovieDetail;
import com.kingphung.kfilm.view.removeMovieFromMyList.V_I_RemoveMovieFromMyList;
import com.kingphung.kfilm.view.showMovieDetail.V_ShowMovieDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
        implements V_I_RemoveMovieFromMyList {
    ArrayList<Movie> listMovie;
    Context context;

    public MovieAdapter(ArrayList listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout to the its view holder
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //set onClickListener for a item of sub/listMovie recycler view
        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hoaang
//                    showMovieDetail(listMovie.get(position), context);
                //hminh
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                MovieDetail movieDetail = new MovieDetail();
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", listMovie.get(position));
                movieDetail.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, movieDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        //map a movie picture to the item of a sub/listMovie recycler view
        Picasso.get().load(listMovie.get(position).getImg_url()).into(holder.imgMovie);
    }

    private void showMovieDetail(Movie movie, Context context) {
        V_ShowMovieDetail v_showMovieDetail1 = new V_ShowMovieDetail(movie, context);
        v_showMovieDetail1.showDetail();
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public void onCompleteRemoveMovieFromMyList(boolean isSuccessfullyRemove) {
        if (isSuccessfullyRemove) notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMovie;

        public ViewHolder(@NonNull View itemView) {

            //init the UI for a item for sub/listMovie recycler view
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgMovie);
        }
    }
}
