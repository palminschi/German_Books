package com.palmdev.german_books.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.palmdev.german_books.R


object AdMob {

    const val AD_TYPE_01 = "AD_TYPE_01"
    const val AD_TYPE_02 = "AD_TYPE_02"

    private var mRewardedAd: RewardedAd? = null
    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "ADS"

    fun loadRewardedAd(context: Context) {

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(context, context.getString(R.string.AD_REWARDED_ID), adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mRewardedAd = rewardedAd
                }
            }
        )
    }

    fun showRewardedVideo(
        context: Context,
        activity: Activity,
        listener: OnUserEarnedRewardListener
    ) {

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.")
            }


            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.")
                mRewardedAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.")
                mRewardedAd = null
                loadRewardedAd(context)
            }
        }

        mRewardedAd?.show(activity, listener)
    }

    fun loadInterstitialAd(context: Context) {

        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            context.getString(R.string.AD_INTERSTITIAL_ID),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )

    }

    fun showInterstitialAd(context: Context, activity: Activity) {

        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    mInterstitialAd = null
                    loadInterstitialAd(context)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(TAG, "Ad failed to show.")
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    // Called when ad is dismissed.
                }
            }
            mInterstitialAd?.show(activity)
        }
    }

    fun loadNativeAd(
        context: Context,
        root: ViewGroup
    ) {
        val view = LayoutInflater.from(context).inflate(R.layout.native_ad_type_2, null)
        root.removeAllViews()
        view.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=ispeak.german.learning.app.words.german.conversations.dialogues")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.message?.let { Log.e("TAG", it) }
            }
        }
        root.addView(view)
    }


}