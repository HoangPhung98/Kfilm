package com.kingphung.kfilm.model.addToMyList;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.addToMyList.P_I_AddToMyList;

public class M_AddToMyList {
    Context context;
    P_I_AddToMyList p_i_addToMyList;
    Movie movie;

    public M_AddToMyList(Context context, P_I_AddToMyList p_i_addToMyList, Movie movie){
        this.context = context;
        this.p_i_addToMyList = p_i_addToMyList;
        this.movie = movie;
    }

    public void addToFireBase(){

        DatabaseReference myListRef = MyFireBase.getMyListRef();
        myListRef.child(movie.getName()).setValue(movie)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        p_i_addToMyList.onCompleteAddToMyList(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                p_i_addToMyList.onCompleteAddToMyList(false);
            }
        });
    }
}
