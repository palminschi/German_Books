package com.palmdev.data.repository

import com.palmdev.data.purchase.Purchases
import com.palmdev.domain.repository.PurchasesRepository

class PurchasesRepositoryImpl(private val purchases: Purchases) : PurchasesRepository {

    override fun getUserPremiumStatus(): Boolean {
        return purchases.getUserPremiumStatus()
    }

    override fun setUserPremiumStatus(boolean: Boolean) {
        purchases.setUserPremiumStatus(boolean)
    }

}