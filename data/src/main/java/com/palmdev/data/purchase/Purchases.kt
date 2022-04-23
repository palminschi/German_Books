package com.palmdev.data.purchase

interface Purchases {

    fun getUserPremiumStatus(): Boolean

    fun setUserPremiumStatus(boolean: Boolean)

}