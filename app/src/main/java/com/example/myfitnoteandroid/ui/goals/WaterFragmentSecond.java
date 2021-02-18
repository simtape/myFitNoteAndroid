package com.example.myfitnoteandroid.ui.goals;

import android.animation.Animator;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.util.Arrays;

public class WaterFragmentSecond extends Fragment {
    ViewGroup root;
    LottieAnimationView glass_second,complete_goal,menu_an_second;
    Goal water_second = new Goal();
    Goal kcal_second = new Goal();
    private JSONObject postData;
    TextView water_goal_second,water_value_second;
    ImageButton plus_second;
    Button reset_second;
    String id_user;
    CardView card_switch_second;
    public static WaterFragmentSecond newInstance() { return new WaterFragmentSecond(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_water_second, container, false);
        glass_second = root.findViewById(R.id.glass_second);
        water_goal_second = root.findViewById(R.id.water_goal_second);
        water_value_second = root.findViewById(R.id.water_value_second);
        complete_goal = root.findViewById(R.id.water_complete);
        reset_second = root.findViewById(R.id.reset_second);
        card_switch_second = root.findViewById(R.id.card_switch_second);
        menu_an_second = root.findViewById(R.id.menu_an_second);
        plus_second= root.findViewById(R.id.plus_second);
        getGoals();
        button_plus_lst();
        reset_lst();
        return root;
    }
    public void set_card(){
        if(!water_second.getStatus_goal()){
            card_switch_second.setVisibility(View.VISIBLE);
            reset_second.setVisibility(View.GONE);
            activate_menu();
        }
        if(water_second.getStatus_goal()){
            card_switch_second.setVisibility(View.GONE);
            reset_second.setVisibility(View.VISIBLE);
        }
    }
    public void activate_menu(){
        menu_an_second.setAnimation(R.raw.menu2);
        menu_an_second.setMinAndMaxFrame(0,130);
        menu_an_second.setSpeed(-1);
        menu_an_second.playAnimation();
        menu_an_second.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                activate_switch();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void activate_switch() {
        menu_an_second.setAnimation(R.raw.switch_an);
        menu_an_second.setMinAndMaxFrame(0,170);
        menu_an_second.setSpeed(1);
        menu_an_second.playAnimation();
        menu_an_second.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                activate_menu();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    public void getGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            id_user = sessionManager.getSession();
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
                        set_card();
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
                    plus_second.setVisibility(View.GONE);
                    glass_second.setVisibility(View.GONE);
                    complete_goal.setMinAndMaxFrame(0,50);
                    complete_goal.playAnimation();
                }
                break;
            case 2:
                cont=0;
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(0,4);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(4,8);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(8,12);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(12,16);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(16,20);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(20,24);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(24,28);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(28,32);
                    glass_second.playAnimation();
                    cont+=0.25;
                    plus_second.setVisibility(View.GONE);
                    glass_second.setVisibility(View.GONE);
                    complete_goal.setMinAndMaxFrame(0,50);
                    complete_goal.playAnimation();
                }
                break;
            case 3:
                cont=0;
                if(progress>=cont+0.25){
                glass_second.setMinAndMaxFrame(0,2);
                glass_second.playAnimation();
                cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(2,5);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(5,7);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(7,10);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(10,12);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(12,15);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(15,17);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(17,20);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(20,22);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(22,25);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(25,28);
                    glass_second.playAnimation();
                    cont+=0.25;
                }
                if(progress>=cont+0.25){
                    glass_second.setMinAndMaxFrame(28,36);
                    glass_second.playAnimation();
                    cont+=0.25;
                    plus_second.setVisibility(View.GONE);
                    glass_second.setVisibility(View.GONE);
                    complete_goal.setMinAndMaxFrame(0,50);
                    complete_goal.playAnimation();
                }
                break;
        }
    }

    public void button_plus_lst(){
        plus_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glass_second.setSpeed(1);
                double value = water_second.getProgress_goal();
                value += 0.25;
                water_second.setProgress_goal(value);
                if(value<=water_second.getValue_goal()){
                    set_valore2();
                    int sec1;
                    int sec2;
                    double cont;
                    switch (water_second.getValue_goal()){
                        case 1:
                            sec1 = 0;
                            sec2 = 9;
                            cont=0.25;
                            for(int i=0; i<=3; i++){
                                if(value == cont){
                                    glass_second.setMinAndMaxFrame(sec1,sec2);
                                    glass_second.playAnimation();
                                }
                                if(value == water_second.getValue_goal()){
                                    plus_second.setVisibility(View.GONE);
                                    glass_second.setVisibility(View.GONE);
                                    complete_goal.setVisibility(View.VISIBLE);
                                    complete_goal.setMinAndMaxFrame(0,50);
                                    complete_goal.playAnimation();
                                }
                                cont+=0.25;
                                sec1+=9;
                                sec2+=9;
                            }
                            break;
                        case 2:
                            sec1 = 0;
                            sec2 = 4;
                            cont=0.25;
                            for(int i=0; i<=7; i++){
                                if(value == cont){
                                    glass_second.setMinAndMaxFrame(sec1,sec2);
                                    glass_second.playAnimation();
                                }
                                if(value == water_second.getValue_goal()){
                                    plus_second.setVisibility(View.GONE);
                                    glass_second.setVisibility(View.GONE);
                                    complete_goal.setVisibility(View.VISIBLE);
                                    complete_goal.setMinAndMaxFrame(0,50);
                                    complete_goal.playAnimation();
                                }
                                cont+=0.25;
                                sec1+=4;
                                sec2+=4;
                            }
                            break;
                        case 3:
                            sec1 = 0;
                            sec2 = 3;
                            cont=0.25;
                            for(int i=0; i<=11; i++){
                                if(value == cont){
                                    glass_second.setMinAndMaxFrame(sec1,sec2);
                                    glass_second.playAnimation();
                                }
                                if(value == water_second.getValue_goal()){
                                    plus_second.setVisibility(View.GONE);
                                    glass_second.setVisibility(View.GONE);
                                    complete_goal.setVisibility(View.VISIBLE);
                                    complete_goal.setMinAndMaxFrame(0,50);
                                    complete_goal.playAnimation();
                                }
                                cont+=0.25;
                                sec1+=3;
                                sec2+=3;
                            }
                            break;
                    }
                }
                removeGoals();
            }
        });
        }
    public void unset_glass(){
        int value = water_second.getValue_goal();
        double progress = water_second.getProgress_goal();
        String value_sw = Double.toString(progress);
        switch(value) {
            case 1:
                switch (value_sw){
                    case "1.0":
                        glass_second.setMinAndMaxFrame(0,8);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.75":
                        glass_second.setMinAndMaxFrame(0,16);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.5":
                        glass_second.setMinAndMaxFrame(0,24);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.25":
                        glass_second.setMinAndMaxFrame(0,32);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                }
            case 2:
                switch (value_sw){
                    case "2.0":
                        glass_second.setMinAndMaxFrame(0,32);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.75":
                        glass_second.setMinAndMaxFrame(0,28);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.5":
                        glass_second.setMinAndMaxFrame(0,24);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.25":
                        glass_second.setMinAndMaxFrame(0,20);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.0":
                        glass_second.setMinAndMaxFrame(0,16);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.75":
                        glass_second.setMinAndMaxFrame(0,12);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.5":
                        glass_second.setMinAndMaxFrame(0,8);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                        case "0.25":
                        glass_second.setMinAndMaxFrame(0,4);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                }
            case 3:
                switch (value_sw){
                    case "3.0":
                        glass_second.setMinAndMaxFrame(0,36);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "2.75":
                        glass_second.setMinAndMaxFrame(0,33);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "2.5":
                        glass_second.setMinAndMaxFrame(0,30);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "2.25":
                        glass_second.setMinAndMaxFrame(0,27);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "2.0":
                        glass_second.setMinAndMaxFrame(0,24);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.75":
                        glass_second.setMinAndMaxFrame(0,21);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.5":
                        glass_second.setMinAndMaxFrame(0,18);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.25":
                        glass_second.setMinAndMaxFrame(0,15);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "1.0":
                        glass_second.setMinAndMaxFrame(0,12);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.75":
                        glass_second.setMinAndMaxFrame(0,9);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.5":
                        glass_second.setMinAndMaxFrame(0,6);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                    case "0.25":
                        glass_second.setMinAndMaxFrame(0,3);
                        glass_second.setSpeed(-1);
                        glass_second.playAnimation();
                        break;
                }
        }
    }
    public void reset_lst() {
        reset_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(water_second.getProgress_goal()==water_second.getValue_goal()){
                    complete_goal.setVisibility(View.GONE);
                    plus_second.setVisibility(View.VISIBLE);
                    glass_second.setVisibility(View.VISIBLE);
                }
                unset_glass();
                water_second.setProgress_goal(0.0);
                removeGoals();
                set_valore2();
            }
        });
    }

    public void removeGoals(){
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            id_user = sessionManager.getSession();
            Log.d("user_id", id_user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/remove_goals";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Post", response.getString("message"));
                    updateGoals();
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

        queue.add(jsonObjectRequest);}
    public void updateGoals() {
        postData = new JSONObject();
        Boolean[] status_array = new Boolean[]{water_second.getStatus_goal(),kcal_second.getStatus_goal()};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{water_second.getValue_goal(),kcal_second.getValue_goal()};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{water_second.getProgress_goal(),kcal_second.getProgress_goal()};
        JSONArray prog_jarray = new JSONArray(Arrays.asList(prog_array));
        try {
            postData.put("status",status_jarray);
            postData.put("obiettivo",value_jarray);
            postData.put("valore_attuale",prog_jarray);
            postData.put("id_user", id_user);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/update_goals";
        Log.d("PostDAta",postData.toString());
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("goal");
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
    }