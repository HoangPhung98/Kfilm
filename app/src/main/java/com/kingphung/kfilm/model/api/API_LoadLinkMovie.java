package com.kingphung.kfilm.model.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kingphung.kfilm.presenter.playMovie.P_Imp_LoadLinkMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API_LoadLinkMovie {
    //CONSTANTS
    final private String IP = "http://52.231.163.176:3001";
    final static private String LINK_GG_DRIVE = "https://www.googleapis.com/drive/v3/files/";
    final static private String KEY = "?alt=media&key=AIzaSyA-dV0VdZ_A_3lxQB75P2c9IXBoFViixjk";
    //CONSTANTS>>>

    //Global vars
    private RequestQueue requestQueue;
    private Context context;
    private P_Imp_LoadLinkMovie imp_loadLinkMovie;
    private String ID;
    //Global vars>>>
    public API_LoadLinkMovie(P_Imp_LoadLinkMovie imp_loadLinkMovie, String ID , Context context){
        this.imp_loadLinkMovie = imp_loadLinkMovie;
        this.ID = ID;
        this.context = context;
    }
    public void Load_Link_GGDrive_and_Subtitle(final String ID){
        requestQueue = Volley.newRequestQueue(context);
        //load link to get video and sub and set to movie object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getLink_toGetDriveID_and_SubID(ID),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("video");

                            //**************Get 1 drive ID and sub link
                            JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
                            imp_loadLinkMovie.onAPIReturnLink(getLinkDriveOrSub(jsonObject.getString("driveId")),
                                                                getLinkDriveOrSub(response.getString("sub")));
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
    public String getLink_toGetDriveID_and_SubID(String ID){
        return IP+"/films/link/"+ID;
    }
    private String getLinkDriveOrSub(String id_drive_or_sub){
        //return link ggdrive that store video and subtitle
        Log.d("KingPhung","Link "+LINK_GG_DRIVE + id_drive_or_sub + KEY);
        return LINK_GG_DRIVE + id_drive_or_sub + KEY;
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
