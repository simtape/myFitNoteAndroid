package com.example.myfitnoteandroid.ui.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myfitnoteandroid.R;

public class exerciseDetailsActivity extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("exe_position");
    }
}