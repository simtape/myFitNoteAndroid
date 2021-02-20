package com.example.myfitnoteandroid.ui.foods;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfitnoteandroid.R;

public class foodDetailsActivity extends AppCompatActivity {


    int position;
    String nameFood, kcalFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("food_position");
        nameFood = extras.getString("food");
        kcalFood = extras.getString("kcal");


    }
}