package com.example.myfitnoteandroid.ui.goals;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WaterFragmentSecond extends Fragment {
    ViewGroup root;
    LottieAnimationView glass_second;
    Goal water_second = new Goal();
    Goal kcal_second = new Goal();
    private JSONObject postData;
    TextView water_goal_second,water_value_second;
    public static WaterFragmentSecond newInstance() { return new WaterFragmentSecond(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_water_second, container, false);
        glass_second = root.findViewById(R.id.glass_second);
        water_goal_second = root.findViewById(R.id.water_goal_second);
        water_value_second = root.findViewById(R.id.water_value_second);
        getGoals();
        return root;
    }


    public void getGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/get_goals";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("goal");
                    if (res != null) {
                        JSONArray status = res.getJSONArray("status");
                        JSONArray value = res.getJSONArray("obiettivo");
                        JSONArray progress = res.getJSONArray("valore_attuale");
                        water_second.setGoal(status.getBoolean(0),value.getInt(0),progress.getDouble(0));
                        kcal_second.setGoal(status.getBoolean(1),value.getInt(1),progress.getDouble(1));
                        set_obiettivo2();
                        set_valore2();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(jsonObjectRequest);
    }
    public void set_obiettivo2(){
        water_goal_second.setText("Obiettivo: "+water_second.getValue_goal()+" L");
    }

    public void set_valore2(){
        water_value_second.setText(water_second.getProgress_goal()+"L");
        set_glass();
    }
    public void set_glass(){
        int value = water_second.getValue_goal();
        double progress = water_second.getProgress_goal();
        switch(value) {
            case 1:
                double cont=0;
                    if(progress>=cont+0.25){
                        glass_second.setMinAndMaxFrame(0,8);
                        glass_second.playAnimation();
                        cont+=0.25;
                    }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(8,16);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(16,24);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(24,32);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                break;
            case 2:
                cont=0;
                for(int i=0;i<=7;i++){
                    if(progress>cont+0.25){
                        glass_second.setMinAndMaxFrame(0,27);
                        glass_second.playAnimation();
                    }
                }
            case 3:
                cont=0;
                for(int i=0;i<=11;i++){
                    if(progress>cont+0.25){
                        glass_second.setMinAndMaxFrame(0,27);
                        glass_second.playAnimation();
                    }
                }
        }
    }


}