package com.example.myfitnoteandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfitnoteandroid.data.SessionManager;

import java.util.Calendar;

public class RegistrazioneParteDueActivity extends AppCompatActivity implements View.OnClickListener {

    EditText peso, altezza;
    Button button, datebtn;
    TextView datetxt;
    Calendar c;
    DatePickerDialog dpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_parte_2);

        peso = findViewById(R.id.Peso);
        altezza = findViewById(R.id.Altezza);
        button = findViewById(R.id.reg_parte2);
        datetxt = findViewById(R.id.Data_nascita);
        datebtn = findViewById(R.id.btn_dtn);

        button.setOnClickListener(this);
        datebtn.setOnClickListener(this);






    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.reg_parte2:
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.setPeso(peso.getText().toString());
            sessionManager.setAltezza(altezza.getText().toString());
            Intent intent = new Intent(this, RegistrazioneParteTreActivity.class);
            startActivity(intent);
            break;
            case R.id.btn_dtn:
                c  = Calendar.getInstance();
                int  day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(RegistrazioneParteDueActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        datetxt.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month,year);
                dpd.show();
            break;

        }


    }
}