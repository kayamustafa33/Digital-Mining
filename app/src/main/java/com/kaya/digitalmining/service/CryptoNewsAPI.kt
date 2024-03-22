package com.kaya.digitalmining.service

import com.kaya.digitalmining.model.News
import retrofit2.Response
import retrofit2.http.GET

interface CryptoNewsAPI {

    @GET("crypto-json/crypto_news.json")
    suspend fun getCryptoNews() : Response<News>

}