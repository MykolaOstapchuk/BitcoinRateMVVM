package com.example.bitcoinratemvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PostViewModelFactory implements ViewModelProvider.Factory {
    private Application myapplication;

    public PostViewModelFactory(Application application) {
        this.myapplication = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PostViewModel.class)) {
            return (T) new PostViewModel(myapplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}