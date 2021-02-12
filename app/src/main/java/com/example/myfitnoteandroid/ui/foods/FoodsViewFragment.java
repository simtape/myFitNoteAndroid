package com.example.myfitnoteandroid.ui.foods;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
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
import com.example.myfitnoteandroid.ui.exercises.ShowExercisesAdapter;
import com.example.myfitnoteandroid.ui.exercises.exerciseDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodsViewFragment extends Fragment {

    private FoodsViewModel galleryViewModel;
    private List<String> nameFoods = new ArrayList<>();
    private List<Integer> nameKcal = new ArrayList<>();
    ListView listView;
    SearchView searchViewfood;
    ShowFoodAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.getFood();
        View root = inflater.inflate(R.layout.foods_view_fragment, container, false);
        listView = root.findViewById(R.id.list_viewFood);
        searchViewfood = root.findViewById(R.id.searchView2);

        searchViewfood.setOnQueryTextListener(
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
                adapter = new ShowFoodAdapter(getContext(), nameFoods, nameKcal);
                listView.setAdapter(adapter);
            }
        }, 1500);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), foodDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtra("food_position", position);
//                intent.putExtra("food", nameFoods.get(position));
//                intent.putExtra("kcal", nameKcal.get(position));
//
//                startActivity(intent);
//            }


        return root;
    }


    private void  getFood(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/aliments/get_aliments";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("aliments");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject alimenti = jsonArray.getJSONObject(i);

                                String nomeAlimento = alimenti.getString("nome");
                                int kcalAlimento = alimenti.getInt("kcal");

                                //exercisesView.append(nameExercise + ", " + gearExercise + "\n\n");

                                nameFoods.add(nomeAlimento);
                                Log.d("nome cibo", nameFoods.get(i));
                                nameKcal.add(kcalAlimento);
                                Log.d("nome kcal", String.valueOf(nameKcal.get(i)));
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
