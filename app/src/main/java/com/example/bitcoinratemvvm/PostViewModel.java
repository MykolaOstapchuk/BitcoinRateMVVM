package com.example.bitcoinratemvvm;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends AndroidViewModel {
    PostRepository postRepository;
    MutableLiveData<Response<Post>> myResponse = new MutableLiveData<>();

    public PostViewModel(Application application) {
        super(application);
        this.postRepository = new PostRepository();
    }

    public void getCall(String valute) {
        Call<Post> myCall = postRepository.getCallValute(valute);
        myCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                    myResponse.setValue(response);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplication(), "Failed to get data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
