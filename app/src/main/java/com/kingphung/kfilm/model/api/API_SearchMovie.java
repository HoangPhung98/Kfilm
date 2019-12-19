package com.kingphung.kfilm.model.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingphung.kfilm.model.Movie;
import com.kingphung.kfilm.view.searchMovie.V_I_SearchMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API_SearchMovie {

    //CONSTANTS
    final private String IP = "http://52.231.163.176:3001";
    final private String API_LINK_MOVIE_SEARCH= IP+"/films/search/?keyword=";

    private Context context;
    private V_I_SearchMovie v_i_searchMovie;

    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovie;

    public API_SearchMovie(Context context, V_I_SearchMovie v_i_searchMovie){
        this.context = context;
        this.v_i_searchMovie = v_i_searchMovie;
        listMovie = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
    }

    public void Search(String searchWord){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    API_LINK_MOVIE_SEARCH + searchWord,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for(int i=0; i<response.length(); i++){
                                    JSONObject jsonObject =(JSONObject) response.get(i);
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
                                }
                                v_i_searchMovie.onCompleteSearchMovie(listMovie);
                            }catch (JSONException e){
                                Log.d("KingPhung","JSONerror: "+e.getMessage());
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
    }
}
