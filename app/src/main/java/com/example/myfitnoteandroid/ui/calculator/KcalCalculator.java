package com.example.myfitnoteandroid.ui.calculator;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KcalCalculator extends Fragment implements View.OnClickListener {


    ViewGroup view;
    List<AutoCompleteTextView> foodsACList = new ArrayList<>();
    List<EditText> gramsACList = new ArrayList<>();

    AutoCompleteTextView food, grams;
    Button addButton, calculateBtn;
    LayoutInflater layoutInflater;
    ViewGroup container;
    String[] foods;
    List<Integer> kcal;
    ArrayAdapter<String> adapter;
    TextView totKcal;
    LinearLayout rowsLayout;
    int tot = 0;


    public static KcalCalculator newInstance() {
        return new KcalCalculator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getFoods();
        view = (ViewGroup) inflater.inflate(R.layout.kcal_calculator_fragment, container, false);


//        grams = view.findViewById(R.id.quantity);

        //view.addView(R.layout.calculate_kcal_row);
        layoutInflater = inflater;
        this.container = container;

        rowsLayout = view.findViewById(R.id.rows_layout);

        View row = inflater.inflate(R.layout.calculate_kcal_row, container, false);
        rowsLayout.addView(row);

        AutoCompleteTextView food = row.findViewById(R.id.food_name);
        EditText gramValue = row.findViewById(R.id.quantity);


        totKcal = view.findViewById(R.id.newKcal);
        totKcal.setText(String.valueOf(tot));

        calculateBtn = view.findViewById(R.id.calculate_button);
        calculateBtn.setOnClickListener(this);
        foodsACList.add(food);
        gramsACList.add(gramValue);

        addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new ArrayAdapter<>(getContext(),
                        R.layout.custom_list_advices_foods, R.id.text_view_list_item, foods);
                foodsACList.get(0).setAdapter(adapter);

            }
        }, 2000);


        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addButton.getId()) {
            addNewRow();
            Log.d("bottone", "funziona");


        }
        if (v.getId() == calculateBtn.getId()) {

            calculateKcal();

        }
    }

    private void calculateKcal() {

        List<Integer> kcalForGrams = new ArrayList<>();
        Boolean isEmpty = false, allEquals = true;
        Boolean[] equalWords = new Boolean[foodsACList.size()];
        tot = 0;

        for (int q = 0; q < foodsACList.size(); q++) {
            equalWords[q] = false;
        }

        for (int i = 0; i < foods.length; i++) {

            int j = 0;
            Log.d("dimensione foods", String.valueOf(foodsACList.size()));
            for (AutoCompleteTextView food : foodsACList) {

                if (food.getText().toString().toLowerCase().equals(foods[i]) &&
                        !food.getText().toString().isEmpty() &&
                        !gramsACList.get(j).getText().toString().isEmpty()) {

                    int value = kcal.get(i) * (Integer.valueOf(gramsACList.get(j).getText().toString()))
                            / 100;
                    kcalForGrams.add(value);
                    equalWords[j] = true;


                } else if (food.getText().toString().isEmpty() || gramsACList.get(j).getText().toString().isEmpty()) {
                    isEmpty = true;

                }

            }

            j++;
        }
 /* for (int result : kcalForGrams) {
            tot = result + tot;

        }*/

        for (int l = 0; l < equalWords.length; l++) {
            if (!equalWords[l])
                allEquals = false;
            if(!kcalForGrams.isEmpty())
            tot = kcalForGrams.get(l) + tot;
        }

        if (isEmpty) {
            Toast.makeText(this.getContext(), "Devi riempire tutti i campi!", Toast.LENGTH_LONG).show();

        } else if (!allEquals) {
            Toast.makeText(this.getContext(), "Il cibo inserito non è present nel db!", Toast.LENGTH_LONG).show();

        }
        totKcal.setText(String.valueOf(tot));
    }

    private void findKcal() {


    }

    private void addNewRow() {


        View row = this.layoutInflater.inflate(R.layout.calculate_kcal_row, container, false);
        //view.addView(row);
        rowsLayout.addView(row);

        AutoCompleteTextView food = row.findViewById(R.id.food_name);
        foodsACList.add(food);
        foodsACList.get(foodsACList.size() - 1).setAdapter(adapter);

        EditText gramValue = row.findViewById(R.id.quantity);
        gramsACList.add(gramValue);

    }


    private void getFoods() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/aliments/get_aliments";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("aliments");
                            foods = new String[jsonArray.length()];
                            kcal = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject alimenti = jsonArray.getJSONObject(i);

                                String nomeAlimento = alimenti.getString("nome");
                                int kcalAlimento = alimenti.getInt("kcal");

                                //exercisesView.append(nameExercise + ", " + gearExercise + "\n\n");

                                foods[i] = nomeAlimento.toLowerCase();
                                Log.d("food", foods[i]);
                                kcal.add(kcalAlimento);

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
