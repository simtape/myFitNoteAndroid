package com.example.myfitnoteandroid.data.sheets_data;

import java.util.ArrayList;
import java.util.List;

public class SheetsHandler {
    private static SheetsHandler instance = new SheetsHandler();
    private List<Sheet> userSheets = new ArrayList<>();

    private SheetsHandler() {

    }

    public void resetSheetsHandler() {
        userSheets = new ArrayList<>();

    }

    public static SheetsHandler getInstance() {
        return instance;
    }

    public void addSheet(Sheet sheet) {
        userSheets.add(sheet);

    }

    public List<Sheet> getUserSheets() {
        return userSheets;
    }

    public String[] nameSheets() {
        String[] names = new String[this.userSheets.size()];

        int i = 0;
        for (Sheet sheet : userSheets) {
            names[i] = sheet.getName();
            i++;
        }


        return names;
    }

    public String[]getDates(){
        String[] dates = new String[this.userSheets.size()];

        int i = 0;
        for (Sheet sheet : userSheets) {
            dates[i] = sheet.getDate();
            i++;
        }


        return dates;

    }
}
