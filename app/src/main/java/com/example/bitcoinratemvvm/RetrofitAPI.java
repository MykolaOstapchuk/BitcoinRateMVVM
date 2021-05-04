package com.example.bitcoinratemvvm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("v1/exchangerate/BTC/{select_valute}")
    Call<Post> getCurrentValute(
            @Path("select_valute") String current_valute,
            @Query("apikey") String apikey);
}
