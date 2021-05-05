package com.example.bitcoinratemvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public PostViewModelFactory(Application application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PostViewModel.class)) {
            return (T) new PostViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}