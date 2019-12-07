package com.kingphung.kfilm.presenter.showListCategory;

import android.content.Context;

import com.kingphung.kfilm.model.adapter.CategoryAdapter;
import com.kingphung.kfilm.model.api.API_LoadListCategory;
import com.kingphung.kfilm.view.showListCategory.V_ShowListMovie;

public class P_ShowListMovie implements P_impShowListMovie{
    V_ShowListMovie v_showListMovie;
    Context context;
    CategoryAdapter categoryAdapter;
    public P_ShowListMovie(V_ShowListMovie v_showListMovie, Context context, CategoryAdapter categoryAdapter){
        this.v_showListMovie = v_showListMovie;
        this.context = context;
        this.categoryAdapter = categoryAdapter;
    }
    public void showListMovie(){
        API_LoadListCategory api = new API_LoadListCategory(this, context, categoryAdapter);
        api.LoadListCategory();
    }

    @Override
    public void onAPIResponse(CategoryAdapter categoryAdapter) {
        v_showListMovie.showList(categoryAdapter);
    }
}
