package com.example.myfitnoteandroid.ui.goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfitnoteandroid.R;

public class GoalsAdapter extends ArrayAdapter<String> {
    Context context;
    String rTitle[];
    String rDesciption[];
    int rImgs[];
    long item;
    GoalsAdapter(Context c, String[] title, String[] description, int imgs[]) {
        super(c, R.layout.goals_row, R.id.tvWater, title);
        this.context = c;
        this.rTitle = title;
        this.rDesciption = description;
        this.rImgs = imgs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View goals_row = layoutInflater.inflate(R.layout.goals_row, parent, false);
        ImageView images = goals_row.findViewById(R.id.imageWater);
        TextView myTitle = goals_row.findViewById(R.id.tvWater);
        TextView myDescription = goals_row.findViewById(R.id.goalstv2);

        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);
        myDescription.setText(rDesciption[position]);
        Switch aSwitch = goals_row.findViewById(R.id.goals_switch);
        return goals_row;
    }
}
