package com.example.myfitnoteandroid.ui.home;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeSheetFragment extends Fragment {
    Sheet lastSheet;

    public HomeSheetFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_sheet, container, false);
    }

    public void getLastSheet() {
        JSONObject postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("user_id", sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        String url = "http://myfitnote.herokuapp.com/sheets/get_last_sheet";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                

                            } else {


                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }
}