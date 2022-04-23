package com.palmdev.data.purchase

import android.content.Context
import com.palmdev.data.util.Constants

class PurchasesImpl(private val context: Context) : Purchases {

    private val mSharedPrefs =
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun setUserPremiumStatus(boolean: Boolean) {
        mSharedPrefs.edit().putBoolean(Constants.USER_PREMIUM_STATUS, boolean).apply()
    }

    override fun getUserPremiumStatus(): Boolean {
        return mSharedPrefs.getBoolean(Constants.USER_PREMIUM_STATUS, false)
    }
}