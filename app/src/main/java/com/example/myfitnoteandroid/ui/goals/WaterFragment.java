package com.example.myfitnoteandroid.ui.goals;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

public class WaterFragment extends Fragment {
    ViewGroup root;
    Goal water = new Goal();
    Goal kcal = new Goal();
    private JSONObject postData;
    TextView water_goal,water_value;
    ImageButton button_plus;
    Button reset;
    LottieAnimationView glass,complete,menu_an;
    int[] glass_array = new int[12];
    int[] animation = new int[2];
    ConstraintLayout but_layout;
    CardView card_total,card_switch;
    String id_user;
    int counter = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_water, container, false);
        glass_array[0] = R.id.glass1;
        glass_array[1] = R.id.glass2;
        glass_array[2] = R.id.glass3;
        glass_array[3] = R.id.glass4;
        glass_array[4] = R.id.glass5;
        glass_array[5] = R.id.glass6;
        glass_array[6] = R.id.glass7;
        glass_array[7] = R.id.glass8;
        glass_array[8] = R.id.glass9;
        glass_array[9] = R.id.glass10;
        glass_array[10] = R.id.glass11;
        glass_array[11] = R.id.glass12;
        water_goal = root.findViewById(R.id.water_goal);
        water_value = root.findViewById(R.id.water_value);
        button_plus = root.findViewById(R.id.button_plus);
        but_layout = root.findViewById(R.id.but_layout);
        card_total = root.findViewById(R.id.card_total);
        complete = root.findViewById(R.id.complete_goal);
        card_switch = root.findViewById(R.id.card_switch);
        menu_an = root.findViewById(R.id.menu_an);
        animation[0]=R.raw.menu2;
        animation[1]=R.raw.switch_an;
        reset = root.findViewById(R.id.reset);
        button_plus_lst();
        button_reset_lst();
        getGoals();
        return root;
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
                        water.setGoal(status.getBoolean(0),value.getInt(0),progress.getDouble(0));
                        kcal.setGoal(status.getBoolean(1),value.getInt(1),progress.getDouble(1));
                        set_obiettivo();
                        set_valore();
                        set_card();
                        set_glasses();
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
            public void retry(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void set_card(){
        if(!water.getStatus_goal()){
            card_switch.setVisibility(View.VISIBLE);
            WaterFragment.MenuThread menu_thread = new  WaterFragment.MenuThread();
            menu_thread.start();
        }else
        if((water.getValue_goal()==0)){
            card_switch.setVisibility(View.VISIBLE);
            card_total.setVisibility(View.GONE);
        }else
        if(water.getProgress_goal()!=water.getValue_goal()){
            card_switch.setVisibility(View.GONE);
            card_total.setVisibility(View.GONE);
        }else{
            card_total.setVisibility(View.VISIBLE);
            card_switch.setVisibility(View.GONE);
            button_plus.setVisibility(View.GONE);
            complete.setVisibility(View.GONE);
        }
    }
    public void activate_menu(){
        menu_an.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                if(counter==0){
                    menu_an.setAnimation(animation[1]);
                    menu_an.setMinAndMaxFrame(0,170);
                    menu_an.setSpeed(1);
                    menu_an.playAnimation();
                    counter=1;
                }else{
                    menu_an.setAnimation(animation[0]);
                    menu_an.setMinAndMaxFrame(0,130);
                    menu_an.setSpeed(-1);
                    menu_an.playAnimation();
                    counter=0;
                }
            }
        });
    }
        public void button_plus_lst(){
        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double value = water.getProgress_goal();
                value += 0.25;
                water.setProgress_goal(value);
                removeGoals();
                if(value<=water.getValue_goal()){
                    card_total.setVisibility(View.GONE);
                    ConstraintLayout.LayoutParams params_ = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    if(value==1.5){
                        params_.leftMargin = params_.leftMargin - (103*5);
                    params_.topMargin = params_.topMargin + 170;
                    }
                else{
                        params_.leftMargin = params_.leftMargin + 103;
                        Log.d("ff","ff"+103);
                    }
                    but_layout.setLayoutParams(params_ );
                    set_valore();
                    int j= (int) (water.getProgress_goal()/0.25);
                    set_glasses2(j);
                    if (value == water.getValue_goal()) {
                        card_total.setVisibility(View.VISIBLE);
                        button_plus.setVisibility(View.GONE);
                        set_glasses2(0);
                        complete.setVisibility(View.VISIBLE);
                        complete.setMinAndMaxFrame(0,50);
                        complete.playAnimation();
                    }}
            }
        });
    }
    public void button_reset_lst(){
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(water.getProgress_goal()==water.getValue_goal()){
                    complete.setVisibility(View.GONE);
                }
                unset_glasses();
                int j = (int)(water.getProgress_goal()/0.25);

                water.setProgress_goal(0.0);
                removeGoals2();
            }
        });
    }
    public void unset_glasses(){
        double progress = water.getProgress_goal();
        int j = (int)( progress/0.25);
        for(int i=0; i<j;i++){
            glass = root.findViewById(glass_array[i]);
            glass.setVisibility(View.VISIBLE);
            glass.setMinAndMaxFrame(0,27);
            glass.setSpeed(-1);
            glass.playAnimation();
        }
        if(j>=6){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
            params.leftMargin = params.leftMargin - (103*(j-6));
            params.topMargin = params.topMargin - 170;
            but_layout.setLayoutParams(params);
        }else{
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
            params.leftMargin = params.leftMargin - (103*j);
            but_layout.setLayoutParams(params);
        }
    }
    @SuppressLint("SetTextI18n")
    public void set_obiettivo(){
        water_goal.setText("Obiettivo: "+water.getValue_goal()+" Litri");
    }

    @SuppressLint("SetTextI18n")
    public void set_valore(){
        water_value.setText(water.getProgress_goal()+"L");
    }

    public void set_glasses() {
        int value = water.getValue_goal();
        double progress = water.getProgress_goal();
        double value_control = 0.25;
        int i=0;
        for(i=0; i<12; i++){
            glass = root.findViewById(glass_array[i]);
            glass.setVisibility(View.VISIBLE);
            if(progress<value_control){
                button_plus.setVisibility(View.VISIBLE);
            }else{
                if(i!=5){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin = params.leftMargin + 103;
                    but_layout.setLayoutParams(params);

                }else{
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)but_layout.getLayoutParams();
                    params.leftMargin =params.leftMargin - (103*5);
                    params.topMargin = params.topMargin + 170;
                    but_layout.setLayoutParams(params);
                }
                glass.setMinAndMaxFrame(0,27);
                glass.setSpeed(1);
                glass.playAnimation();
            }
            if(value_control>value){
                glass = root.findViewById(glass_array[i]);
                glass.setVisibility(View.GONE);
            }
            value_control+=0.25;
        }

        if(progress==value){
            button_plus.setVisibility(View.GONE);
            card_total.setVisibility(View.VISIBLE);
            set_glasses2(0);
            complete.setVisibility(View.VISIBLE);
            complete.setMinAndMaxFrame(0,50);
            complete.playAnimation();
        }
    }
    public void set_glasses2(int i){
        if(i==0){
            for(int j=0;j<12;j++){
                glass = root.findViewById(glass_array[j]);
                glass.setVisibility(View.GONE);
            }
        }else{
            glass = root.findViewById(glass_array[i-1]);
            glass.setVisibility(View.VISIBLE);
            glass.setMinAndMaxFrame(0,27);
            glass.setSpeed(1);
            glass.playAnimation();
        }
    }

    public void removeGoals(){
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            id_user = sessionManager.getSession();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/remove_goals";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateGoals();

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
            public void retry(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
    public void removeGoals2(){
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            id_user = sessionManager.getSession();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/remove_goals";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateGoals2();

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
            public void retry(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
    public void updateGoals() {
        postData = new JSONObject();
        Boolean[] status_array = new Boolean[]{water.getStatus_goal(),kcal.getStatus_goal()};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{water.getValue_goal(),kcal.getValue_goal()};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{water.getProgress_goal(),kcal.getProgress_goal()};
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
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
            public void retry(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    public void updateGoals2() {
        postData = new JSONObject();
        Boolean[] status_array = new Boolean[]{water.getStatus_goal(),kcal.getStatus_goal()};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{water.getValue_goal(),kcal.getValue_goal()};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{water.getProgress_goal(),kcal.getProgress_goal()};
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
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getGoals();
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
            public void retry(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    class MenuThread extends Thread{
        @Override
        public void run(){
            activate_menu();
        }
    }
}