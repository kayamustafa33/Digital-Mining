package com.kaya.digitalmining.contracts

import android.content.Context

interface AdmobImplementation {
    fun loadRewardedAd(context: Context, callBack : (Boolean) -> Unit)

}