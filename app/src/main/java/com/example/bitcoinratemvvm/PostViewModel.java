package com.example.bitcoinratemvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import retrofit2.Call;

public class PostViewModel extends AndroidViewModel {
    PostRepository postRepository;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository();
    }

    public Call<Post> getCurrentValute(String valute) {
        return postRepository.getCurrentValute(valute);
    }

}
