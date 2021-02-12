package com.example.myfitnoteandroid.ui.goals;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myfitnoteandroid.MainActivity;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.ui.sheets.SheetDetailsActivity;

import static android.widget.Toast.LENGTH_SHORT;


public class GoalsFragment extends Fragment {
    ListView listView;
    String mTitle[] = {"Acqua", "Grassi Bruciati"};
    String mDescription[] = {"Quantità di acqua da bere", "Calorie da bruciare"};
    int images[] = {R.drawable.ic_baseline_local_drink_24, R.drawable.ic_kcal};
    Switch aSwitch;
    ViewGroup root;
    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.goals_fragment, container, false);
        listView = root.findViewById(R.id.goal_view);
        GoalsAdapter adapter = new GoalsAdapter(getContext(), mTitle, mDescription, images);
        listView.setAdapter(adapter);
        return root;
    }

}