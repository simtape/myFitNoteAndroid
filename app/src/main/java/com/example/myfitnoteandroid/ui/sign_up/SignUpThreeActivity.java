package com.example.myfitnoteandroid.ui.sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpThreeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView label, name, surname, mail, password, peso, altezza, dataN;
    ImageButton confirmButton, cancelButton;


    private JSONObject postData, userJsonObject;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity_three);

        label = findViewById(R.id.i_tuoi_dati);
        name = findViewById(R.id.nome);
        surname = findViewById(R.id.Cognome);
        mail = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        peso = findViewById(R.id.Peso);
        altezza = findViewById(R.id.Altezza);
        dataN = findViewById(R.id.Datan);
        confirmButton = findViewById(R.id.register_btn);
        cancelButton = findViewById(R.id.cancel_btn);



        SessionManager sessionManager = new SessionManager(this);

        label.setText("I tuoi dati");
        name.setText(sessionManager.getName());
        surname.setText(sessionManager.getSurname());
        mail.setText(sessionManager.getMail());
        password.setText(sessionManager.getPassword());
        peso.setText(sessionManager.getPeso());
        altezza.setText(sessionManager.getAltezza());
        dataN.setText(sessionManager.getData());

        confirmButton.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v) {

        switch(v.getId()){
            case R.id.register_btn:
                postData = new JSONObject();

                try {

                    postData.put("mail", mail.getText().toString().trim());
                    postData.put("name", name.getText().toString().trim());
                    postData.put("surname", surname.getText().toString().trim());
                    postData.put("height", altezza.getText().toString().trim());
                    postData.put("weight", peso.getText().toString().trim());
                    postData.put("date", dataN.getText().toString().trim());
                    postData.put("password", password.getText().toString().trim());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                registrazione();
                break;

            case R.id.cancel_btn:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();

        }

/*

        if (v.getId() == confirmButton.getId()) {
            postData = new JSONObject();

            try {

                postData.put("mail", mail.getText().toString().trim());
                postData.put("name", name.getText().toString().trim());
                postData.put("surname", surname.getText().toString().trim());
                postData.put("height", altezza.getText().toString().trim());
                postData.put("weight", peso.getText().toString().trim());
                postData.put("date", dataN.getText().toString().trim());
                postData.put("password", password.getText().toString().trim());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            registrazione();

        }
*/


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
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);



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
            public void retry(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);


    }
}