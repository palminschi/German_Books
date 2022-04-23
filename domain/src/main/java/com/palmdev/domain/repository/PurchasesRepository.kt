package com.palmdev.domain.repository

interface PurchasesRepository {

    fun getUserPremiumStatus(): Boolean

    fun setUserPremiumStatus(boolean: Boolean)

}