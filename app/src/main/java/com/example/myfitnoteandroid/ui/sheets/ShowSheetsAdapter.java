package com.example.myfitnoteandroid.ui.sheets;

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

public class ShowSheetsAdapter extends ArrayAdapter<String> {

    Context context;
    String rTitle[];
    String rDescription[];
    List<Sheet> sheets;

    ShowSheetsAdapter(Context c, String title[], /*String description[], */List<Sheet> sheets) {

        super(c, R.layout.row, R.id.textView1, title);

        this.context = c;
        this.rTitle = title;
        //this.rDescription = description;
        this.sheets = sheets;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            //ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.gearTextView);

            // now set our resources on views
            //images.setImageResource(rImgs);
            //myTitle.setText(rTitle[position]);
            myTitle.setText(sheets.get(position).getName());
            myDescription.setText(sheets.get(position).getDate());
            //myDescription.setText(rDescription[position]);


            return row;

    }
}