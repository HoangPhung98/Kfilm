package com.kingphung.kfilm.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.Category;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.SearchAdapter;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link SearchFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link SearchFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SearchFragment extends Fragment {
    Context context;
    ArrayList<Movie> listAllMovie;

    //UI
    RecyclerView recycler_listSearch;
    SearchAdapter searchAdapter;
    EditText etSearch;

    private OnFragmentInteractionListener mListener;

    public SearchFragment(ArrayList<Movie> listAllMovie) {
        this.listAllMovie = listAllMovie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recycler_listSearch = view.findViewById(R.id.recycler_listSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_listSearch.setLayoutManager(linearLayoutManager);
        recycler_listSearch.setHasFixedSize(true);

        searchAdapter = new SearchAdapter(context, listAllMovie);
        recycler_listSearch.setAdapter(searchAdapter);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
