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
import com.kingphung.kfilm.model.api.API_SearchMovie;
import com.kingphung.kfilm.view.searchMovie.V_I_SearchMovie;

import java.util.ArrayList;

public class SearchFragment extends Fragment
    implements V_I_SearchMovie {
    Context context;
    ArrayList<Movie> listMovieSearch;

    //UI
    RecyclerView recycler_listSearch;
    SearchAdapter searchAdapter;
    EditText etSearch;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMovieSearch = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recycler_listSearch = view.findViewById(R.id.recycler_listSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_listSearch.setLayoutManager(linearLayoutManager);
        recycler_listSearch.setHasFixedSize(true);

        searchAdapter = new SearchAdapter(context, listMovieSearch);
        recycler_listSearch.setAdapter(searchAdapter);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                searchAdapter.getFilter().filter(s);
                //Hàm này được gọi mỗi khi gõ text thay đổi edit text trên thanh search.
                if(s!=null){
                    String searchWord = s.toString().toLowerCase().trim();
                    API_SearchMovie api_searchMovie = new API_SearchMovie(context, getV_I_SearchMovie());
                    api_searchMovie.Search(searchWord);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing here
            }
        });
        return view;
    }

    @Override
    public void onCompleteSearchMovie(ArrayList<Movie> listMovie) {
            this.listMovieSearch.clear();
            this.listMovieSearch.addAll(listMovie);
            searchAdapter.notifyDataSetChanged();
    }

    private V_I_SearchMovie getV_I_SearchMovie(){
        return this;
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
