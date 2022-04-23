package com.palmdev.german_books.presentation.screens.shop

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.domain.usecase.purchases.GetPremiumStatusUseCase
import com.palmdev.german_books.utils.InAppPurchases

class ShopViewModel(
    private val getPremiumStatusUseCase: GetPremiumStatusUseCase
) : ViewModel() {

    private val _premiumStatus = MutableLiveData<Boolean>()
    val premiumStatus: LiveData<Boolean> = _premiumStatus

    fun buyPremium(context: Context, activity: Activity){
        InAppPurchases(context, activity).buyPremium()
    }

    fun initPremiumStatus(){
        _premiumStatus.value = getPremiumStatusUseCase.execute()
    }
}