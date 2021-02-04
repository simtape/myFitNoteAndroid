package com.example.myfitnoteandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentRegistrazioneDue extends Fragment {

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = getActivity().getIntent();
        String nome = intent.getStringExtra("nome");
        System.out.print(nome);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //= getIntent();
    // message = intent.getStringExtra("message");


}
