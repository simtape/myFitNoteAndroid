package com.example.myfitnoteandroid.ui.misuraBattito;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfitnoteandroid.R;

public class misuraBattitoFragment extends Fragment {

    private MisuraBattitoViewModel mViewModel;

    public static misuraBattitoFragment newInstance() {
        return new misuraBattitoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.misura_battito_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisuraBattitoViewModel.class);
        // TODO: Use the ViewModel
    }

}