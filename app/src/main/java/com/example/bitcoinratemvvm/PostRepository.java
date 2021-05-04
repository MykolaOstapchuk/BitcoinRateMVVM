package com.example.bitcoinratemvvm;

import retrofit2.Call;

public class PostRepository {

    private static final String API_KEY3 = "3F2BDCB7-38F9-4961-96DF-DDB19907B8A2";
    private static final String API_KEY2 = "63F64B3B-16A5-45CC-8E2B-AEABD49D1182";
    private static final String API_KEY = "23F2CAC9-88E6-4388-9FC5-EF0DD195B9AA";


    public Call<Post> getCallValute(String valute) {
        return RetrofitInstance.getRetrofitAPI().getCurrentValute(valute, API_KEY3);
    }
}
