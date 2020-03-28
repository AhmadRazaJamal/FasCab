package com.example.fascab.ui.preferences;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.Bundle;

import com.example.fascab.R;

public class PreferencesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreferencesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is preferences fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}