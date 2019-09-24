package com.kingphung.kfilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<Category> list_listMovieByType;
    Context context;

    public CategoryAdapter(ArrayList<Category> list_listMovieByType, Context context) {
        this.list_listMovieByType = list_listMovieByType;
        this.context = context;
    }
    public CategoryAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMovieType.setText(list_listMovieByType.get(position).getType());
        holder.recycler_list.setAdapter(new MovieAdapter(list_listMovieByType.get(position).getlistMovie(), context));
    }
    @Override
    public int getItemCount() {
        return list_listMovieByType.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMovieType;
        RecyclerView recycler_list;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieType = itemView.findViewById(R.id.txtType);
            recycler_list = itemView.findViewById(R.id.recycler_list);
            recycler_list.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
