package com.example.myfitnoteandroid.data;

import com.example.myfitnoteandroid.ui.home.StepCounterReceiver;

public class StepCounterHandler {
    int counter;
    int switch_walk;

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
    public void setSwitch(int i){
        this.switch_walk=i;
    }
    public int getSwitch(){
        return switch_walk;
    }

    public void increase(){
        counter++;

    }
}
