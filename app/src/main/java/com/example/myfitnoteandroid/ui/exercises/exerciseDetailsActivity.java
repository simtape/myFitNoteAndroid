package com.example.myfitnoteandroid.ui.exercises;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfitnoteandroid.R;

public class exerciseDetailsActivity extends AppCompatActivity {
    int position;
    String nameExercise, gearExercise;
    TextView esercizioTxt, attrezzoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("exe_position");
        nameExercise = extras.getString("exe");
        gearExercise = extras.getString("gear");


        Log.d("esercizioooo activity", String.valueOf(nameExercise));
        Log.d("exe_position", String.valueOf(position));


        esercizioTxt = findViewById(R.id.nomeEsercizio);
        attrezzoTxt = findViewById(R.id.attrezziEsercizio);
        esercizioTxt.setText(nameExercise);
        attrezzoTxt.setText(gearExercise);

    }
}