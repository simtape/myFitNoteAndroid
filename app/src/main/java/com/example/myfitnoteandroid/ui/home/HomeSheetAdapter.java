package com.example.myfitnoteandroid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.sheets_data.Sheet;

import java.util.List;

public class HomeSheetAdapter extends ArrayAdapter<String> {
    List<String> obj;
    Context context;
    Sheet sheet;

    public HomeSheetAdapter(@NonNull Context context, @NonNull List<String> objects, Sheet sheet) {
        super(context, R.layout.last_sheet_row_home, objects);
        this.context = context;
        this.sheet = sheet;
        this.obj = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.last_sheet_row_home, parent, false);
        TextView nameExercise = row.findViewById(R.id.tvNameExercisesHome);
        nameExercise.setText(sheet.getSheetExercises().get(position).getNameExercise());

        TextView seriesAndReps = row.findViewById(R.id.tvSeriesRepsHome);
        String repetition = String.valueOf(sheet.getSheetExercises().get(position).getRep());
        String serie = String.valueOf(sheet.getSheetExercises().get(position).getSerie());
        String concRepSeries = serie.concat(" ").concat(repetition);
        seriesAndReps.setText(concRepSeries);


        return row;
    }
}
