package com.example.myfitnoteandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

public class ViewProfile extends AppCompatActivity {

    TextView mailTxt, dateTxt, weightTxt, heightTxt, sheetsTxt, nameTxt, surnameTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mailTxt = findViewById(R.id.mailTxt);
        nameTxt = findViewById(R.id.textViewname);
        surnameTxt = findViewById(R.id.textViewSurname);
        dateTxt = findViewById(R.id.dataTxt);
        weightTxt = findViewById(R.id.pesotxt);
        heightTxt = findViewById(R.id.altezzaTxt);
        sheetsTxt = findViewById(R.id.schedeTxt);
        SessionManager sessionManager = new SessionManager(this);

        nameTxt.setText(sessionManager.getName());
        surnameTxt.setText(sessionManager.getSurname());
        mailTxt.setText(sessionManager.getMail());
        dateTxt.setText(sessionManager.getData());
        weightTxt.setText(sessionManager.getPeso());
        heightTxt.setText(sessionManager.getAltezza());

    }
}