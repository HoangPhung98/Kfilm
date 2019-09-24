package com.kingphung.kfilm;

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
import com.kingphung.kfilm.fragment.DownloadFragment;
import com.kingphung.kfilm.fragment.MoreFragment;
import com.kingphung.kfilm.fragment.SearchFragment;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.OnFragmentInteractionListener,
                    MoreFragment.OnFragmentInteractionListener,
                    DownloadFragment.OnFragmentInteractionListener{
    RecyclerView recycler_main;
    BottomNavigationView bottomNavigationView;
    ArrayList<Category> listCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        //varible
        listCategory = new ArrayList<>();
        //varible>>>


        //create an api instance to load from web api
        API api = new API(listCategory, recycler_main, getApplicationContext());
        api.LoadListCategory(listCategory);
        //create an api instance to load from web api>>>


    }
    private void initUI(){
        //<create recycler view to show list movie categories
        recycler_main = findViewById(R.id.recycler_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_main.setHasFixedSize(true);
        recycler_main.setLayoutManager(linearLayoutManager);
        //<create recycler view to show list movie categories>>>


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //<Handler bottom navigation bar click
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
        //don't know how this works
    }
}
