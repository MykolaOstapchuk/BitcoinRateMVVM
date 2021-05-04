package com.example.bitcoinratemvvm;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {

    private static final String API_KEY3 = "3F2BDCB7-38F9-4961-96DF-DDB19907B8A2";
    private static final String API_KEY2 = "63F64B3B-16A5-45CC-8E2B-AEABD49D1182";
    private static final String API_KEY = "23F2CAC9-88E6-4388-9FC5-EF0DD195B9AA";
    private static final String BASE_URL = "https://rest.coinapi.io/";

    public PostRepository(){}

    public Call<Post> getCurrentValute(String valute) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        //return null;
        return retrofitAPI.getCurrentValute(valute, API_KEY2);
    }

    public Call<Post> getCallValute(String valute){
        return RetrofitInstance.getRetrofitAPI().getCurrentValute(valute,API_KEY3);
    }
}
