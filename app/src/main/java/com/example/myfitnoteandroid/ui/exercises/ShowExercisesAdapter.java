package com.example.myfitnoteandroid.ui.exercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfitnoteandroid.R;

import java.util.List;

public class ShowExercisesAdapter extends ArrayAdapter<String>{
    Context context;
    List<String> rExercise;
    List<String> rGear;

        ShowExercisesAdapter(Context c, List<String> name,  List<String> gear){
            super(c, R.layout.rowexe, R.id.textViewExe, name);
            this.context = c;
            this.rExercise = name;
            this.rGear = gear;

        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowExe = layoutInflater.inflate(R.layout.rowexe, parent, false);
        TextView myExercise = rowExe.findViewById(R.id.textViewExe);
        TextView myGear = rowExe.findViewById(R.id.textView2);

        myExercise.setText(rExercise.get(position));
        myGear.setText(rGear.get(position));

       return rowExe;
    }
}
