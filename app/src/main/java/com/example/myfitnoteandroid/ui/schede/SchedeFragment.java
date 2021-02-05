package com.example.myfitnoteandroid.ui.schede;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.ui.home.HomeViewModel;



public class SchedeFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.schede_fragment, container, false);
        TextView textView = root.findViewById(R.id.text_fragment);
        textView.setText("Non hai ancora creato nessuna scheda!");

        return root;
    }
}