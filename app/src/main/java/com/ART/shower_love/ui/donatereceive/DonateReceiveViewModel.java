package com.ART.shower_love.ui.donatereceive;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DonateReceiveViewModel extends ViewModel {

        private MutableLiveData<String> mText;

        public DonateReceiveViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is the about us section of shower love");
        }

        public LiveData<String> getText() {
            return mText;
        }
    }

