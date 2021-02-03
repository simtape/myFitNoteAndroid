package com.example.myfitnoteandroid.ui.esercizi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class eserciziViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public eserciziViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}