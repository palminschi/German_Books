package com.palmdev.domain.usecase.purchases

import com.palmdev.domain.repository.PurchasesRepository

class GetPremiumStatusUseCase(private val purchasesRepository: PurchasesRepository) {

    fun execute(): Boolean {
        return purchasesRepository.getUserPremiumStatus()
    }

}