package com.kingphung.kfilm.model.updatePositionMyListMovie;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.view.activity.MainActivity;

public class M_UpdatePositionMyListMovie {
    Context context;
    Movie movie;

    public M_UpdatePositionMyListMovie(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
    }

    public void update(){
        final DatabaseReference myListRef = MyFireBase.getMyListRef();
        myListRef.child(movie.getName()).setValue(movie)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        for(int i=0; i<MainActivity.listMyMovie.size(); i++){
//                            if(MainActivity.listMyMovie.get(i).getName().equals(movie.getName())){{
//                                Log.d("KingPhung","possition update"+movie.getCurrentPosition());
//                                MainActivity.listMyMovie.get(i).setCurrentPosition(movie.getCurrentPosition());
//                                break;
//                            }}
//                        }
//                        p_i_addToMyList.onCompleteAddToMyList(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                p_i_addToMyList.onCompleteAddToMyList(false);
            }
        });
    }
}
