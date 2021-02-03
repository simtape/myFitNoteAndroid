package com.example.myfitnoteandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.myfitnoteandroid.LoginActivity;
import com.example.myfitnoteandroid.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView background, logo;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //background = findViewById(R.id.bg_splash);
        //logo = findViewById(R.id.logo_splash);

        intent = new Intent(this, LoginActivity.class);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);
    }



}