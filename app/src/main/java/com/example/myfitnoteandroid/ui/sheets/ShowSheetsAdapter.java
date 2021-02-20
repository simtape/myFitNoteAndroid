package com.example.myfitnoteandroid.ui.sheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

public class ShowSheetsAdapter extends ArrayAdapter<String> implements Filterable {

    Context context;
    List<String> names, namesFull, dates;

    ShowSheetsAdapter(Context c,  /*String description[], List<Sheet> sheets,*/ List<String> names, List<String> dates) {

        super(c, R.layout.row, R.id.textView1, names);

        this.context = c;

        //this.rDescription = description;
        //this.sheetsFull = new ArrayList<>(sheets);
        //this.sheets = sheets;
        this.names = names;
        this.namesFull = new ArrayList<>(names);
        this.dates = dates;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View row = layoutInflater.inflate(R.layout.row, parent, false);
        //ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView1);
        TextView myDescription = row.findViewById(R.id.gearTextView);

        // now set our resources on views
        //images.setImageResource(rImgs);
        //myTitle.setText(rTitle[position]);
        //myTitle.setText(sheets.get(position).getName());
        //myDescription.setText(sheets.get(position).getDate());

        myTitle.setText(names.get(position));
        myDescription.setText(dates.get(position));


        //myDescription.setText(rDescription[position]);
        Log.d("prova schede", "ok");


        return row;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return customFilter;


    }

    private final Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = namesFull;

            if (!(constraint == null || constraint.length() == 0)) {
                filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : namesFull) {
                    if (item.toLowerCase().contains(filterPattern))
                        filteredList.add(item);
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            names.clear();
            names.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


}