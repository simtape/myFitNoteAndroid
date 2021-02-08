package com.example.myfitnoteandroid.data.sheets_data;

import java.util.ArrayList;
import java.util.List;

public class SheetsHandler {
    private static SheetsHandler instance = new SheetsHandler();
    private List<Sheet> userSheets = new ArrayList<>();

    private SheetsHandler() {

    }

    public void resetSheetsHandler(){
        userSheets = new ArrayList<>();

    }

    public static SheetsHandler getInstance() {
        return instance;
    }

    public void addSheet(Sheet sheet) {
        userSheets.add(sheet);

    }

    public List<Sheet> getUserSheets(){
        return  userSheets;
    }
}
