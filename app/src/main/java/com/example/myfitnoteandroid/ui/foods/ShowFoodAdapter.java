package com.example.myfitnoteandroid.ui.foods;

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

public class ShowFoodAdapter extends ArrayAdapter<String>{
    Context context;
    List<String> rFood;
    List<Integer> rKcal;

    ShowFoodAdapter(Context c, List<String> food, List<Integer> kcal){
        super(c, R.layout.rowfood, R.id.nameFoodTxt, food);
        this.context = c;
        this.rFood = food;
        this.rKcal = kcal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowFood = layoutInflater.inflate(R.layout.rowfood, parent, false);
        TextView myFood = rowFood.findViewById(R.id.nameFoodTxt);
        TextView myKcal = rowFood.findViewById(R.id.KcaltextView);

        myFood.setText(rFood.get(position));
        myKcal.setText(rKcal.get(position).toString());

        return rowFood;
    }

}
