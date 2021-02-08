package com.example.myfitnoteandroid.ui.sheets;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.List;


public class ShowSheetsFragment extends Fragment {

    List<SheetExercise> sheetExerciseList = new ArrayList<>();
    List<String> days = new ArrayList<>();
    JSONArray jsonArrayResponse;
    ViewGroup root;


    @Override
    public void onStart() {
        super.onStart();

        this.getSheets();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         root = (ViewGroup) inflater.inflate(R.layout.show_sheets_fragment, container, false);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout linearLayout = root.findViewById(R.id.linear_layout);
                List<TextView>textViews = createSheetLabes();
                for(TextView textView: textViews)
                    linearLayout.addView(textView);

            }
        }, 1500);

        return root;

    }

    private void getSheets() {
        SheetsHandler.getInstance().resetSheetsHandler();
        SessionManager sessionManager = new SessionManager(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/sheets/get_sheets?user_id=";
        String conc_url = url.concat(sessionManager.getSession());
        Log.d("id sessione", sessionManager.getSession());
        Log.d("prova", conc_url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, conc_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    jsonArrayResponse = response.getJSONArray("sheet");
                    if (jsonArrayResponse.length() == 0) {
                        Log.d("messaggio", "l'utente non ha inserito schede");
                    } else {
                        for (int i = 0; i < jsonArrayResponse.length(); i++) {

                            JSONObject jsonObject = jsonArrayResponse.getJSONObject(i);
                            for (int j = 0; j < jsonObject.getJSONArray("exercises").length(); j++) {
                                JSONArray exerciseJsonArray = jsonObject.getJSONArray("exercises");
                                JSONArray repsJsonArray = jsonObject.getJSONArray("reps");
                                JSONArray seriesJsonArray = jsonObject.getJSONArray("series");

                                String exercise = exerciseJsonArray.getString(j);
                                String repsString = repsJsonArray.getString(j);
                                String seriesString = seriesJsonArray.getString(j);

                                int reps = Integer.valueOf(repsString);
                                int series = Integer.valueOf(seriesString);

                                SheetExercise sheetExercise = new SheetExercise(exercise, reps, series);
                                sheetExerciseList.add(sheetExercise);
                            }

                            String name = jsonObject.getString("name_sheet");
                            String id = jsonObject.getString("_id");

                            JSONArray daysJsonArray = jsonObject.getJSONArray("days");

                            for (int k = 0; k < daysJsonArray.length(); k++) {
                                days.add(daysJsonArray.getString(k));

                            }
                            Sheet sheet = new Sheet(name, id, sheetExerciseList, days);
                            Log.d("nome scheda:", sheet.getName());
                            Log.d("id scheda: ", sheet.getId());

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

    private List<TextView> createSheetLabes(){
        int textViewSize = SheetsHandler.getInstance().getUserSheets().size();
        Log.d("nome scheda f csl", String.valueOf(textViewSize));
        List<TextView> textViews = new ArrayList<>();
        for(int i = 0; i<textViewSize; i++){
            TextView textView = new TextView(getContext());
           /* textViews[i] = new TextView(getContext());
            textViews[i].setText(SheetsHandler.getInstance().getUserSheets().get(i).getName());*/
            textView.setText(SheetsHandler.getInstance().getUserSheets().get(i).getName());
            textViews.add(textView);
            Log.d("scheda", i + SheetsHandler.getInstance().getUserSheets().get(i).getName());
        }
        return textViews;
    }

}