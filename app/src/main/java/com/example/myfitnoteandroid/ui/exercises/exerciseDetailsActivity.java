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
    TextView esercizioTxt, attrezoTxt;

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        esercizioTxt.setText(nameExercise);
        attrezoTxt.setText(gearExercise);
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("exe_position");
        nameExercise = extras.getString("exe");
        gearExercise = extras.getString("gear");

        esercizioTxt.findViewById(R.id.nomeEsercizio);
        attrezoTxt = findViewById(R.id.attrezziEsercizio);



//        Log.d("esercizioooo activity", String.valueOf(nameExercise));
//        Log.d("exe_position", String.valueOf(position));

    }
}