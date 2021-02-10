package com.example.myfitnoteandroid.ui.sheets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myfitnoteandroid.R;

public class SheetDetailsActivity extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("sheet_position");
        Log.d("posizione activity:", String.valueOf(position));
    }
}