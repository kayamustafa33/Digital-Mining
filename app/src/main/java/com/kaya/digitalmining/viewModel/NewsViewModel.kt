package com.kaya.digitalmining.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaya.digitalmining.model.New
import com.kaya.digitalmining.model.News
import com.kaya.digitalmining.service.CryptoNewsAPIService
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val newsData = MutableLiveData<News?>()
    private val cryptoNewsAPIService = CryptoNewsAPIService()

    fun getCryptoNews() {
        viewModelScope.launch {
            val response = cryptoNewsAPIService.getNews()
            if (response.isSuccessful) {
                val responseNews = response.body()
                responseNews.let {
                    newsData.value = responseNews!!
                }
            } else {
                newsData.value = null
                Log.d("crypto-news-log","Response Error!")
            }
        }
    }


}