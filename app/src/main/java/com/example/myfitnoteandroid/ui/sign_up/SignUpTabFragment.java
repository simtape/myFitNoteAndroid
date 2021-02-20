package com.example.myfitnoteandroid.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.User;

import org.json.JSONObject;


public class SignUpTabFragment extends Fragment implements View.OnClickListener {

    //RequestQueue requestQueue;
    EditText nome, cognome, email, password;
    Button btn_registration1;
    CheckBox showpassword;
    User newUser;

    float v = 0;

    private JSONObject postData, exercisesJsonObject;

    @Override
    public void onStart() {
        super.onStart();

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        nome = root.findViewById(R.id.nome);
        cognome = root.findViewById(R.id.Cognome);
        email = root.findViewById(R.id.Emaill);
        password = root.findViewById(R.id.passwordL);


        btn_registration1 = root.findViewById(R.id.registrazione1);
        showpassword = root.findViewById(R.id.showpasswordReg);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn_registration1.setOnClickListener(this);
        return root;
    }

    public void onClick(View v) {

        if (v.getId() == btn_registration1.getId()) {
            Log.d("PROVA", "ho cliccato sul tasto registrazione parte 1");
            String name = nome.getText().toString();
            String surname = cognome.getText().toString();
            String mail = email.getText().toString();
            String password = this.password.getText().toString();
            if (name.trim().isEmpty() || surname.trim().isEmpty() || mail.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(getContext(), "Non hai compilato tutti i campi", Toast.LENGTH_LONG).show();
            } else {

                SessionManager sessionManager  =  new SessionManager(getContext());

                sessionManager.removeSession();
                sessionManager.setSessionName(name);
                sessionManager.setSessionSurname(surname);
                sessionManager.setSessionMail(mail);
                sessionManager.setSessionPassword(password);

                System.out.println("Nome registrazione parte 1: " + sessionManager.getName());
                System.out.println("Cognome registrazione parte 1: " + sessionManager.getSurname());
                System.out.println("Email registrazione parte 1: " + sessionManager.getMail());
                Intent intent = new Intent(getActivity(), SignUpTwoActivity.class);
                startActivity(intent);

            }

        }
    }
    }

