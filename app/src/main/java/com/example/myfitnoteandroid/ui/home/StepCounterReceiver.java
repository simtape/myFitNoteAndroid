package com.example.myfitnoteandroid.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myfitnoteandroid.data.StepCounterHandler;

public class StepCounterReceiver extends BroadcastReceiver {
    String SHARED_PREF_NAME = "counter";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        /*SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("shared pref before",String.valueOf(sharedPreferences.getInt("rtStepCount", 0)));
        editor.clear();
        editor.putInt("rtStepCount", 0).apply();
        Log.d("shared pref",String.valueOf(sharedPreferences.getInt("stepCount", 0)));*/
        StepCounterHandler.getInstance().setCounter(0);
        Log.d("shared pref",String.valueOf(StepCounterHandler.getInstance().getCounter()));
    }
}