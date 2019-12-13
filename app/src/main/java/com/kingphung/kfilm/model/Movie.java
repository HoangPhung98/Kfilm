package com.kingphung.kfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

public class Movie implements Parcelable {
    private String id;
    private String name;
    private String product_year;
    private String img_url;
    private String IMDB;
    private String statusSub;
    private String description;
    private String director;
    private String size;
    private String currentPosition;

    public Movie(){}

    public Movie(String id, String name, String product_year, String img_url, String IMDB, String statusSub, String description, String director, String size, String currentPosition) {
        this.id = id;
        this.name = name;
        this.product_year = product_year;
        this.img_url = img_url;
        this.IMDB = IMDB;
        this.statusSub = statusSub;
        this.description = description;
        this.director = director;
        this.size = size;
        this.currentPosition = currentPosition;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.name.equals(((Movie)obj).getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_year() {
        return product_year;
    }

    public void setProduct_year(String product_year) {
        this.product_year = product_year;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIMDB() {
        return IMDB;
    }

    public void setIMDB(String IMDB) {
        this.IMDB = IMDB;
    }

    public String getStatusSub() {
        return statusSub;
    }

    public void setStatusSub(String statusSub) {
        this.statusSub = statusSub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setSize(String size){
        this.size = size;
    }
    public String getSize(){
        return this.size;
    }

    public void setCurrentPosition(String currentPosition){
        this.currentPosition = currentPosition;
    }
    public String getCurrentPosition(){
        return  this.currentPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.product_year);
        dest.writeString(this.img_url);
        dest.writeString(this.IMDB);
        dest.writeString(this.statusSub);
        dest.writeString(this.description);
        dest.writeString(this.director);
        dest.writeString(this.size);
        dest.writeString(this.currentPosition);
    }
    private Movie(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.product_year = in.readString();
        this.img_url = in.readString();
        this.IMDB = in.readString();
        this.statusSub = in.readString();
        this.description = in.readString();
        this.director = in.readString();
        this.size = in.readString();
        this.currentPosition = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
