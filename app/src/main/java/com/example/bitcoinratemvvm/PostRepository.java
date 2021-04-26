package com.example.bitcoinratemvvm;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {

    public static final String BASE_URL = "https://rest.coinapi.io/";
    public static final String API_KEY = "23F2CAC9-88E6-4388-9FC5-EF0DD195B9AA";

    public Call<Post> getCurrentValute(String valute) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        return retrofitAPI.getCurrentValute(valute, API_KEY);
    }
}
