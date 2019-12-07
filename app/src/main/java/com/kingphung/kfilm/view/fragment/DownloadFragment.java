package com.kingphung.kfilm.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.DownloadedMovieAdapter;
import com.kingphung.kfilm.presenter.readDownloadedMoviesFromSQLite.P_ReadSQLite;
import com.kingphung.kfilm.view.readSQLite.V_I_ReadSQLite;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DownloadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DownloadFragment} factory method to
 * create an instance of this fragment.
 */
public class DownloadFragment extends Fragment
        implements V_I_ReadSQLite {

    private OnFragmentInteractionListener mListener;

    private DownloadedMovieAdapter downloadedMovieAdapter;
    private ArrayList<Movie> listDownloadedMovie;
    private Context context;

    RecyclerView recyclerView;

    public DownloadFragment(){}
    public DownloadFragment(ArrayList<Movie> listDownloadedMovie) {
        this.listDownloadedMovie = listDownloadedMovie;
    }

//    public static DownloadFragment newInstance(ArrayList<Movie> listDownloadedMovie, Context context) {
//        DownloadFragment fragment = new DownloadFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        //recycler view
        recyclerView = view.findViewById(R.id.recycler_downloadedMovies);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        P_ReadSQLite p_readSQLite = new P_ReadSQLite(context, this);
        p_readSQLite.read();
        return view;
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
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onCompleteReadSQLite(ArrayList<Movie> listDownloadedMovie) {
        downloadedMovieAdapter = new DownloadedMovieAdapter(context, listDownloadedMovie);
        Log.d("KingPhung",listDownloadedMovie.size()+"");
        recyclerView.setAdapter(downloadedMovieAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
