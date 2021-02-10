package com.example.myfitnoteandroid.ui.goals;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myfitnoteandroid.R;

public class GoalsFragment extends Fragment {
    ListView listView;
    String mTitle[] = {"Acqua","Grassi Bruciati"};
    String mDescription[] = {"Quantità di acqua da bere","Calorie da bruciare"};
    int images[] = {R.drawable.ic_baseline_local_drink_24,R.drawable.ic_kcal};
    ViewGroup root;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("Goal","ok");
        root = (ViewGroup) inflater.inflate(R.layout.goals_fragment, container, false);
        listView = root.findViewById(R.id.goal_view);

        GoalsAdapter adapter = new GoalsAdapter(getContext(), mTitle, mDescription, images);
        listView.setAdapter(adapter);
        return root;

    }
}