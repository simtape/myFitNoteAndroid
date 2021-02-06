package com.example.myfitnoteandroid.data.sheets_data;

import com.example.myfitnoteandroid.data.sheets_data.SheetExercise;

import java.util.List;

public class Sheet {
    private String name;
    private String id;
    private List<SheetExercise>sheetExercises;

    public Sheet(String name, String id, List<SheetExercise> sheetExercises) {
        this.name = name;
        this.id = id;
        this.sheetExercises = sheetExercises;
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
}
