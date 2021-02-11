package com.example.myfitnoteandroid.ui.sheets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;

public class SheetDetailsAdapter extends ArrayAdapter<String> {
    String[]obj;
    Sheet sheet;
    Context context;


    public SheetDetailsAdapter(Context context, String[] objects, Sheet sheet) {
        super(context, R.layout.row_details_sheet, R.id.tvNameExercises, objects);
        this.obj = objects;
        this.sheet = sheet;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_details_sheet, parent, false);
        TextView nameExercise = row.findViewById(R.id.tvNameExercises);
        TextView myDescription = row.findViewById(R.id.tvSeriesReps);
        nameExercise.setText(sheet.getSheetExercises().get(position).getNameExercise());

        Log.d("nome es adapter", sheet.getSheetExercises().get(position).getNameExercise());
        String repetition = String.valueOf(sheet.getSheetExercises().get(position).getRep());
        String serie = String.valueOf(sheet.getSheetExercises().get(position).getSerie());
        String concRepSeries = serie.concat(" ").concat(repetition);
        myDescription.setText(concRepSeries);
        return row;

    }
}
