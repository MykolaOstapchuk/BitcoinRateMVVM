package com.example.bitcoinratemvvm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;

public class PostViewModel extends AndroidViewModel {
    PostRepository postRepository;

    public PostViewModel(Application application){
        super(application);
        this.postRepository = new PostRepository();
    }

    public Call<Post> getCallValute(String valute){
        return  postRepository.getCallValute(valute);
        //return postRepository.getCurrentValute(valute);
    }

//    public Call<Post> getCurrentValute(String valute) {
//        return postRepository.getCurrentValute(valute);
//    }

}
