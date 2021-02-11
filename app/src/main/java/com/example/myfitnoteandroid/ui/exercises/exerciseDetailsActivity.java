package com.example.myfitnoteandroid.ui.exercises;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myfitnoteandroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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