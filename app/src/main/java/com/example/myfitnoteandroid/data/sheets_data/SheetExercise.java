package com.example.myfitnoteandroid.data.sheets_data;

public class SheetExercise {
    private String nameExercise;
    private int rep;
    private int serie;

    public SheetExercise(String nameExercise, int rep, int serie) {
        this.nameExercise = nameExercise;
        this.rep = rep;
        this.serie = serie;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public int getRep() {
        return rep;
    }

    public int getSerie() {
        return serie;
    }
}
