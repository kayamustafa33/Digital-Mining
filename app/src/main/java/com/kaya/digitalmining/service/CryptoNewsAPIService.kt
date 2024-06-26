package com.kaya.digitalmining.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoNewsAPIService {

    private val BASE_URL = "https://erhansennx.github.io/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CryptoNewsAPI::class.java)

    suspend fun getNews() = api.getCryptoNews()

}