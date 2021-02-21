package com.example.myfitnoteandroid.ui.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.User;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpTabFragment extends Fragment implements View.OnClickListener {

    //RequestQueue requestQueue;
    EditText nome, cognome, email, password;
    Button btn_registration1;
    CheckBox showpassword;
    User newUser;
    Boolean mailIsUsed = false;
    ImageView wrongEmail, rightEmail;
    Boolean isOk = false;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
        wrongEmail = root.findViewById(R.id.email_wrong);
        wrongEmail.setVisibility(View.INVISIBLE);

        rightEmail = root.findViewById(R.id.email_ok);
        rightEmail.setVisibility(View.INVISIBLE);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().trim().matches(emailPattern) && s.length() > 0) {
                    rightEmail.setVisibility(View.VISIBLE);
                    wrongEmail.setVisibility(View.INVISIBLE);
                    isOk = true;
                } else {
                    rightEmail.setVisibility(View.INVISIBLE);
                    wrongEmail.setVisibility(View.VISIBLE);
                    isOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_registration1 = root.findViewById(R.id.registrazione1);
        showpassword = root.findViewById(R.id.showpasswordReg);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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

            checkMail();


        }
    }

    private void checkMail() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("mail", email.getText().toString());
        } catch (JSONException e) {

        }
        String url = "https://myfitnote.herokuapp.com/users/find_mail";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //mailIsUsed = false;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("error")) {
                        mailIsUsed = true;
                        Toast.makeText(getContext(), "La mail è gia usata!", Toast.LENGTH_LONG).show();
                    } else {

                        String name = nome.getText().toString();
                        String surname = cognome.getText().toString();
                        String mail = email.getText().toString();
                        String pw = password.getText().toString();
                        if (name.trim().isEmpty() || surname.trim().isEmpty() || mail.trim().isEmpty() || pw.trim().isEmpty()) {
                            Toast.makeText(getContext(), "Non hai compilato tutti i campi", Toast.LENGTH_LONG).show();

                        } else if (!isOk) {
                            Toast.makeText(getContext(), "Email non valida!", Toast.LENGTH_LONG).show();

                        } else {

                            SessionManager sessionManager = new SessionManager(getContext());

                            sessionManager.removeSession();
                            sessionManager.setSessionName(name);
                            sessionManager.setSessionSurname(surname);
                            sessionManager.setSessionMail(mail);
                            sessionManager.setSessionPassword(pw);

                            System.out.println("Nome registrazione parte 1: " + sessionManager.getName());
                            System.out.println("Cognome registrazione parte 1: " + sessionManager.getSurname());
                            System.out.println("Email registrazione parte 1: " + sessionManager.getMail());
                            Intent intent = new Intent(getActivity(), SignUpTwoActivity.class);
                            startActivity(intent);

                        }
                    }
                    //mailIsUsed = false;
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onPause() {
        super.onPause();
        rightEmail.setVisibility(View.INVISIBLE);
        wrongEmail.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
        rightEmail.setVisibility(View.INVISIBLE);
        wrongEmail.setVisibility(View.INVISIBLE);
    }
}

