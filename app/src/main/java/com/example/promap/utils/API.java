package com.example.promap.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static final String BASE_URL = "";
    private final InterfaceAPI interfaceAPI;

    public API() {
        interfaceAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(InterfaceAPI.class);
    }
}
