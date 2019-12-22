package com.kingphung.kfilm.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kingphung.kfilm.model.Category;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.CategoryAdapter;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.model.firebase.MyFireBase;
import com.kingphung.kfilm.presenter.readDownloadedMoviesFromSQLite.P_ReadSQLite;
import com.kingphung.kfilm.presenter.showListCategory.P_ShowListMovie;
import com.kingphung.kfilm.view.fragment.DownloadFragment;
import com.kingphung.kfilm.view.fragment.MoreFragment;
import com.kingphung.kfilm.view.fragment.SearchFragment;
import com.kingphung.kfilm.view.readSQLite.V_I_ReadSQLite;
import com.kingphung.kfilm.view.showListCategory.V_ShowListMovie;
import com.kingphung.kfilm.view.showMovieDetail.V_ShowMovieDetail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.OnFragmentInteractionListener,
                    MoreFragment.OnFragmentInteractionListener,
                    DownloadFragment.OnFragmentInteractionListener,
                    V_ShowListMovie,
                    V_I_ReadSQLite {

    //Global variables
    public static ArrayList<Movie> listMyMovie;
    public static ArrayList<Movie> listDownloadedMovie;
    RecyclerView recycler_listCategory;
    CategoryAdapter categoryAdapter;
    BottomNavigationView bottomNavigationView;
    P_ShowListMovie p_showListMovie;
    P_ReadSQLite p_readSQLite;

    private boolean isAdapterSeted = false;
    //Global variables>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listAllMovie is used for search fragment
        listMyMovie = new ArrayList<>();

        initUI();

        //read my list movie
        if(MyFireBase.checkLoggedIn()){
            listMyMovie = MyFireBase.getMyListMovie();
        }

        //read listDownloaded movie
        p_readSQLite = new P_ReadSQLite(MainActivity.this, this);
        p_readSQLite.read();

        p_showListMovie = new P_ShowListMovie(this, MainActivity.this, categoryAdapter);
        p_showListMovie.showListMovie();
    }
    private void initUI(){
        //<create recycler view to show list movie categories
        recycler_listCategory = findViewById(R.id.recycler_main);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_listCategory.setHasFixedSize(true);
        recycler_listCategory.setLayoutManager(linearLayoutManager);
        //<create recycler view to show list movie categories>>>


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //<Handler bottom navigation bar click, if an icon is clicked, show the corresponding fragment
        //fragment is almost like activity, except it's a smaller and simpler version
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        clearAllFragmentFromBackStack();
                        return true;
                    case R.id.nav_search:
                        loadFragment(new SearchFragment());
                        return true;
                    case R.id.nav_download:
                        loadFragment(new DownloadFragment());
                        return true;
                    case R.id.nav_more:
                        loadFragment(new MoreFragment());
                        return true;

                }
                return false;
            }
        });
        //<Handler bottom navigation bar click>>>>

    }
    private void loadFragment(Fragment fragment){
        //move to another fragment from recent fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(int i=0; i<fragmentList.size(); i++) Log.d("KingPhung", fragmentList.get(i).toString());
        if(fragmentList.size()>0){
            switch (fragmentList.get(fragmentList.size()-1).getClass().toString()){
                case "class com.kingphung.kfilm.view.fragment."+"SearchFragment":
                    bottomNavigationView.setSelectedItemId(R.id.nav_search);
                    break;
                case "class com.kingphung.kfilm.view.fragment."+"DownloadFragment":
                    bottomNavigationView.setSelectedItemId(R.id.nav_download);
                    break;
                case "class com.kingphung.kfilm.view.fragment."+"MoreFragment":
                    bottomNavigationView.setSelectedItemId(R.id.nav_more);
                    break;
            }
        }else{
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }


    }

    private void clearAllFragmentFromBackStack(){
        //move back to main activity / home by removing all other fragment in stack
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //don't know how this works, docs say it's use to communicate between activity and fragment
    }

    @Override
    public void showList(CategoryAdapter categoryAdapter) {
        if(!isAdapterSeted) {
            isAdapterSeted = true;
            this.categoryAdapter = categoryAdapter;
            recycler_listCategory.setAdapter(categoryAdapter);
            this.categoryAdapter.notifyDataSetChanged();
        }
        else {
            this.categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("KingPhung","result in activity");
    }

    @Override
    public void onCompleteReadSQLite(ArrayList<Movie> listDownloadedMovie) {
        this.listDownloadedMovie = listDownloadedMovie;
    }
}
