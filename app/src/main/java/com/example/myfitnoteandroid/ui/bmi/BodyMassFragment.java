package com.example.myfitnoteandroid.ui.bmi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfitnoteandroid.R;

import java.util.ArrayList;
import java.util.List;

public class BodyMassFragment extends Fragment implements View.OnClickListener {

    Spinner sessoSp;
    ViewGroup view;
    TextView sessoTxt, resultIndexTxt;
    Button indiceC;
    String yourSesso;
    EditText pesoEdit, altezzaEdit;
    final double meter = 0.01;
    double result;

    public BodyMassFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       view = (ViewGroup) inflater.inflate(R.layout.body_mass_fragment, container, false);
        sessoTxt = view.findViewById(R.id.textSesso);
        resultIndexTxt = view.findViewById(R.id.resultIndex);
        indiceC = view.findViewById(R.id.indice);
        indiceC.setOnClickListener(this);
        pesoEdit = view.findViewById(R.id.editpeso);
        altezzaEdit = view.findViewById(R.id.editaltezza);
        sessoSp = view.findViewById(R.id.spinnersesso);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> sesso = new ArrayList<>();
        sesso.add("- scegli il tuo sesso -");
        sesso.add("M");
        sesso.add("F");
        ArrayAdapter<String> sessoAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, sesso );
        sessoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessoSp.setAdapter(sessoAdapter);
        sessoSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 yourSesso = sessoSp.getSelectedItem().toString();
                if(!yourSesso.equals("- scegli il tuo sesso -")){
                    sessoTxt.setText(yourSesso);
                }else {
                    sessoTxt.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == indiceC.getId()) {
            switch (yourSesso){
                case "M":
                    String pesoString = pesoEdit.getText().toString();
                    int pesoInt = Integer.parseInt(pesoString);
                    String altezzaStr = altezzaEdit.getText().toString();
                    double aletzzaInt = Integer.parseInt(altezzaStr);
                    aletzzaInt = aletzzaInt*meter;
                    result = cBMI(pesoInt, aletzzaInt);
                 if (result<=20.00){
                     resultIndexTxt.setText("SOTTOPESO");
                     resultIndexTxt.setBackgroundColor(Color.BLUE);
                     resultIndexTxt.setTextColor(Color.WHITE);
                 }else if(20.00<result && result<= 25.00){
                     resultIndexTxt.setText("NORMOPESO");
                     resultIndexTxt.setBackgroundColor(Color.GREEN);
                 }else if(25.00<result && result<= 30.00){
                     resultIndexTxt.setText("SOVRAPPESO");
                     resultIndexTxt.setBackgroundColor(Color.YELLOW);
                     resultIndexTxt.setTextColor(Color.BLACK);
                 }else if(30.00<result && result<= 40.00){
                     resultIndexTxt.setText("ADIPOSITA'");
                     resultIndexTxt.setBackgroundColor(Color.RED);
                     resultIndexTxt.setTextColor(Color.WHITE);
                 }else{
                     Toast.makeText(getContext(), "Non è stato possibile calcolare l'indice", Toast.LENGTH_LONG).show();
                 }
                 break;

                case "F":
                    String pesoStringF = pesoEdit.getText().toString();
                    int pesoIntF = Integer.parseInt(pesoStringF);
                    String altezzaStrF = altezzaEdit.getText().toString();
                    double aletzzaIntF = Integer.parseInt(altezzaStrF);
                    aletzzaIntF = aletzzaIntF*meter;
                    result = cBMI(pesoIntF, aletzzaIntF);
                    if (result<=19.00){
                        resultIndexTxt.setText("SOTTOPESO");
                        resultIndexTxt.setBackgroundColor(Color.BLUE);
                        resultIndexTxt.setTextColor(Color.WHITE);
                    }else if((19.00<result) && (result<= 24.00)){
                        resultIndexTxt.setText("NORMOPESO");
                        resultIndexTxt.setBackgroundColor(Color.GREEN);
                    }else if(24.00<result && result<= 30.00){
                        resultIndexTxt.setText("SOVRAPPESO");
                        resultIndexTxt.setBackgroundColor(Color.YELLOW);
                        resultIndexTxt.setTextColor(Color.BLACK);
                    }else if(30.00<result && result<= 40.00){
                        resultIndexTxt.setText("ADIPOSITA'");
                        resultIndexTxt.setBackgroundColor(Color.RED);
                        resultIndexTxt.setTextColor(Color.WHITE);
                    }else{
                        Toast.makeText(getContext(), "Non è stato possibile calcolare l'indice", Toast.LENGTH_LONG).show();
                    }
                break;
            }
        }
    }

    public double cBMI(int peso, double altezza){
        return peso/(altezza*altezza);
    }
}

