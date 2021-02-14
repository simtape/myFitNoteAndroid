package com.example.myfitnoteandroid.data.sheets_data;

public class SheetExercise {
    private String nameExercise;
    private String rep;
    private String serie;

    public SheetExercise(String nameExercise, String rep, String serie) {
        this.nameExercise = nameExercise;
        this.rep = rep;
        this.serie = serie;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public String getRep() {
        return rep;
    }

    public String getSerie() {
        return serie;
    }
}
