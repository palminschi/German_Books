package com.palmdev.german_books.domain.usecases

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.QueryPurchasesParams
import com.palmdev.german_books.domain.repository.UserRepository
import javax.inject.Inject

class CheckSubscriptionUseCase @Inject constructor(private val context: Context, private val userRepository: UserRepository) {

    operator fun invoke() {
        val billingClient = BillingClient.newBuilder(context).enablePendingPurchases()
            .setListener { _: BillingResult?, _: List<Purchase?>? -> {} }
            .build()
        val finalBillingClient: BillingClient = billingClient
        billingClient.startConnection(object : BillingClientStateListener {

            override fun onBillingServiceDisconnected() = Unit

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    finalBillingClient.queryPurchasesAsync(
                        QueryPurchasesParams.newBuilder()
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    ) { billingResult1: BillingResult, list: List<Purchase> ->

                        if (billingResult1.responseCode == BillingClient.BillingResponseCode.OK) {

                            if (list.isNotEmpty()) {
                                val purchasedItem =
                                    list.find { it.purchaseState == Purchase.PurchaseState.PURCHASED }

                                if (purchasedItem == null) {
                                    // Check if user has inapp
                                    finalBillingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder()
                                        .setProductType(BillingClient.ProductType.INAPP)
                                        .build(), mPurchasesUpdatedListener)
                                } else {
                                    userRepository.setPremiumStatus(true)
                                    Log.d("AAA", "$purchasedItem")
                                }
                            } else {
                                finalBillingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder()
                                    .setProductType(BillingClient.ProductType.INAPP)
                                    .build(), mPurchasesUpdatedListener)
                            }
                        }
                    }
                }
            }
        })
    }

    private val mPurchasesUpdatedListener = PurchasesResponseListener { _, purchases ->
        if (purchases.isNotEmpty()) {
            val purchasedItem = purchases.find { it.purchaseState == Purchase.PurchaseState.PURCHASED }
            if (purchasedItem == null) {
                userRepository.setPremiumStatus(false)
            } else {
                userRepository.setPremiumStatus(true)
            }
        } else {
            userRepository.setPremiumStatus(false)
        }
    }
}