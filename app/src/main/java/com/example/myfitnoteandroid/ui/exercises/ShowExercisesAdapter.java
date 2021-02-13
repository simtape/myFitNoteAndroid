package com.example.myfitnoteandroid.ui.exercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfitnoteandroid.R;

import java.util.ArrayList;
import java.util.List;

public class ShowExercisesAdapter extends ArrayAdapter<String>implements Filterable {
    Context context;
    List<String> rExercise, rExerciseFull;
    List<String> rGear;

        ShowExercisesAdapter(Context c, List<String> name,  List<String> gear){
            super(c, R.layout.rowexe, R.id.textViewExe, name);
            this.context = c;
            this.rExercise = name;
            this.rExerciseFull = new ArrayList<>(name);
            this.rGear = gear;

        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowExe = layoutInflater.inflate(R.layout.rowexe, parent, false);
        TextView myExercise = rowExe.findViewById(R.id.textViewExe);
        TextView myGear = rowExe.findViewById(R.id.gearTextView);

        myExercise.setText(rExercise.get(position));
        myGear.setText(rGear.get(position));

       return rowExe;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return customFilter;


    }


    private Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = rExerciseFull;

            if (!(constraint == null || constraint.length() == 0)) {
                filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(String item: rExerciseFull){
                    if(item.toLowerCase().contains(filterPattern))
                        filteredList.add(item);
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rExercise.clear();
            rExercise.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };
}
