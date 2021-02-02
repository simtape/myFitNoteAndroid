package com.example.myfitnoteandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LoginTabFragment extends Fragment implements View.OnClickListener {

    EditText username;
    EditText password;
    Button login;
    float v = 0;
    private JSONObject postData, userJsonObject;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        login = root.findViewById(R.id.login);

        /*username.setAlpha(v);
        password.setAlpha(v);
        login.setAlpha(v);*/

        login.setOnClickListener(this);

       /* username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();*/

        return root;
    }


    @Override
    public void onClick(View v) {
        postData = new JSONObject();
        if (v.getId() == login.getId()) {
            Log.d("PROVA", "ho cliccato sul tasto login");
            try {
                postData.put("mail", username.getText().toString().trim());
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
                try {
                    if (userJsonObject != null) {

                        //setto l'oggetto user con il risultato della post
                        String name = userJsonObject.getString("name");
                        String surname = userJsonObject.getString("surname");
                        String mail = userJsonObject.getString("mail");
                        User.getInstance().setCurrentUser(name, surname, mail);
                        
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        //finisce l'activity del login, l'utente non potrà più rivederla
                        getActivity().finish();
                        startActivity(intent);

                    }
                    else {
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
}


