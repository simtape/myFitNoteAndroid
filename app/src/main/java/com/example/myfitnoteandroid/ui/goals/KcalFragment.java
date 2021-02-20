package com.example.myfitnoteandroid.ui.goals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
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

public class KcalFragment extends Fragment {
    JSONObject postData;
    ViewGroup root;
    LottieAnimationView loader,kcal_an;
    ProgressBar kcal_bar;
    int peso;
    int counter;
    int peso_cont;
    float percentage_cont;
    int percentage;
    float kcal;
    TextView percentual;
    CardView card_switch;
    int kcal_goal;
    boolean status_goal;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGoals();
        SessionManager sessionManager = new SessionManager(requireContext());
        peso = Integer.parseInt(sessionManager.getPeso());
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        counter=sharedPreferences.getInt("stepCount", 0);
        kcal = cKcal();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                set_percentage();
            }
        }, 2000);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.kcal_fragment, container, false);
        loader = root.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        kcal_an = root.findViewById(R.id.kcal_an);
        kcal_bar = root.findViewById(R.id.kcal_bar);
        kcal_bar.setVisibility(View.GONE);
        percentual = root.findViewById(R.id.percentual);
        card_switch = root.findViewById(R.id.card_switch_off);
        card_switch.setVisibility(View.GONE);
        return root;
    }
    public float cKcal() {
        peso_cont = peso;
        return (float) ((0.0005) * (peso) * (cMetres()));
    }
    public float cMetres() {
        return (float) (0.762 * counter);
    }

    public void set_animation_kcal(){
        int i;
        int perc_control=0;
        if(percentage>=100){
            kcal_bar.setVisibility(View.GONE);
            kcal_an.setVisibility(View.VISIBLE);
            kcal_an.setMinAndMaxFrame(0,60);
            kcal_an.setSpeed(1);
            kcal_an.playAnimation();
            percentual.setVisibility(View.GONE);
        }else{
            do{
            if(percentage>=perc_control){
                kcal_bar.setVisibility(View.VISIBLE);
                kcal_an.setVisibility(View.GONE);
                kcal_bar.setProgress(perc_control);
                perc_control+=1;
                i=0;
            }else{
                i=1;
            }
        }while(i==0);}
    }

    public  void set_card(){
        if(!status_goal){
            card_switch.setVisibility(View.VISIBLE);
            percentual.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
        }else
        {
            loader.setVisibility(View.GONE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    card_switch.setVisibility(View.GONE);
                    percentual.setText(percentage+"%");
                    set_animation_kcal();
                }
            }, 500);
        }
    }

    public void set_percentage(){
        percentage_cont = (kcal*100)/kcal_goal;
        percentage = (int) percentage_cont;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                set_card();
            }
        }, 500);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(requireContext());
        try {
            postData.put("id_user", sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/get_goals";
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("goal");
                        JSONArray status = res.getJSONArray("status");
                        JSONArray value = res.getJSONArray("obiettivo");
                        kcal_goal= value.getInt(1);
                    status_goal = status.getBoolean(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    createGoals();
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createGoals() {
        Boolean[] status_array = new Boolean[]{false,false};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{3,4000};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{0.00,0.00};
        JSONArray prog_jarray = new JSONArray(Arrays.asList(prog_array));
        SessionManager sessionManager = new SessionManager(requireContext());
        try {
            postData.put("status",status_jarray);
            postData.put("obiettivo",value_jarray);
            postData.put("valore_attuale",prog_jarray);
            postData.put("id_user", sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/create_goals";
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("goal");
                    JSONArray status = res.getJSONArray("status");
                    JSONArray value = res.getJSONArray("obiettivo");
                    kcal_goal= value.getInt(1);
                    status_goal = status.getBoolean(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    set_percentage();
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
}