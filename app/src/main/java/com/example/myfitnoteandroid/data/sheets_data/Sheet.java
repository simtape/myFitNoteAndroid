package com.example.myfitnoteandroid.data.sheets_data;

import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sheet {
    private String name;
    private String id;
    private List<String> days;
    private List<SheetExercise> sheetExercises ;
    private String date;

    public Sheet(String name, String id, List<SheetExercise> sheetExercises, List<String> days, String date) {
        this.name = name;
        this.id = id;
        this.sheetExercises = sheetExercises;
        this.date = date;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SheetExercise> getSheetExercises() {
        return sheetExercises;
    }

    public void setSheetExercises(List<SheetExercise> sheetExercises) {
        this.sheetExercises = sheetExercises;
    }

    public List<String> getDays() {
        return this.days;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;

    }

    public List<String> getNamesExercises() {
        List<String> exercises = new ArrayList<>();
        for (int i = 0; i < sheetExercises.size(); i++) {
            exercises.add(sheetExercises.get(i).getNameExercise());


        }
        return exercises;
    }

    public List<String> getSeries() {
        List<String> series = new ArrayList<>();
        for (int i = 0; i < sheetExercises.size(); i++) {
            series.add(sheetExercises.get(i).getSerie());


        }
        return series;
    }

    public List<String> getReps() {
        List<String> reps = new ArrayList<>();
        for (int i = 0; i < sheetExercises.size(); i++) {
            reps.add(sheetExercises.get(i).getRep());


        }
        return reps;
    }
}
