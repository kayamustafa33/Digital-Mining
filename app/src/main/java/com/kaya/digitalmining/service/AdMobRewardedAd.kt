package com.kaya.digitalmining.service

import android.app.Activity
import android.content.Context

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.kaya.digitalmining.contracts.AdmobImplementation


class AdMobRewardedAd : AdmobImplementation {

    var rewardedAd: RewardedAd? = null

   override fun loadRewardedAd(context: Context, callBack : (Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                callBack(false)
            }
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
                callBack(true)
            }
        })
    }

    fun showRewardedAd(activity: Activity, callBack: (Boolean) -> Unit) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {}

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {}

            override fun onAdShowedFullScreenContent() {}
        }

        val adCallback = OnUserEarnedRewardListener { _ ->
            callBack(true)
        }

        rewardedAd?.show(activity, adCallback)
    }





}