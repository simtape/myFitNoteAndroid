package com.example.myfitnoteandroid.ui.goals;


public class Goal{

    boolean status_goal;
    int value_goal;
    double progress_goal;



    public void setGoal(boolean status_in,int value_in,double progress_in){
        setStatus_goal(status_in);
        setValue_goal(value_in);
        setProgress_goal(progress_in);
    }


    public void setStatus_goal(boolean status_in) {
        status_goal = status_in;
    }
    public void setValue_goal(int value_in){
        value_goal = value_in;
    }
    public void setProgress_goal(double progress_in){
        progress_goal = progress_in;
    }

    public boolean getStatus_goal(){
        return status_goal;
    }
    public int getValue_goal(){
        return value_goal;
    }
    public double getProgress_goal(){
        return progress_goal;
    }

}
