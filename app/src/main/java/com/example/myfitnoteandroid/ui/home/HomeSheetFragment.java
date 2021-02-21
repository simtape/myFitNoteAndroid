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
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;

import org.json.JSONArray;
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

    LottieAnimationView sheet_empty_animation, loader_pink;
    private boolean thereAreSheets;
    private JSONArray jsonArrayResponse;
    List<String> days = new ArrayList<>();

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
        //getLastSheet();
        //Log.d("fragment creato", "homesheet");
        getSheets();


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                loader_pink.setVisibility(View.GONE);
              /*  if (result) {
                    titleFragment.setText("Il tuo allenamento");
                    List<String> names = lastSheet.getNamesExercises();

                    homeSheetAdapter = new HomeSheetAdapter(getContext(), names, lastSheet);
                    listView.setAdapter(homeSheetAdapter);


                } else {
                    titleFragment.setText("Non hai allenamenti!");
                    sheet_empty_animation.setVisibility(View.VISIBLE);
                }*/
                SessionManager sessionManager = new SessionManager(getContext());
                int posFav = sessionManager.getFavouriteSheet();
                if (posFav == -1) {
                    titleFragment.setText("Non hai un allenamento preferito!");
                    sheet_empty_animation.setVisibility(View.VISIBLE);

                } else {
                    setSheetToShow();
                    titleFragment.setText("Il tuo allenamento preferito");
                    List<String> names = lastSheet.getNamesExercises();

                    homeSheetAdapter = new HomeSheetAdapter(getContext(), names, lastSheet);
                    listView.setAdapter(homeSheetAdapter);


                }


            }
        }, 3000);


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

    private void setSheetToShow() {
        SessionManager sessionManager = new SessionManager(getContext());
        int positionFav = sessionManager.getFavouriteSheet();
        this.lastSheet = SheetsHandler.getInstance().getUserSheets().get(positionFav);


    }

    private void getSheets() {
        SheetsHandler.getInstance().resetSheetsHandler();
        SessionManager sessionManager = new SessionManager(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/sheets/get_sheets?user_id=";
        String conc_url = url.concat(sessionManager.getSession());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, conc_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.getBoolean("error")) {
                        thereAreSheets = false;

                    } else {

                        jsonArrayResponse = response.getJSONArray("sheet");

                        for (int i = 0; i < jsonArrayResponse.length(); i++) {
                            List<SheetExercise> sheetExerciseList = new ArrayList<>();
                            JSONObject jsonObject = jsonArrayResponse.getJSONObject(i);


                            for (int j = 0; j < jsonObject.getJSONArray("exercises").length(); j++) {

                                JSONArray exerciseJsonArray = jsonObject.getJSONArray("exercises");
                                JSONArray repsJsonArray = jsonObject.getJSONArray("reps");
                                JSONArray seriesJsonArray = jsonObject.getJSONArray("series");

                                String exercise = exerciseJsonArray.getString(j);
                                String repsString = repsJsonArray.getString(j);

                                String series = seriesJsonArray.getString(j);

                                SheetExercise sheetExercise = new SheetExercise(exercise, repsString, series);
                                sheetExerciseList.add(sheetExercise);

                            }

                            String name = jsonObject.getString("name_sheet");
                            String id = jsonObject.getString("_id");
                            String date = jsonObject.getString("date");
                            String splittedDate = date.substring(0, 10);
                            JSONArray daysJsonArray = jsonObject.getJSONArray("days");
                            days.clear();
                            List<String> daysNew = new ArrayList<>();
                            for (int k = 0; k < daysJsonArray.length(); k++) {
                                days.add(daysJsonArray.getString(k));
                                daysNew.add(daysJsonArray.getString(k));


                            }

                            Sheet sheet = new Sheet(name, id, sheetExerciseList, daysNew, splittedDate);
                            for (int m = 0; m < sheetExerciseList.size(); m++) {
                            }

                            SheetsHandler.getInstance().addSheet(sheet);
                        }
                    }
                } catch (JSONException ignored) {


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(jsonObjectRequest);


    }


}