package com.example.myfitnoteandroid.ui.sheets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;
import com.example.myfitnoteandroid.data.sheets_data.SheetsHandler;

public class SheetDetailsActivity extends AppCompatActivity {
    int position;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_details);
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("sheet_position");


        Log.d("posizione activity:", String.valueOf(position));

        Sheet sheet = SheetsHandler.getInstance().getUserSheets().get(position);
        Log.d("dimensione esercizi", String.valueOf(sheet.getSheetExercises().size()));
        listView = findViewById(R.id.list_view_details);

        String[] objs = new String[sheet.getSheetExercises().size()];

        for (int i = 0; i < sheet.getSheetExercises().size(); i++) {

            objs[i] = sheet.getSheetExercises().get(i).getNameExercise();
        }
        Log.d("esercizi: ", sheet.getNamesExercises().toString());
        SheetDetailsAdapter sheetDetailsAdapter;
        sheetDetailsAdapter = new SheetDetailsAdapter(this, objs, sheet);
        listView.setAdapter(sheetDetailsAdapter);
    }
}