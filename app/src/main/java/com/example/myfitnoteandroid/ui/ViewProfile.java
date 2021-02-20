package com.example.myfitnoteandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

public class ViewProfile extends AppCompatActivity {

    TextView mailTxt;
    TextView dateTxt;
    TextView weightTxt;
    TextView heightTxt;
    TextView nameTxt;
    TextView surnameTxt;
    Button button;

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
        button = findViewById(R.id.updateProfilebutton);


        SessionManager sessionManager = new SessionManager(this);

        nameTxt.setText(sessionManager.getName());
        surnameTxt.setText(sessionManager.getSurname());
        mailTxt.setText(sessionManager.getMail());
        dateTxt.setText(sessionManager.getData());
        weightTxt.setText(sessionManager.getPeso());
        heightTxt.setText(sessionManager.getAltezza());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfile.this, UpdateProfile.class);
                startActivity(intent);
            }
        });

    }

}