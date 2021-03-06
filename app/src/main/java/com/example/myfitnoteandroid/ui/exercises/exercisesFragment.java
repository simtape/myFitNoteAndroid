package com.example.myfitnoteandroid.ui.exercises;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class exercisesFragment extends Fragment {
    ListView listView;
    private final List<String> nameExercises = new ArrayList<>();
    private final List<String> nameGear = new ArrayList<>();
    SearchView searchViewExe;
    ShowExercisesAdapter adapter;
    ProgressDialog progressBar;




    @Override
    public void onStart() {
        super.onStart();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.getExercises();
        View root = inflater.inflate(R.layout.exercises_fragment, container, false);

        listView = root.findViewById(R.id.list_viewExe);
        searchViewExe = root.findViewById(R.id.searchViewexe);
        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Stiamo caricando gli esercizi");
        progressBar.setProgressStyle(R.drawable.circle);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        searchViewExe.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new ShowExercisesAdapter(getContext(), nameExercises, nameGear);
                listView.setAdapter(adapter);
                progressBar.dismiss();

            }
        }, 1500);

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), exerciseDetailsActivity.class);
                intent.putExtra("exe_position", position);
                intent.putExtra("exe", nameExercises.get(position));
                intent.putExtra("gear", nameGear.get(position));

                startActivity(intent);
            }
        });*/


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
            public void retry(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
}