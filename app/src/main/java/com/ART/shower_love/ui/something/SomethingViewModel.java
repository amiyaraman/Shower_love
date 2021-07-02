package com.ART.shower_love.ui.something;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SomethingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SomethingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Something Section");

    }

    public LiveData<String> getText() {
        return mText;
    }
}