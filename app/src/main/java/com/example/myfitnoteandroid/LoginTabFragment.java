package com.example.myfitnoteandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myfitnoteandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginTabFragment extends Fragment {

   EditText username;
   EditText password;
   FloatingActionButton login;
   float v=0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        username =  root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        login = root.findViewById(R.id.login);

        username.setAlpha(v);
        password.setAlpha(v);
        login.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
