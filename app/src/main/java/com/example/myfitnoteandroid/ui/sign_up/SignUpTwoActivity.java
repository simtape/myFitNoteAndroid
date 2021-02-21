package com.example.myfitnoteandroid.ui.sign_up;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignUpTwoActivity extends AppCompatActivity implements View.OnClickListener {


    Button button, datebtn;
    TextView datetxt, pesotext, altezzatext;
    Calendar c;
    ImageView arrow;
    DatePickerDialog dpd;
     Spinner spinPeso, spinAltezza;
     int i, k;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_two);

        pesotext = findViewById(R.id.Peso);
        altezzatext = findViewById(R.id.Altezza);
        button = findViewById(R.id.reg_parte2);
        datetxt = findViewById(R.id.Data_nascita);
        datebtn = findViewById(R.id.btn_dtn);

        spinPeso = findViewById(R.id.spinnerPeso);
        spinAltezza = findViewById(R.id.spinnerAltezza);

        button.setOnClickListener(this);
        datebtn.setOnClickListener(this);

        arrow = findViewById(R.id.back_btn);
        arrow.setOnClickListener(this);

        List<String> peso = new ArrayList<>();
        peso.add("- scegli il tuo peso -");
        for (i = 40; i <= 170; i++){
            String stringpeso = String.valueOf(i);
            peso.add( stringpeso);

        }

        List<String> altezza = new ArrayList<>();
        altezza.add("- scegli la tua altezza -");
        for (k = 100; k <= 210; k++){
            String stringaltezza = String.valueOf(k);
            altezza.add( stringaltezza);

        }
        ArrayAdapter<String> pesoAdapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item, peso );
        pesoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPeso.setAdapter(pesoAdapter);
        spinPeso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yourPeso = spinPeso.getSelectedItem().toString();
                if(!yourPeso.equals("- scegli il tuo peso -")){
                    pesotext.setText(yourPeso);
                } else {
                    pesotext.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> altezzaAdapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item, altezza );
        altezzaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAltezza.setAdapter(altezzaAdapter);
        spinAltezza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yourAltezza = spinAltezza.getSelectedItem().toString();
                if(!yourAltezza.equals("- scegli la tua altezza -")){
                    altezzatext.setText(yourAltezza);
                } else {
                    altezzatext.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.reg_parte2:
                String date = datetxt.getText().toString();
                String peso = pesotext.getText().toString();
                String altezza = altezzatext.getText().toString();
                if (date.trim().isEmpty() || peso.trim().isEmpty() || altezza.trim().isEmpty()) {
                    Toast.makeText(this, "Non hai compilato tutti i campi", Toast.LENGTH_LONG).show();
                }else {
                    SessionManager sessionManager = new SessionManager(this);
                    sessionManager.setPeso(pesotext.getText().toString());
                    sessionManager.setAltezza(altezzatext.getText().toString());
                    sessionManager.setData(datetxt.getText().toString());
                    Intent intent = new Intent(this, SignUpThreeActivity.class);
                    startActivity(intent);
                }
                    break;


            case R.id.btn_dtn:
                c  = Calendar.getInstance();
                int  day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(SignUpTwoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        datetxt.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month,year);
                dpd.show();
            break;

            case R.id.back_btn:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                break;

        }


    }
}