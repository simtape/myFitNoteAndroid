package com.example.myfitnoteandroid.ui.home;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myfitnoteandroid.R;
import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.data.StepCounterHandler;

import java.text.DecimalFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView stepCountertxt;
    TextView metrestxt;
    TextView kcaltxt;
    SensorManager sensorManager;
    double MagnitudePrevius = 0;
    float metrespass;
    float kcalpass;
    String peso;
    int pesoInt;
    LinearLayout control;
    CardView glass,pluss,kcalCard;
    LottieAnimationView walker;
    ConstraintLayout wal_layout;
    int[] animation = new int[4];
    int cont=0;
    DisplayMetrics display;
    Button reset;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        animation[0]=R.raw.flex;
        animation[1]=R.raw.flex2;
        animation[2]=R.raw.flex3;
        animation[3]=R.raw.flex4;
        stepCountertxt = root.findViewById(R.id.stepcounter);
        metrestxt = root.findViewById(R.id.metri);
        kcaltxt = root.findViewById(R.id.kcal);
        glass = root.findViewById(R.id.glass);
        pluss = root.findViewById(R.id.pluss);
        kcalCard = root.findViewById(R.id.cardKcal);
        control = root.findViewById(R.id.control);
        walker = root.findViewById(R.id.walker_an);
        wal_layout = root.findViewById(R.id.walk_layout);
        reset = root.findViewById(R.id.resetHome);
        reset.setOnClickListener(this);
        set_water();
        HomeThread wal_thread = new HomeThread();
        wal_thread.start();
        //resetstep();

      /*  SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        SessionManager sessionManager = new SessionManager(getContext());
        if (sessionManager.getAccess() != 16) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putInt("stepCount", 0);
            editor.apply();

            sessionManager.setLastAccessNoControl(16);
        }
*/
        //StepCounterHandler.getInstance().setCounter(sharedPreferences.getInt("stepCount", 0));


        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SessionManager sessionManager = new SessionManager(getContext());
        peso = sessionManager.getPeso();
       pesoInt = Integer.parseInt(peso);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        StepCounterHandler.getInstance().setCounter(StepCounterHandler.getInstance().getCounter() - 1);

        SensorEventListener stepDetector = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];
                    double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    double MagnitudeDelta = Magnitude - MagnitudePrevius;
                    MagnitudePrevius = Magnitude;
                    DecimalFormat df = new DecimalFormat("0.00");
                    if (MagnitudeDelta > 6) {
                        StepCounterHandler.getInstance().increase();
                    }
                    stepCountertxt.setText(String.valueOf(StepCounterHandler.getInstance().getCounter()));
                    metrespass = cMetres();
                    metrestxt.setText(metrespass + "   " + "m");
                    kcalpass = cKcal();

                    //Da sistemare le cifre dopo la virgola
                    kcaltxt.setText(df.format(cKcal())+ "   " + "Kcal");
                    //////////////////////////////////////
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void set_water(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)control.getLayoutParams();
        LinearLayout.LayoutParams kcal = (LinearLayout.LayoutParams)kcalCard.getLayoutParams();
        Log.d("d","d"+getContext().getResources().getDisplayMetrics().widthPixels);
        if(getContext().getResources().getDisplayMetrics().widthPixels<1080){
            params.height = kcal.height;
            control.setLayoutParams(params);
            glass.setVisibility(View.GONE);
            pluss.setVisibility(View.VISIBLE);
        }else
        {
            glass.setVisibility(View.VISIBLE);
            pluss.setVisibility(View.GONE);
        }
    }

    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", StepCounterHandler.getInstance().getCounter());
        editor.apply();

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.setLastAccess(Calendar.DATE);

    }

    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", StepCounterHandler.getInstance().getCounter());
        editor.apply();

        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.setLastAccess(Calendar.DATE);


    }

    public void onResume() {

        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SessionManager sessionManager = new SessionManager(getContext());
        if (sessionManager.getAccess() != Calendar.DATE) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putInt("stepCount", 0);
            editor.apply();

            sessionManager.setLastAccess(Calendar.DATE);
        }
        StepCounterHandler.getInstance().setCounter(sharedPreferences.getInt("stepCount", 0));
    }

    public float cMetres() {
        return (float) (0.762 * StepCounterHandler.getInstance().getCounter());
    }

    public float cKcal() {
        return (float) ((0.0005) * (pesoInt) * (cMetres()));
    }
    public void set_walker(){
        walker.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                walker.setVisibility(View.GONE);
                cont++;
                walker.setAnimation(animation[cont]);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        walker.setVisibility(View.VISIBLE);
                        walker.playAnimation();
                    }
                }, 2000);
                if(cont==3){
                    cont=0;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == reset.getId()){
            StepCounterHandler.getInstance().setCounter(0);
        }
    }

    class HomeThread extends Thread{
        @Override
        public void run(){
            set_walker();
        }
    }
}
