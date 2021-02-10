package com.example.myfitnoteandroid.data;

import com.example.myfitnoteandroid.ui.home.StepCounterReceiver;

public class StepCounterHandler {
    int counter;

    private static StepCounterHandler stepCounterHandler = new StepCounterHandler();

    public static StepCounterHandler getInstance(){
        return stepCounterHandler;
    }

    private StepCounterHandler(){

    }

    public void setCounter(int i){
        this.counter = i;
    }

    public int getCounter(){
        return counter;
    }

    public void increase(){
        counter++;

    }
}
