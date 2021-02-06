package com.example.myfitnoteandroid.data.sheets_data;

import java.util.List;

public class SheetsHandler {
    private static SheetsHandler instance = new SheetsHandler();
    private List<Sheet> userSheets;

    private SheetsHandler() {

    }

    public static SheetsHandler getInstance() {
        return instance;
    }

    public void setUserSheets(List<Sheet> userSheets) {
        this.userSheets = userSheets;

    }

}
