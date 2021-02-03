package com.example.myfitnoteandroid.ui.calcolaKcal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class calcolaKcalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public calcolaKcalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}