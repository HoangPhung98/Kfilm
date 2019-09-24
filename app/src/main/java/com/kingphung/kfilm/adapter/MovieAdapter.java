package com.kingphung.kfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.activity.MoviePlayActivity;
import com.kingphung.kfilm.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
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
                Intent intent = new Intent(context, MoviePlayActivity.class);
                intent.putExtra("MOVIE", listMovie.get(position));
                //intent.putExtra("LINK_GGDRIVE", API.GetLinkGGDrive(listMovie.get(position).getId_drive()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //map a movie picture to the item of a sub/listMovie recycler view
        Picasso.get().load(listMovie.get(position).getImg_url()).into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
     private ImageView imgMovie;
        public ViewHolder(@NonNull View itemView) {

            //init the UI for a item for sub/listMovie recycler view
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgMovie);
        }
    }
}
