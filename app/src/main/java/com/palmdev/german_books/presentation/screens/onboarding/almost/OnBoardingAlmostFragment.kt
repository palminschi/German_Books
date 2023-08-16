package com.palmdev.german_books.presentation.screens.onboarding.almost

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingAlmostBinding
import com.palmdev.german_books.presentation.BaseFragment

class OnBoardingAlmostFragment :
    BaseFragment<FragmentOnboardingAlmostBinding>(FragmentOnboardingAlmostBinding::inflate) {

    override fun onBackPressed(): Boolean {
        return true
    }

    override val isNavigationVisible: Boolean
        get() = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.onBoardingExploreFragment)
        }
    }
}