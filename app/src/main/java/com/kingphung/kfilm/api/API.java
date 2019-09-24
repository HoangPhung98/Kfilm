package com.kingphung.kfilm.api;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingphung.kfilm.adapter.CategoryAdapter;
import com.kingphung.kfilm.model.Category;
import com.kingphung.kfilm.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API {
    //CONSTANTS
    final private String IP = "http://52.231.163.176:3001";
    final private String API_LINK_MOVIE_TYPE= IP+"/system/variable";
    final static private String LINK_GG_DRIVE = "https://www.googleapis.com/drive/v3/files/";
    final static private String KEY = "?alt=media&key=AIzaSyBbgWECKniqq5g9qdrqz1KOtnn0Zhu8tKs";
    //CONSTANTS>>>

    //Global vars
    private RequestQueue requestQueue;
    private ArrayList<Category> listCategory;
    private RecyclerView recyclerView;
    private Context context;
    //Global vars>>>

    public API(ArrayList<Category> listCategory, RecyclerView recyclerView, Context context) {
        this.requestQueue =  Volley.newRequestQueue(context);
        this.listCategory = listCategory;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public void LoadListCategory(final ArrayList<Category> listCategory){
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
                                listCategory.add(new Category(jsonObject.getString("name"),new ArrayList<Movie>()));
                            }

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
                                        "",
                                        ""
                                );

                                //load ggdrive link and subtitle link of the movie
                                Load_Link_GGDrive_and_Subtitle(movie, getLink_toGetDriveID_and_SubID(movie.getId()));
                                listMovie.add(movie);
                            }

                            //after loading a list of movie by type, create a Category adapter to display list movie
                            //why i place 2 these code lines here?
                            //because if i place it in the MainActivity(look back to MainActivity file to see)
                            CategoryAdapter adapter = new CategoryAdapter( listCategory, context);
                            recyclerView.setAdapter(adapter);
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
    protected void Load_Link_GGDrive_and_Subtitle(final  Movie movie, final String url){
        //load link to get video and sub and set to movie object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("video");

                            //**************Get 1 drive ID and sub link
                            JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
                            movie.setLink_drive(getLinkDriveOrSub(jsonObject.getString("driveId")));
                            movie.setLink_subtitle(getLinkDriveOrSub(response.getString("sub")));
                            //>>>
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        setRequestTimeout(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest);
    }

    private String getLinkDriveOrSub(String id_drive_or_sub){
        //return link ggdrive that store video and subtitle
        return LINK_GG_DRIVE + id_drive_or_sub + KEY;
    }
    public String getLink_toGetDriveID_and_SubID(String ID){
        return IP+"/films/link/"+ID;
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

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }
    public ArrayList<Category> getListCategory() {
        return listCategory;
    }
    public void setListCategory(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
    }
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
}
