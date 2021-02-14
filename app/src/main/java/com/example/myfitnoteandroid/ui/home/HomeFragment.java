package com.example.myfitnoteandroid.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.myfitnoteandroid.data.StepCounterHandler;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    TextView stepCountertxt, metrestxt, pesoTxt, kcaltxt;
    SensorManager sensorManager;
    double MagnitudePrevius = 0;
    private Integer stepCount = 0;
    float metrespass, kcalpass;
    String peso;
    int pesoInt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        stepCountertxt = root.findViewById(R.id.stepcounter);
        metrestxt = root.findViewById(R.id.metri);
        kcaltxt = root.findViewById(R.id.kcal);
        resetstep();




        return root;
    }

       public void onActivityCreated (Bundle savedInstanceState){
           super.onActivityCreated(savedInstanceState);
           SessionManager sessionManager = new SessionManager(getContext());
           peso = sessionManager.getPeso();
           pesoInt = Integer.parseInt(peso);


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
                        StepCounterHandler.getInstance().increase();
                    }
                    stepCountertxt.setText(String.valueOf(StepCounterHandler.getInstance().getCounter()));
                    metrespass = cMetres();
                    metrestxt.setText(String.valueOf(metrespass));
                    kcalpass = cKcal();
                    kcaltxt.setText(String.valueOf(kcalpass));




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
        editor.putInt("stepCount", StepCounterHandler.getInstance().getCounter());
        editor.apply();

    }

    public void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", StepCounterHandler.getInstance().getCounter());
        editor.apply();

    }
    public void onResume() {

        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        StepCounterHandler.getInstance().setCounter(sharedPreferences.getInt("stepCount", 0));
    }

    public float cMetres(){
        float metres = (float) (0.762*StepCounterHandler.getInstance().getCounter());
        return metres;
    }

    public float cKcal(){
        float kcal = (float) ((0.50)*(pesoInt)*(metrespass*1000));
        return kcal;
    }

    public void resetstep() {
        //reset steps every 24 hours
        Calendar now = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE, 53);
        cal.set(Calendar.SECOND, 15);
        Intent intent = new Intent(getContext(), StepCounterReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(), 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (now.before(cal)) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else {
            cal.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) +1);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
