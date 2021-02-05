package com.example.myfitnoteandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfitnoteandroid.data.SessionManager;

public class RegistrazioneParteDueActivity extends AppCompatActivity implements View.OnClickListener {

    EditText peso, altezza;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_parte_2);

        peso = findViewById(R.id.Peso);
        altezza = findViewById(R.id.Altezza);
        button = findViewById(R.id.reg_parte2);

        button.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == button.getId()){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.setPeso(peso.getText().toString());
            sessionManager.setAltezza(altezza.getText().toString());
            Intent intent = new Intent(this, RegistrazioneParteTreActivity.class);
            startActivity(intent);
        }


    }
}