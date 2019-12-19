package com.kingphung.kfilm.model.removeMovieFromMyList;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.removeMovieFromMyList.P_I_RemoveMovieFromMyList;

public class M_RemoveMovieFromMyList {
    Context context;
    P_I_RemoveMovieFromMyList p_i_removeMovieFromMyList;

    public M_RemoveMovieFromMyList(Context context, P_I_RemoveMovieFromMyList p_i_removeMovieFromMyList) {
        this.context = context;
        this.p_i_removeMovieFromMyList = p_i_removeMovieFromMyList;
    }

    public void remove(String movieName){
        DatabaseReference myListRef = MyFireBase.getMyListRef();
        myListRef.child(movieName).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        p_i_removeMovieFromMyList.onCompleteRemoveFromMyList(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                p_i_removeMovieFromMyList.onCompleteRemoveFromMyList(false);
            }
        });
    }
}
