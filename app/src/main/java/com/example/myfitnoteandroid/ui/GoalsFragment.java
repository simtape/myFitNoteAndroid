package com.example.myfitnoteandroid.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfitnoteandroid.R;


public class GoalsFragment extends Fragment {


    public GoalsFragment() {

    }

//    @Override
    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.goals_fragment, container, false);
    }
}