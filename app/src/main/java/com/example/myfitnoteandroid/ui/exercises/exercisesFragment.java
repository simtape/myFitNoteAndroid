package com.example.myfitnoteandroid.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;
import com.example.myfitnoteandroid.ui.sheets.SheetDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class exercisesFragment extends Fragment {
    ListView listView;
    List<String> nameExercises = new ArrayList<>();
    List<String> nameGear = new ArrayList<>();

    private exercisesViewModel slideshowViewModel;


    @Override
    public void onStart() {
        super.onStart();
        //this.getExercises();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.getExercises();
        View root = inflater.inflate(R.layout.exercises_fragment, container, false);

        listView = root.findViewById(R.id.list_viewExe);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShowExercisesAdapter adapter;
                adapter = new ShowExercisesAdapter(getContext(), nameExercises, nameGear);
                listView.setAdapter(adapter);
            }
        }, 1500);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), exerciseDetailsActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("exe_position", position);
                startActivity(intent);
            }
        });


        return root;
    }

    private void getExercises() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/esercizi_2/get";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("exercises");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject esercizio = jsonArray.getJSONObject(i);

                                String nameExercise = esercizio.getString("nome");
                                String gearExercise = esercizio.getString("attrezzi");

                                //exercisesView.append(nameExercise + ", " + gearExercise + "\n\n");

                                nameExercises.add(nameExercise);
                                Log.d("nome esercizio", nameExercises.get(i));
                                nameGear.add(gearExercise);
                                Log.d("nome attrezzo", nameGear.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
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