package com.kingphung.kfilm.model;


import java.util.ArrayList;

public class Category {
    private String type;
    private ArrayList<Movie> listMovie;

    public Category(String type, ArrayList<Movie> listMovie) {
        this.type = type;
        this.listMovie = listMovie;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<Movie> getlistMovie() {
        return listMovie;
    }
    public void setlistMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }
}
