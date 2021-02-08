package com.example.myfitnoteandroid.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;

public class HomeFragment extends Fragment {

    TextView stepCountertxt, metrestxt;
    SensorManager sensorManager;
    double MagnitudePrevius = 0;
    private Integer stepCount = 0;
    float metrespass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        stepCountertxt = root.findViewById(R.id.stepcounter);
        metrestxt = root.findViewById(R.id.metri);





        return root;
    }

       public void onActivityCreated (Bundle savedInstanceState){
           super.onActivityCreated(savedInstanceState);
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
           SensorEventListener stepDetector = new SensorEventListener() {
               @Override
               public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!=null){
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];
                    double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
                    double MagnitudeDelta = Magnitude - MagnitudePrevius;
                    MagnitudePrevius = Magnitude;

                    if(MagnitudeDelta > 6){
                        stepCount++;
                    }
                    stepCountertxt.setText(stepCount.toString());
                    metrespass = cMetres();
                    metrestxt.setText(String.valueOf(metrespass));
                }
               }

               @Override
               public void onAccuracyChanged(Sensor sensor, int accuracy) {

               }
           };
           sensorManager.registerListener(stepDetector, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();

    }

    public void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();

    }
    public void onResume() {

        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }

    public float cMetres(){
        float metres = (float) (0.762*stepCount);
        return metres;
    }

    public void resetstep(){
        //reset steps every 24 hours
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        stepCount = 0;
        editor.putInt("stepCount", stepCount);
        editor.commit();

    }
}
