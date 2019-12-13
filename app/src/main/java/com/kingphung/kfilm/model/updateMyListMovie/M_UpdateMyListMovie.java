package com.kingphung.kfilm.model.updateMyListMovie;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.updateMyListMovie.P_I_UpdateMyListMovie;

import java.util.ArrayList;

public class M_UpdateMyListMovie {
    P_I_UpdateMyListMovie p_i_updateMyListMovie;
    public M_UpdateMyListMovie(P_I_UpdateMyListMovie p_i_updateMyListMovie){
        this.p_i_updateMyListMovie = p_i_updateMyListMovie;
    }

    public void update(){
        final ArrayList<Movie> myListMovie = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("KingPhung","onDatachange Listener");
                myListMovie.clear();
                for(DataSnapshot i : dataSnapshot.getChildren()){
                    Movie movie = i.getValue(Movie.class);
                    myListMovie.add(movie);
                }
                p_i_updateMyListMovie.onCompleteUpdateMyListMovie(myListMovie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        MyFireBase.getMyListRef().addValueEventListener(valueEventListener);
    }
}
