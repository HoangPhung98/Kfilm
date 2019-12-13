package com.kingphung.kfilm.model.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.ultils.Constant;

import java.util.ArrayList;

public class MyFireBase {
    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public static boolean checkLoggedIn(){
        return FirebaseAuth.getInstance().getCurrentUser()!=null;
    }

    public static DatabaseReference getUserRef(){
        String userEmail = getCurrentUser().getEmail();
        userEmail = userEmail.replaceAll("[@.]","");
        return FirebaseDatabase.getInstance().getReference(userEmail);
    }

    public static DatabaseReference getMyListRef(){
        return getUserRef().child(Constant.myListRef);
    }

    public static ArrayList<Movie> getMyListMovie(){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        getMyListRef().addValueEventListener(valueEventListener);
        return myListMovie;
    }
}
