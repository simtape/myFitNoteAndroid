package com.example.myfitnoteandroid.ui.foods;

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

public class ShowFoodAdapter extends ArrayAdapter<String> implements Filterable {
    Context context;
    List<String> rFoodFull, rFood;
    List<Integer> rKcal;

    ShowFoodAdapter(Context c, List<String> food, List<Integer> kcal) {
        super(c, R.layout.rowfood, R.id.nameFoodTxt, food);
        this.context = c;
        this.rFood = food;
        this.rFoodFull = new ArrayList<>(food);
        this.rKcal = kcal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowFood = layoutInflater.inflate(R.layout.rowfood, parent, false);
        TextView myFood = rowFood.findViewById(R.id.nameFoodTxt);
        TextView myKcal = rowFood.findViewById(R.id.KcaltextView);

        myFood.setText(rFood.get(position));
        myKcal.setText(rKcal.get(position).toString());

        return rowFood;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return customFilter;


    }

    private Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = rFoodFull;

            if (!(constraint == null || constraint.length() == 0)) {
                filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(String item: rFoodFull){
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
            rFood.clear();
            rFood.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };
}
