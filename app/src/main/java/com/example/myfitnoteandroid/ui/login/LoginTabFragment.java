package com.example.myfitnoteandroid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.MainActivity;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.User;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginTabFragment extends Fragment implements View.OnClickListener {

    EditText email;
    EditText password;
    Button login;
    float v = 0;
    private JSONObject postData, userJsonObject;
    List<SheetExercise> sheetExerciseList = new ArrayList<>();
    List<String> days = new ArrayList<>();
    JSONArray jsonArrayResponse;

    @Override
    public void onStart() {
        super.onStart();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        login = root.findViewById(R.id.login);

        email.setTranslationX(800);
        password.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        login.setAlpha(v);

        login.setOnClickListener(this);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }


    @Override
    public void onClick(View v) {
        postData = new JSONObject();
        if (v.getId() == login.getId()) {
            Log.d("PROVA", "ho cliccato sul tasto login");
            try {
                postData.put("mail", email.getText().toString().trim());
                postData.put("password", password.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            login();
        }

    }


    private void login() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/users/login";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //se response non è nullo, vuol dire che il login è andato a buon fine
                try {
                    userJsonObject = response.getJSONObject("user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //modifica inutile
                try {
                    if (userJsonObject != null) {


                        //setto l'oggetto user con il risultato della post
                        String name = userJsonObject.getString("name");
                        String surname = userJsonObject.getString("surname");
                        String mail = userJsonObject.getString("mail");
                        String id = userJsonObject.getString("_id");
                        User user = new User(name, surname, mail);
                        user.setId(id);
                        SessionManager sessionHandler = new SessionManager(getContext());
                        sessionHandler.saveSession(user);
                        System.out.println("sessione: " + sessionHandler.getSession());

                        //carico le schede di allenamento dell'utente
                        //getSheets();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().finish();
                        startActivity(intent);

                    } else {
                        switch (response.getString("message")) {
                            case "Utente sbagliato":
                                Toast.makeText(getContext(), "Utente sbagliato", Toast.LENGTH_LONG).show();
                                break;

                            case "Password sbagliata":
                                Toast.makeText(getContext(), "Password sbagliata", Toast.LENGTH_LONG).show();
                                break;
                        }
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

        /*jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

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


}


