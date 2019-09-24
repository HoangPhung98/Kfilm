package com.kingphung.kfilm;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API {
    final private String IP = "http://52.231.163.176:3001";
    final private String API_LINK_MOVIE_TYPE= IP+"/system/variable";
    final static private String LINK_GG_DRIVE = "https://www.googleapis.com/drive/v3/files/";
    final static private String KEY = "?alt=media&key=AIzaSyBbgWECKniqq5g9qdrqz1KOtnn0Zhu8tKs";

    private RequestQueue requestQueue;
    private ArrayList<Category> listCategory;
    private RecyclerView recyclerView;
    private Context context;


    public API(ArrayList<Category> listCategory, RecyclerView recyclerView, Context context) {
        this.requestQueue =  Volley.newRequestQueue(context);
        this.listCategory = listCategory;
        this.recyclerView = recyclerView;
        this.context = context;
    }
    public static String GetLinkGGDrive(String ID_ggdrive){
        return LINK_GG_DRIVE + ID_ggdrive + KEY;
    }
    protected void LoadListCategory(final ArrayList<Category> listCategory){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_LINK_MOVIE_TYPE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("types");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                listCategory.add(new Category(jsonObject.getString("name"),new ArrayList<Movie>()));
                            }

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
                        LoadListCategory(listCategory);
                    }
                });
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
                                        ""
                                );
                                Load_ID_GGDrive(movie, getLink_Films_Link_ID(movie.getId()));
                                listMovie.add(movie);
                            }
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
        requestQueue.add(jsonArrayRequest);
    }
    protected void Load_ID_GGDrive(final  Movie movie, final String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("video");

                            //**************Get one drive ID
                            JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
                            movie.setId_drive(jsonObject.getString("driveId"));
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
        requestQueue.add(jsonObjectRequest);
    }
    public String getLink_Films_Link_ID(String ID){
        return IP+"/films/link/"+ID;
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
