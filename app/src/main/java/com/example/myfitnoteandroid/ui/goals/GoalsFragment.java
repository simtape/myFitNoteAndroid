package com.example.myfitnoteandroid.ui.goals;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.MainActivity;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.ui.sheets.SheetDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import static android.widget.Toast.LENGTH_SHORT;


public class GoalsFragment extends Fragment {

    String mTitle[] = {"Acqua", "Grassi Bruciati"};
    String mDescription[] = {"Quantità di acqua da bere", "Calorie da bruciare"};
    Button conferma;
    Boolean userGoals[] = {false, false};
    int progressGoals[];
    int valueGoals[];
    int images[] = {R.drawable.ic_baseline_local_drink_24, R.drawable.ic_kcal};
    Switch waterSwitch, kcalSwitch;
    ViewGroup root;
    private JSONObject postData;
    private JSONObject goals;
    int arraySize;
    TextView water, kcal;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getGoals();

        root = (ViewGroup) inflater.inflate(R.layout.goals_fragment, container, false);
        water = root.findViewById(R.id.tvWater);
        kcal = root.findViewById(R.id.tvKcal);
        waterSwitch = root.findViewById(R.id.switchWater);
        kcalSwitch = root.findViewById(R.id.switchKcal);
        water.setText(mTitle[0]);
        kcal.setText(mTitle[1]);
        confermaButton();
        return root;
    }


    public void getGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            Log.d("user_id", sessionManager.getSession());
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
                    goals = response.getJSONObject("goal");
                    if (res != null) {
                        JSONArray status = res.getJSONArray("status");
                        Log.d("status to string", status.toString());
                        userGoals = new Boolean[status.length()];
                        for (int i = 0; i < status.length(); i++) {
                            userGoals[i] = status.getBoolean(i);
                        }
                        setSwitches();
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

    public void createGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            Log.d("user_id", sessionManager.getSession());
            Log.d("user_id", "d" + postData.getBoolean("status[0]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://myfitnote.herokuapp.com/goals/create_goals";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONObject res = response.getJSONObject("goal");
                    if (res != null) {
                        JSONArray status = res.getJSONArray("status");
                        Log.d("status to string", status.toString());
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

    }


    public void setSwitches() {
        //ricerca di acqua
        for (int i = 0; i < mTitle.length; i++) {
            Log.d("for", "entra");

            if (userGoals[0]) {
                waterSwitch.setChecked(true);
            }

        }

        //ricerca di kcal
        for (int j = 0; j < mTitle.length; j++) {
            Log.d("for", "entra");

            if (userGoals[1]) {


                kcalSwitch.setChecked(true);
            }

        }

    }

    public void updateGoals() {


    }

    public void confermaButton() {
        conferma = root.findViewById(R.id.conferma);
        conferma.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Switch switchBtn;
                Log.d("msg", "but");
                switchBtn = root.findViewById(R.id.switchWater);
                if (switchBtn.isChecked()) {
                    userGoals[0] = true;
                } else {
                    userGoals[0] = false;
                }
                switchBtn = root.findViewById(R.id.switchKcal);
                if (switchBtn.isChecked()) {
                    userGoals[1] = true;
                } else {
                    userGoals[1] = false;
                }
            }
        });
    }

}