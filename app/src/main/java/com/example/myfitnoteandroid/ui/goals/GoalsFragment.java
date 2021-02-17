package com.example.myfitnoteandroid.ui.goals;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class GoalsFragment extends Fragment {

    String mTitle[] = {"Acqua", "Grassi Bruciati"};
    Button conferma,conferma2;
    String id_user;
    Goal water_goal = new Goal();
    Goal kcal_goal = new Goal();
    Switch waterSwitch, kcalSwitch;
    ViewGroup root;
    private JSONObject postData;
    JSONObject local_goal;
    int arraySize;
    TextView water, kcal;
    EditText kcal_value,water_value;
    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getGoals();
        root = (ViewGroup) inflater.inflate(R.layout.goals_fragment, container, false);
        conferma = root.findViewById(R.id.conferma);
        conferma.setEnabled(false);
        water = root.findViewById(R.id.tvWater);
        kcal = root.findViewById(R.id.tvKcal);
        kcal_value = root.findViewById(R.id.kcal_value_num);
        water_value = root.findViewById(R.id.water_value_num);
        waterSwitch = root.findViewById(R.id.switchWater);
        kcalSwitch = root.findViewById(R.id.switchKcal);
        water.setText(mTitle[0]);
        kcal.setText(mTitle[1]);
        water_value.addTextChangedListener(edit_text);
        kcal_value.addTextChangedListener(edit_text);
        water_switchLst();
        kcal_switchLst();
        confermaButton();
        return root;
    }


    public void getGoals() {
        postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("id_user", sessionManager.getSession());
            id_user = sessionManager.getSession();
            Log.d("user_id", id_user);
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
                        water_goal.setGoal(status.getBoolean(0),value.getInt(0),progress.getInt(0));
                        kcal_goal.setGoal(status.getBoolean(1),value.getInt(1),progress.getInt(1));
                        local_goal = res;
                        setSwitches();
                    }
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
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(jsonObjectRequest);

    }
    public void createGoals() {
        Boolean[] status_array = new Boolean[]{false,false};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{3,4000};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{0.00,0.00};
        JSONArray prog_jarray = new JSONArray(Arrays.asList(prog_array));
        try {
            postData.put("status",status_jarray);
            postData.put("obiettivo",value_jarray);
            postData.put("valore_attuale",prog_jarray);
            postData.put("id_user", id_user);

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
        getGoals();
    }


    public void setSwitches() {
        //ricerca di acqua
        for (int i = 0; i < mTitle.length; i++) {
            Log.d("for", "entra");

            if (water_goal.getStatus_goal()) {
                waterSwitch.setChecked(true);
            }

        }
        //ricerca di kcal
        for (int j = 0; j < mTitle.length; j++) {
            Log.d("for", "entra");

            if (kcal_goal.getStatus_goal()) {
                kcalSwitch.setChecked(true);
            }
        }
        setWaterView();
        setKcalView();
    }

    public void updateGoals() {
        postData = new JSONObject();
        Boolean[] status_array = new Boolean[]{water_goal.getStatus_goal(),kcal_goal.getStatus_goal()};
        JSONArray status_jarray = new JSONArray(Arrays.asList(status_array));
        Integer[] value_array = new Integer[]{water_goal.getValue_goal(),kcal_goal.getValue_goal()};
        JSONArray value_jarray = new JSONArray(Arrays.asList(value_array));
        Double[] prog_array = new Double[]{0.0,0.0};
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
                    Log.d("Post",res.toString());
                    Toast.makeText(getContext(), "Modifica effettuata", Toast.LENGTH_LONG).show();
                    conferma.setEnabled(false);
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

    public void confermaButton() {
        conferma = root.findViewById(R.id.conferma);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                water_goal.setStatus_goal(waterSwitch.isChecked());
                kcal_goal.setStatus_goal(kcalSwitch.isChecked());
                if(water_value.getText().toString().trim().length() != 0){
                    int w;
                    w = Integer.parseInt(water_value.getText().toString());
                if(w>3){
                    Toast.makeText(getContext(), "MAX 3 Litri", Toast.LENGTH_LONG).show();
                    return;
                }else if(w!=0){
                    water_goal.setValue_goal(w);
                }}
                if(kcal_value.getText().toString().trim().length() != 0){
                    int k;
                    k = Integer.parseInt(kcal_value.getText().toString());
                if(k>4000){
                    Toast.makeText(getContext(), "MAX 4000 Kcal", Toast.LENGTH_LONG).show();
                    return;
                }else if(k!=0){
                    kcal_goal.setValue_goal(k);
                }}
                removeGoals();
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
                    Log.d("Post",response.getString("message"));
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

        queue.add(jsonObjectRequest);
    }
    public void water_switchLst() {
        Button water = (Button) root.findViewById(R.id.switchWater);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWaterView();
                conferma.setEnabled(true);
            }
        });
    }
    public void kcal_switchLst() {
        Button kcal = (Button) root.findViewById(R.id.switchKcal);
        kcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setKcalView();
                conferma.setEnabled(true);
            }
        });
    }
    public TextWatcher edit_text = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String wat = water_value.getText().toString().trim();
            String kal = kcal_value.getText().toString().trim();
            conferma.setEnabled((wat.isEmpty())||(kal.isEmpty()));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };
    public void setWaterView() {
        CardView water_value = (CardView) root.findViewById(R.id.water_value);
        setWaterHint();
        //ricerca di acqua
            if (waterSwitch.isChecked()) {
                water_value.setVisibility(View.VISIBLE);
            }else {
                water_value.setVisibility(View.GONE);
            }
    }
    public void setWaterHint() {
        String water = String.valueOf(water_goal.getValue_goal());
        water_value.setHint(water);
    }
    public void setKcalView() {
        CardView kcal_value = (CardView) root.findViewById(R.id.kcal_value);
        setKcalHint();
        if (kcalSwitch.isChecked()) {
            kcal_value.setVisibility(View.VISIBLE);
        }else {
            kcal_value.setVisibility(View.GONE);
        }
    }
    public void setKcalHint() {
        String kcal = String.valueOf(kcal_goal.getValue_goal());
        kcal_value.setHint(kcal);
    }
}