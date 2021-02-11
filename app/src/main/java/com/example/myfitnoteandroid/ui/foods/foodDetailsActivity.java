package com.example.myfitnoteandroid.ui.foods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myfitnoteandroid.R;

public class foodDetailsActivity extends AppCompatActivity {


    int position;
    String nameFood, kcalFood;
    TextView foodTxt, kcalTxt;

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