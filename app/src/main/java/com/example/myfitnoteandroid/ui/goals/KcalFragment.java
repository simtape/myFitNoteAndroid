package com.example.myfitnoteandroid.ui.goals;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.myfitnoteandroid.data.StepCounterHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class KcalFragment extends Fragment {
    JSONObject postData;
    ViewGroup root;
    LottieAnimationView kcal_an;
    int peso;
    int counter;
    int peso_cont;
    float percentage;
    String passi;
    Goal kcal_goal=new Goal();
    float kcal;
    TextView percentual;
    CardView card_switch;
    public static KcalFragment newInstance() {
        return new KcalFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager sessionManager = new SessionManager(getContext());
        peso = Integer.parseInt(sessionManager.getPeso());
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        counter=sharedPreferences.getInt("stepCount", 0);
        kcal = cKcal();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.kcal_fragment, container, false);
        kcal_an = root.findViewById(R.id.kcal_an);
        percentual = root.findViewById(R.id.percentual);
        card_switch = root.findViewById(R.id.card_switch_off);
        getGoals();
        return root;
    }
    public float cKcal() {
        peso_cont = peso;
        float kcal_ = (float) ((0.0005) * (peso) * (cMetres()));
        return kcal_;
    }
    public float cMetres() {
        float metres = (float) (0.762 * counter);
        return metres;
    }

    public void set_animation_kcal(){
        int i=0;
        int perc_control=0;
        int sec1=0;
        int sec2=2;
        if(percentage>=100){
            kcal_an.setAnimation(R.raw.kcal);
            kcal_an.setMinAndMaxFrame(0,60);
            kcal_an.setSpeed(1);
            kcal_an.playAnimation();
        }else{
            DecimalFormat df = new DecimalFormat("0");
            percentual.setText(df.format(percentage)+"%");
            do{
            if(percentage>=perc_control){
                kcal_an.setMinAndMaxFrame(sec1,sec2);
                kcal_an.setSpeed(3);
                sec1+=2;
                sec2+=2;
                perc_control+=1;
                kcal_an.playAnimation();
            }else{
                i=1;
            }
        }while(i==0);}
    }

    public  void set_card(){
        if(!kcal_goal.getStatus_goal()){
            card_switch.setVisibility(View.VISIBLE);

        }else
        {
            card_switch.setVisibility(View.GONE);

        }
    }

    public void set_percentage(){
        percentage =(kcal*100)/kcal_goal.getValue_goal();
        DecimalFormat df = new DecimalFormat("0.0");
        percentage = Float.parseFloat(df.format(percentage));
        Log.d("perc","perc"+percentage);
        set_card();
        set_animation_kcal();
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
                        kcal_goal.setGoal(status.getBoolean(1),value.getInt(1),progress.getDouble(1));
                        set_percentage();

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
}