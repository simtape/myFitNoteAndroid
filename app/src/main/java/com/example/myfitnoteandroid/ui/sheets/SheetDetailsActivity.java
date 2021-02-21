package com.example.myfitnoteandroid.ui.sheets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;

import java.util.List;

public class SheetDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    int position;
    ListView listView;
    TextView nameSheet, dateSheet, trainingDays;
    ImageButton favouriteSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("sheet_position");


        //Log.d("posizione activity:", String.valueOf(position));

        Sheet sheet = SheetsHandler.getInstance().getUserSheets().get(position);
        // Log.d("giorni", sheet.getDays().toString());
        List<String> days = sheet.getDays();
        //Log.d("dimensione esercizi", String.valueOf(sheet.getSheetExercises().size()));
        listView = findViewById(R.id.list_view_details);
        nameSheet = findViewById(R.id.name_sheetTV);
        dateSheet = findViewById(R.id.date_tv);
        trainingDays = findViewById(R.id.training_days);
        favouriteSheet = findViewById(R.id.favourite_btn);
        favouriteSheet.setOnClickListener(this);

        nameSheet.setText(sheet.getName());
        dateSheet.setText(sheet.getDate());
        for (int i = 0; i < days.size(); i++) {
            trainingDays.append(days.get(i) + " ");

        }


        String[] objs = new String[sheet.getSheetExercises().size()];

        for (int i = 0; i < sheet.getSheetExercises().size(); i++) {

            objs[i] = sheet.getSheetExercises().get(i).getNameExercise();
        }
        Log.d("esercizi: ", sheet.getNamesExercises().toString());
        SheetDetailsAdapter sheetDetailsAdapter;
        sheetDetailsAdapter = new SheetDetailsAdapter(this, objs, sheet);
        listView.setAdapter(sheetDetailsAdapter);
        controlFavouriteOnCreate();
    }

    private void setFavouriteSheet() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        controlFavouriteOnClick();
        if (position == sessionManager.getFavouriteSheet()) {
            sessionManager.removeFavourite();

        } else {
            sessionManager.setFavouriteSheet(position);
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == favouriteSheet.getId())
            setFavouriteSheet();
    }

    private void controlFavouriteOnCreate(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (position == sessionManager.getFavouriteSheet()) {
            favouriteSheet.setBackgroundResource(R.drawable.ic_full_star);

        } else {
            favouriteSheet.setBackgroundResource(R.drawable.ic_empty_fav);
        }



    }

    private void controlFavouriteOnClick(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (position == sessionManager.getFavouriteSheet()) {
            favouriteSheet.setBackgroundResource(R.drawable.ic_empty_fav);

        } else {

            favouriteSheet.setBackgroundResource(R.drawable.ic_full_star);
        }



    }
}