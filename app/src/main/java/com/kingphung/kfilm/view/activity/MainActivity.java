package com.kingphung.kfilm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kingphung.kfilm.model.adapter.CategoryAdapter;
import com.kingphung.kfilm.R;
import com.kingphung.kfilm.presenter.showListCategory.P_ShowListMovie;
import com.kingphung.kfilm.view.fragment.DownloadFragment;
import com.kingphung.kfilm.view.fragment.MoreFragment;
import com.kingphung.kfilm.view.fragment.SearchFragment;
import com.kingphung.kfilm.view.showListCategory.V_ShowListMovie;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.OnFragmentInteractionListener,
                    MoreFragment.OnFragmentInteractionListener,
                    DownloadFragment.OnFragmentInteractionListener,
                    V_ShowListMovie {

    //Global variables
    RecyclerView recycler_listCategory;
    BottomNavigationView bottomNavigationView;
    P_ShowListMovie p_showListMovie;
    //Global variables>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        p_showListMovie = new P_ShowListMovie(this, getApplicationContext());
        p_showListMovie.showListMovie();


    }
    private void initUI(){
        //<create recycler view to show list movie categories
        recycler_listCategory = findViewById(R.id.recycler_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
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
        recycler_listCategory.setAdapter(categoryAdapter);
    }
}
