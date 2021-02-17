package com.example.myfitnoteandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.example.myfitnoteandroid.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener {

    EditText nameEdit, surnameEdit, dtnEdit, heightEdit, weightEdit;
    Button btnUpdate;
    private JSONObject postData, userJsonObject;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        nameEdit = findViewById(R.id.nomeEdit);
        surnameEdit = findViewById(R.id.cognomeEdit);
        dtnEdit = findViewById(R.id.datadiNascitaEdit);
        heightEdit = findViewById(R.id.PesoEdit);
        weightEdit = findViewById(R.id.altezzaEdit);
        btnUpdate = findViewById(R.id.bottoneUpdate);
        btnUpdate.setOnClickListener(this);
        SessionManager sessionManager = new SessionManager(this);

        nameEdit.setText(sessionManager.getName());
        surnameEdit.setText(sessionManager.getSurname());
        dtnEdit.setText(sessionManager.getData());
        heightEdit.setText(sessionManager.getAltezza());
        weightEdit.setText(sessionManager.getPeso());


    }

    public void onClick(View v) {
        if (v.getId() == btnUpdate.getId()) {
            SessionManager sessionManager = new SessionManager(this);
            postData = new JSONObject();
            try {

                postData.put("mail", sessionManager.getMail());
                postData.put("name", nameEdit.getText().toString().trim());
                postData.put("surname", surnameEdit.getText().toString().trim());
                postData.put("height", heightEdit.getText().toString().trim());
                postData.put("weight", weightEdit.getText().toString().trim());
                postData.put("date", dtnEdit.getText().toString().trim());
                Log.d("SIUUUUM", postData.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateProfile();
            sessionManager.setSessionName(nameEdit.getText().toString());
            sessionManager.setSessionSurname(surnameEdit.getText().toString());
            sessionManager.setAltezza(heightEdit.getText().toString());
            sessionManager.setPeso(weightEdit.getText().toString());
            sessionManager.setData(dtnEdit.getText().toString());
            Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.finish();
            startActivity(intent);
        }

    }


    public void updateProfile(){
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url = "https://myfitnote.herokuapp.com/users/update_user";
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
                    Toast.makeText(getApplication().getBaseContext(), "Utente modificato correttamente", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplication().getBaseContext(), "Nessuna modifica esegiuta", Toast.LENGTH_LONG).show();
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