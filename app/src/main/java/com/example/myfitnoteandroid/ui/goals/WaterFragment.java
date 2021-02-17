package com.example.myfitnoteandroid.ui.goals;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.sql.Struct;

public class WaterFragment extends Fragment {
    ViewGroup root;
    Goal water = new Goal();
    Goal kcal = new Goal();
    private JSONObject postData;
    TextView water_goal,water_value;
    public static WaterFragment newInstance() {
        return new WaterFragment();
    }
    ImageButton button_plus;
    LottieAnimationView glass,complete;
    ConstraintLayout but_layout;
    CardView card_total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_water, container, false);
        water_goal = root.findViewById(R.id.water_goal);
        water_value = root.findViewById(R.id.water_value);
        button_plus = root.findViewById(R.id.button_plus);
        but_layout = root.findViewById(R.id.but_layout);
        card_total = root.findViewById(R.id.card_total);
        complete = root.findViewById(R.id.complete_goal);
        button_plus_lst();
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
                        water.setGoal(status.getBoolean(0),value.getInt(0),progress.getDouble(0));
                        kcal.setGoal(status.getBoolean(1),value.getInt(1),progress.getDouble(1));
                        set_obiettivo();
                        set_valore();
                        set_glasses();
                        set_card();
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

    public void set_card(){
        if((water.getValue_goal()==0)){
            card_total.setVisibility(View.VISIBLE);
        }else
        if(water.getProgress_goal()!=water.getValue_goal()){
            card_total.setVisibility(View.GONE);
        }else{
            card_total.setVisibility(View.GONE);
            button_plus.setVisibility(View.GONE);
        }
    }

    public void button_plus_lst(){
        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double value = water.getProgress_goal();
                value += 0.25;
                water.setProgress_goal(value);
                if(value<=water.getValue_goal()){
                    card_total.setVisibility(View.GONE);
                if(value==1.5){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin - (103*5);
                    params.topMargin = params.topMargin + 170;
                    but_layout.setLayoutParams(params);
                }
                else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                }
                set_valore();
                String value_sw = Double.toString(value);
                switch (value_sw){
                    case "0.25":
                        set_glasses2(1);
                    break;
                    case "0.5":
                        set_glasses2(2);
                        break;
                    case "0.75":
                        set_glasses2(3);
                        break;
                    case "1.0":
                        set_glasses2(4);
                        break;
                    case "1.25":
                        set_glasses2(5);
                        break;
                    case "1.5":
                        set_glasses2(6);
                        break;
                    case "1.75":
                        set_glasses2(7);
                        break;
                    case "2.0":
                        set_glasses2(8);
                        break;
                    case "2.25":
                        set_glasses2(9);
                        break;
                    case "2.5":
                        set_glasses2(10);
                        break;
                    case "2.75":
                        set_glasses2(11);
                        break;
                    case "3.0":
                        set_glasses2(12);
                        break;
                }
                    if (value == water.getValue_goal()) {
                        card_total.setVisibility(View.VISIBLE);
                        set_glasses2(0);
                        complete.setMinAndMaxFrame(0,50);
                        complete.playAnimation();
                    }}
            }
        });
    }

    public void set_obiettivo(){
        water_goal.setText("Obiettivo: "+water.getValue_goal()+" Litri");
    }

    public void set_valore(){
        water_value.setText(water.getProgress_goal()+"L");
    }

    public void set_glasses(){
        int value = water.getValue_goal();
        double progress = water.getProgress_goal();
        Log.d("msg","s"+value);
        switch(value){
            case 1:
                glass = root.findViewById(R.id.glass1);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass2);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass3);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass4);
                glass.setVisibility(View.VISIBLE);
                if(progress<1){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass5);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass6);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass7);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass8);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass9);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass10);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass11);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass12);
                glass.setVisibility(View.GONE);
                break;
            case 2:
                glass = root.findViewById(R.id.glass1);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass2);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass3);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass4);
                glass.setVisibility(View.VISIBLE);
                if(progress<1){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass5);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass6);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin =params.leftMargin - (103*5);
                    params.topMargin = params.topMargin + 170;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass7);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass8);
                glass.setVisibility(View.VISIBLE);
                if(progress<2){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass9);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass10);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass11);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass12);
                glass.setVisibility(View.GONE);
                break;
            case 3:

                glass = root.findViewById(R.id.glass1);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass2);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass3);
                glass.setVisibility(View.VISIBLE);
                if(progress<0.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass4);
                glass.setVisibility(View.VISIBLE);
                if(progress<1){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();}
                glass = root.findViewById(R.id.glass5);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass6);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin =params.leftMargin - (103*5);
                    params.topMargin = params.topMargin + 170;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass7);
                glass.setVisibility(View.VISIBLE);
                if(progress<1.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass8);
                glass.setVisibility(View.VISIBLE);
                if(progress<2){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass9);
                glass.setVisibility(View.VISIBLE);
                if(progress<2.25){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass10);
                glass.setVisibility(View.VISIBLE);
                if(progress<2.5){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass11);
                glass.setVisibility(View.VISIBLE);
                if(progress<2.75){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                glass = root.findViewById(R.id.glass12);
                glass.setVisibility(View.VISIBLE);
                if(progress<3){
                    button_plus.setVisibility(View.VISIBLE);
                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);
                    glass.setMinAndMaxFrame(0,27);
                    glass.playAnimation();
                }
                break;
        }
    }
    public void set_glasses2(int i){
        switch(i){
            case 0:
                glass = root.findViewById(R.id.glass1);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass2);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass3);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass4);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass5);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass6);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass7);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass8);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass9);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass10);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass11);
                glass.setVisibility(View.GONE);
                glass = root.findViewById(R.id.glass12);
                glass.setVisibility(View.GONE);
                break;
            case 1:
                glass = root.findViewById(R.id.glass1);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
            break;
            case 2:
                glass = root.findViewById(R.id.glass2);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 3:
                glass = root.findViewById(R.id.glass3);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 4:
                glass = root.findViewById(R.id.glass4);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 5:
                glass = root.findViewById(R.id.glass5);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 6:
                glass = root.findViewById(R.id.glass6);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;
            case 7:
                glass = root.findViewById(R.id.glass7);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;
            case 8:
                glass = root.findViewById(R.id.glass8);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 9:
                glass = root.findViewById(R.id.glass9);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 10:
                glass = root.findViewById(R.id.glass10);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 11:
                glass = root.findViewById(R.id.glass11);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;

            case 12:
                glass = root.findViewById(R.id.glass12);
                glass.setVisibility(View.VISIBLE);
                glass.setMinAndMaxFrame(0,27);
                glass.playAnimation();
                break;
        }
    }



}