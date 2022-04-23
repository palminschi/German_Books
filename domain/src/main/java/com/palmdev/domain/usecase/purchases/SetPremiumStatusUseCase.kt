package com.palmdev.domain.usecase.purchases

import com.palmdev.domain.repository.PurchasesRepository

class SetPremiumStatusUseCase(private val purchasesRepository: PurchasesRepository) {

    fun execute(boolean: Boolean) {
        purchasesRepository.setUserPremiumStatus(boolean)
    }

}