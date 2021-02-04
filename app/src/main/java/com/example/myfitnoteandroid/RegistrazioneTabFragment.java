package com.example.myfitnoteandroid;

import android.content.Intent;
import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.User;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrazioneTabFragment extends Fragment implements View.OnClickListener{

    //RequestQueue requestQueue;
    EditText nome, cognome, email, password;
    Button btn_registration1;
    User newUser;

    float v = 0;

    private JSONObject postData, exercisesJsonObject;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registrazione_tab_fragment, container, false);

        nome = root.findViewById(R.id.nome);
        cognome = root.findViewById(R.id.Cognome);
        email = root.findViewById(R.id.Email);
        password = root.findViewById(R.id.password);


        btn_registration1 = root.findViewById(R.id.registrazione1);

        btn_registration1.setOnClickListener((View.OnClickListener) this);

        //btn_registration.setOnClickListener(this);


        return root;
    }

    public void onClick(View v) {

        if (v.getId() == btn_registration1.getId()) {
            Log.d("PROVA", "ho cliccato sul tasto registrazione parte 1");
            String Nometopass = nome.getText().toString();
            String Cognometopass = cognome.getText().toString();
            String Emailtopass = email.getText().toString();
            String Passwordtopass = password.getText().toString();
            if (Nometopass.isEmpty() & Cognometopass.isEmpty() & Emailtopass.isEmpty() & Passwordtopass.isEmpty()) {

                Toast.makeText(getContext(), "Compilare tutti i campi", Toast.LENGTH_LONG).show();



            } else {
                Intent goRegistrazione2 = new Intent(getActivity(), FragmentRegistrazioneDue.class);
                goRegistrazione2.putExtra("nome", Nometopass);
                goRegistrazione2.putExtra("cognome", Cognometopass);
                goRegistrazione2.putExtra("email", Emailtopass);
                goRegistrazione2.putExtra("password", Passwordtopass);
                this.startActivity(goRegistrazione2);
            }

        }
    }
}
