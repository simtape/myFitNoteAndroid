package com.example.myfitnoteandroid.ui.sheets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ShowSheetsFragment extends Fragment {

    //List<SheetExercise> sheetExerciseList = new ArrayList<>();
    List<String> days = new ArrayList<>();
    JSONArray jsonArrayResponse;
    ViewGroup root;
    TextView noSheets;
    ListView listView;
    SearchView searchView;
    ShowSheetsAdapter showSheetsAdapter;
    ProgressBar progress;
    Thread thread;
    ProgressDialog progressBar;
    Boolean thereAreSheets = true;
    LinearLayout layout;
    int progressCounter = 0;
    int img;
    LottieAnimationView empty_sheet_an;

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.getSheets();
        root = (ViewGroup) inflater.inflate(R.layout.show_sheets_fragment, container, false);
        listView = root.findViewById(R.id.list_view);

        searchView = root.findViewById(R.id.searchViewSheets);
        searchView.setVisibility(View.INVISIBLE);

        noSheets = root.findViewById(R.id.no_sheets);
        noSheets.setVisibility(View.INVISIBLE);

        empty_sheet_an = root.findViewById(R.id.empty_sheet_an);
        empty_sheet_an.setVisibility(View.GONE);

        layout = root.findViewById(R.id.tv_layout);

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Stiamo caricando le schede");
        progressBar.setProgressStyle(R.drawable.circle);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!thereAreSheets) {
                    noSheets.setVisibility(View.VISIBLE);
                    empty_sheet_an.setVisibility(View.VISIBLE);
                    progressBar.dismiss();
                } else {
                    layout.setVisibility(View.INVISIBLE);
                    List<String> dates = SheetsHandler.getInstance().datesList();
                    List<String> names = SheetsHandler.getInstance().namesList();
                    searchView.setVisibility(View.VISIBLE);
                    showSheetsAdapter = new ShowSheetsAdapter(getContext(), names, dates);
                    listView.setAdapter(showSheetsAdapter);
                    progressBar.dismiss();
                }


            }
        }, 1500);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        showSheetsAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), SheetDetailsActivity.class);
                intent.putExtra("sheet_position", position);
                startActivity(intent);


            }
        });

        return root;

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
                                String seriesString = seriesJsonArray.getString(j);

                                String reps = repsString;
                                String series = seriesString;

                                SheetExercise sheetExercise = new SheetExercise(exercise, reps, series);
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
                } catch (JSONException e) {


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