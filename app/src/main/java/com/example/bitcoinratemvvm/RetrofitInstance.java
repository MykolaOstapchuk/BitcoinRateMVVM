package com.example.bitcoinratemvvm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://rest.coinapi.io/";

    private  static Retrofit retrofit;
    private  static RetrofitAPI retrofitAPI;

    private static class LazeHolder{
        public static final Retrofit RETROFIT_INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        public static final RetrofitAPI RETROFIT_API_INSTANCE =
                getRetrofitInstance().create(RetrofitAPI.class);
    }

    public static Retrofit getRetrofitInstance(){
        return LazeHolder.RETROFIT_INSTANCE;
    }

    public static RetrofitAPI getRetrofitApiInstance(){
        return LazeHolder.RETROFIT_API_INSTANCE;
    }



    public static synchronized Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static synchronized RetrofitAPI getRetrofitAPI(){
        if(retrofitAPI == null){
            if(retrofit == null)
                retrofit = getRetrofit();
            retrofitAPI = retrofit.create(RetrofitAPI.class);
        }
        return retrofitAPI;
    }
}
