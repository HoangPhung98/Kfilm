package com.kingphung.kfilm.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.MovieAdapter;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.updateMyListMovie.P_UpdateMyListMovie;
import com.kingphung.kfilm.view.activity.MainActivity;
import com.kingphung.kfilm.view.showPopupLogout.V_I_ShowPopupLogout;
import com.kingphung.kfilm.view.showPopupLogout.V_ShowPopupLogout;
import com.kingphung.kfilm.view.updateMyListMovie.V_I_UpdateMyListMovie;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import static android.app.Activity.RESULT_OK;
import static com.kingphung.kfilm.model.firebase.MyFireBase.getMyListMovie;

public class MoreFragment extends Fragment
    implements V_I_ShowPopupLogout,
        V_I_UpdateMyListMovie {
    static final String tag = "M_FRAGMENT";
    //UI
    CircularImageView ivUser;
    TextView tvUserName, tvEmail;
    Button btMyList, btLogin;
    FloatingActionButton fabLogout;

    RecyclerView recycler_listMyMovie;
    ArrayList<Movie> listMyMovie;
    MovieAdapter movieAdapter;
    Context context;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private boolean isLoggedIn;
    private FirebaseUser user;
    //login
    private final int RC_LOGIN = 25;
    LinearLayout layoutLogin;
    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
        listMyMovie = new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            isLoggedIn = true;
            user = firebaseAuth.getCurrentUser();
            listMyMovie = getMyListMovie();
        }
        Toast.makeText(context, isLoggedIn+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        //login UI
        layoutLogin = view.findViewById(R.id.layoutLogin);
        btLogin = view.findViewById(R.id.btLogin);
        setListenerLogIn();

        //logged-in UI
        ivUser = view.findViewById(R.id.ivUser);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btMyList = view.findViewById(R.id.btMyList);
        fabLogout = view.findViewById(R.id.fab_logout);
        recycler_listMyMovie = view.findViewById(R.id.recycler_myList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_listMyMovie.setLayoutManager(linearLayoutManager);
        recycler_listMyMovie.setHasFixedSize(true);
        setListenerLoggedIn();

        if(isLoggedIn == false) {
            layoutLogin.setVisibility(View.VISIBLE);
        }else{
            updateUIOfLoggedInUser();
        }

        return view;
    }

    private void setListenerLogIn() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KingPhung","Click login");
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.GoogleBuilder().build()
                                        )
                                )
                                .build(), RC_LOGIN

                );
            }
        });
    }
    private void setListenerLoggedIn() {
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                V_ShowPopupLogout v_showPopupLogout = new V_ShowPopupLogout(context, getV_I_ShowPopupLogout(), user.getDisplayName(), user.getPhotoUrl().toString());
                v_showPopupLogout.show();
            }
        });
    }
    private V_I_ShowPopupLogout getV_I_ShowPopupLogout(){return this;}

    @Override
    public void onCompleteSeleted(boolean isWantToLogout) {
        if(isWantToLogout){
            AuthUI.getInstance().signOut(context).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText( context, "Bye", Toast.LENGTH_SHORT).show();
                            Log.d("KingPhung","Logged out");
                            updateLoggedOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("KingPhung=======error: ",e.toString());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("KingPhung","******Resulted");
        if(requestCode == RC_LOGIN && data!= null){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                Log.d("KingPhung", response.getEmail()+"");
                updateLoggedIn();
            }else{
                Log.d("KingPhung", response.getError()+"");
                Toast.makeText(context, "error: " + resultCode+"", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Some error, pls try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIOfLoggedInUser() {
        layoutLogin.setVisibility(View.GONE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Picasso.get().load(user.getPhotoUrl()).into(ivUser);
        tvUserName.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());
        if(movieAdapter == null) {
            movieAdapter = new MovieAdapter(MainActivity.listMyMovie, context);
            recycler_listMyMovie.setAdapter(movieAdapter);
        }
        movieAdapter.notifyDataSetChanged();
    }

    private void updateUIOfLoggedOut(){
        layoutLogin.setVisibility(View.VISIBLE);
    }

    private void updateMyListLoggedOut() {
        MainActivity.listMyMovie.clear();
    }

    private void updateMyListLoggedIn(){

        P_UpdateMyListMovie p_updateMyListMovie = new P_UpdateMyListMovie(this);
        p_updateMyListMovie.update();

    }

    @Override
    public void onCompleteUpdateMyListMovie(ArrayList<Movie> myListMovie) {
        MainActivity.listMyMovie.clear();
        MainActivity.listMyMovie.addAll(myListMovie);
//        movieAdapter = new MovieAdapter(myListMovie, context);
        Log.d("KingPhung","SIZE: "+MainActivity.listMyMovie.size());
        movieAdapter.notifyDataSetChanged();

    }

    private void updateLoggedIn(){
        updateMyListLoggedIn();
        user = FirebaseAuth.getInstance().getCurrentUser();
        isLoggedIn = true;
        updateUIOfLoggedInUser();
    }
    private void updateLoggedOut(){
        updateUIOfLoggedOut();
        updateMyListLoggedOut();
        user = null;
        isLoggedIn = false;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
