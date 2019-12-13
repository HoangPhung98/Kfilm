package com.kingphung.kfilm.model.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingphung.kfilm.model.Category;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.model.adapter.CategoryAdapter;
import com.kingphung.kfilm.presenter.showListCategory.P_impShowListMovie;
import com.kingphung.kfilm.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API_LoadListCategory {
    //CONSTANTS
    final private String IP = "http://52.231.163.176:3001";
    final private String API_LINK_MOVIE_TYPE= IP+"/system/variable";
    //CONSTANTS>>>

    //Global vars
    private RequestQueue requestQueue;
    private ArrayList<Category> listCategory;
    private P_impShowListMovie p_impShowListMovie;
    private Context context;
    private CategoryAdapter categoryAdapter;
    //Global vars>>>

    public API_LoadListCategory(P_impShowListMovie p_impShowListMovie, Context context, CategoryAdapter categoryAdapter) {
        listCategory = new ArrayList<>();
        this.context = context;
        this.p_impShowListMovie = p_impShowListMovie;
        this.requestQueue =  Volley.newRequestQueue(context);
        this.categoryAdapter = categoryAdapter;
    }

    public void LoadListCategory(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_LINK_MOVIE_TYPE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("types");

                            //Create list of movie types/categories
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Log.v("JSON",jsonObject.getString("name"));
                                listCategory.add(new Category(jsonObject.getString("name"),new ArrayList<Movie>()));
                            }

                            categoryAdapter = new CategoryAdapter( listCategory, context);
                            p_impShowListMovie.onAPIResponse(categoryAdapter);

                            //For each type/category of movies, load movie belong to that type/category
                            for(int i=0; i<listCategory.size(); i++)
                                LoadListMovieByType(listCategory.get(i).getlistMovie(),
                                        IP+"/films/type/"+listCategory.get(i).getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("JSON", error.toString());
                    }
                });

        //set timeout value greater so that avoid volley timeout error
        setRequestTimeout(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest);
    }
    private void LoadListMovieByType(final ArrayList<Movie> listMovie, final String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            //load all movie belong to current type
                            //load all info of a movie
                            //then create a movie instance
                            //then add it to list movie
                            for(int i=0; i< response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Movie movie = new Movie(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("product_year"),
                                        jsonObject.getString("img_url"),
                                        jsonObject.getString("IMDB"),
                                        jsonObject.getString("statusSub"),
                                        jsonObject.getString("description"),
                                        jsonObject.getString("director"),
                                        null,
                                        "0"
                                );
                                listMovie.add(movie);
                                if(!MainActivity.listAllMovie.contains(movie))MainActivity.listAllMovie.add(movie);
                                Log.d("KingPhung","===========Category update");
                            }
                            p_impShowListMovie.onAPIResponse(categoryAdapter);
                            //after loading a list of movie by type, create a Category adapter to display list movie
                            //why i place 2 these code lines here?
                            //because if i place it in the MainActivity(look back to MainActivity file to see)
//                            CategoryAdapter adapter = new CategoryAdapter( listCategory, context);
//                            p_impShowListMovie.onAPIResponse(adapter);
                        } catch (JSONException e) {
                            Log.v("JSON",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("JSON", error.toString());
                    }

                });
        setRequestTimeout(jsonArrayRequest);
        requestQueue.add(jsonArrayRequest);
    }
    private void setRequestTimeout(Request request){
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}
