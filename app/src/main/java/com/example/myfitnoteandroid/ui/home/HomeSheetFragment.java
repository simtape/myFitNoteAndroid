package com.example.myfitnoteandroid.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeSheetFragment extends Fragment {
    Sheet lastSheet;
    ViewGroup view;
    TextView titleFragment;
    ListView listView;
    HomeSheetAdapter homeSheetAdapter;
    Boolean result = false;

    LottieAnimationView sheet_empty_animation,loader_pink;
    public HomeSheetFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_home_sheet, container, false);
        loader_pink = view.findViewById(R.id.loader_pink);
        loader_pink.setVisibility(View.VISIBLE);
        titleFragment = view.findViewById(R.id.title_sheet_home);
        listView = view.findViewById(R.id.list_sheet_home);
        sheet_empty_animation = view.findViewById(R.id.sheet_empty_animation);
        sheet_empty_animation.setVisibility(View.GONE);
        getLastSheet();
        //Log.d("fragment creato", "homesheet");



        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                loader_pink.setVisibility(View.GONE);
                if (result) {
                    titleFragment.setText("Il tuo allenamento");
                    List<String> names = lastSheet.getNamesExercises();

                    homeSheetAdapter = new HomeSheetAdapter(getContext(), names, lastSheet);
                    listView.setAdapter(homeSheetAdapter);


                } else {
                    titleFragment.setText("Non hai allenamenti!");
                    sheet_empty_animation.setVisibility(View.VISIBLE);
                }

            }
        }, 2500);



        return view;
    }

    public void getLastSheet() {
        JSONObject postData = new JSONObject();
        SessionManager sessionManager = new SessionManager(getContext());
        try {
            postData.put("user_id", sessionManager.getSession());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        Log.d("user id", sessionManager.getSession());
        String url = "https://myfitnote.herokuapp.com/sheets/get_last_sheet";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("fragment creato", response.toString());
                        try {
                            if (!response.getBoolean("error")) {
                                result = true;
                                JSONObject sheet = response.getJSONArray("result").getJSONObject(0);
                                String name = sheet.getString("name_sheet");
                                Log.d("nome scheda", name);
                                int numOfDays = sheet.getJSONArray("days").length();

                                List<String> days = new ArrayList<>();
                                for (int i = 0; i < numOfDays; i++) {
                                    days.add(sheet.getJSONArray("days").getString(i));

                                }
                                List<SheetExercise> sheetExercises = new ArrayList<>();
                                int dimensionExercises = sheet.getJSONArray("exercises").length();
                                for (int j = 0; j < dimensionExercises; j++) {
                                    String nameExercise = sheet.getJSONArray("exercises").getString(j);
                                    Log.d("nome esercizio", nameExercise);
                                    String rep = sheet.getJSONArray("reps").getString(j);
                                    String serie = sheet.getJSONArray("series").getString(j);
                                    SheetExercise sheetExercise = new SheetExercise(nameExercise, rep, serie);
                                    sheetExercises.add(sheetExercise);
                                }

                                String date = sheet.getString("date");
                                String id = sheet.getString("_id");
                                String splittedDate = date.substring(0, 10);
                                lastSheet = new Sheet(name, id, sheetExercises, days, splittedDate);
                            } else {
                                result = false;

                            }
                        } catch (JSONException ignored) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("fragment creato", error.toString());
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