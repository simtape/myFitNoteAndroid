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
    ListView listView;
    SearchView searchView;
    ShowSheetsAdapter showSheetsAdapter;
    ProgressBar progress;
    Thread thread;
    ProgressDialog progressBar;
    int progressCounter = 0;
    int img;


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
        /*progress = root.findViewById(R.id.circleb);
        progress.setVisibility(View.VISIBLE);
        //setProgressValue(progressCounter);
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Stiamo caricando le schede");
        progressBar.setProgressStyle(R.drawable.circle);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        //progressBarStatus = 0;


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                List<String> dates = SheetsHandler.getInstance().datesList();
                List<String> names = SheetsHandler.getInstance().namesList();
                //progress.setVisibility(View.INVISIBLE);
                showSheetsAdapter = new ShowSheetsAdapter(getContext(), names, dates);
                listView.setAdapter(showSheetsAdapter);
                progressBar.dismiss();
                //thread.interrupt();

         /*       for(int i = 0; i<SheetsHandler.getInstance().getUserSheets().size(); i++){
                    Sheet sheet = SheetsHandler.getInstance().getUserSheets().get(i);
                    Log.d("SCHEDA NUMERO", String.valueOf(i));
                    Log.d("nome", sheet.getName());
                    Log.d("esercizi", sheet.getNamesExercises().toString());


                }*/

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
                Bundle bundle = new Bundle();
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
                    jsonArrayResponse = response.getJSONArray("sheet");
                    if (jsonArrayResponse.length() == 0) {

                    } else {

                        for (int i = 0; i < jsonArrayResponse.length(); i++) {
                            List<SheetExercise> sheetExerciseList = new ArrayList<>();
                            //sheetExerciseList.clear();
                            JSONObject jsonObject = jsonArrayResponse.getJSONObject(i);
                            Log.d("scheda", jsonObject.toString());

                            for (int j = 0; j < jsonObject.getJSONArray("exercises").length(); j++) {

                                JSONArray exerciseJsonArray = jsonObject.getJSONArray("exercises");
                                JSONArray repsJsonArray = jsonObject.getJSONArray("reps");
                                JSONArray seriesJsonArray = jsonObject.getJSONArray("series");

                                String exercise = exerciseJsonArray.getString(j);
                                String repsString = repsJsonArray.getString(j);
                                String seriesString = seriesJsonArray.getString(j);
                               /* Log.d("SCHEDA NOME ", jsonObject.getString("name_sheet"));
                                Log.d("reps", repsString);
                                Log.d("esercizio", exercise);*/
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
                            List<String>daysNew = new ArrayList<>();
                            for (int k = 0; k < daysJsonArray.length(); k++) {
                                days.add(daysJsonArray.getString(k));
                                daysNew.add(daysJsonArray.getString(k));
                                Log.d("Giorno", daysNew.get(k));

                            }

                            Sheet sheet = new Sheet(name, id, sheetExerciseList, daysNew, splittedDate);
                            for (int m = 0; m < sheetExerciseList.size(); m++) {
                                Log.d("esercizi", sheetExerciseList.get(m).getNameExercise());

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