package com.example.myfitnoteandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrazioneParteTreActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name, surname, mail, password, peso, altezza;
    Button button;

    private JSONObject postData, userJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_parte_tre);

        name = findViewById(R.id.nome);
        surname = findViewById(R.id.Cognome);
        mail = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        peso = findViewById(R.id.Peso);
        altezza = findViewById(R.id.Altezza);
        button = findViewById(R.id.reg3);


        SessionManager sessionManager = new SessionManager(this);

        name.setText(sessionManager.getName());
        surname.setText(sessionManager.getSurname());
        mail.setText(sessionManager.getMail());
        password.setText(sessionManager.getPassword());
        peso.setText(sessionManager.getPeso());
        altezza.setText(sessionManager.getAltezza());

        button.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v) {
        if (v.getId() == button.getId()) {
            postData = new JSONObject();

            try {

                postData.put("mail", mail.getText().toString().trim());
                postData.put("name", name.getText().toString().trim());
                postData.put("surname", surname.getText().toString().trim());
                postData.put("height", altezza.getText().toString().trim());
                postData.put("weight", peso.getText().toString().trim());
                postData.put("password", password.getText().toString().trim());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            registrazione();

        }


    }

    public void registrazione() {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/users/registrazione";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    userJsonObject = response.getJSONObject("user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (userJsonObject != null) {
                    Toast.makeText(getApplication().getBaseContext(), "Utente registrato correttamente", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplication().getBaseContext(), "Email gia esistente", Toast.LENGTH_LONG).show();
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