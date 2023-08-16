package com.palmdev.german_books.presentation.screens.onboarding.books

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingBooksBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.animations.AnimAppearFromBottom

class OnBoardingBooksFragment :
    BaseFragment<FragmentOnboardingBooksBinding>(FragmentOnboardingBooksBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimAppearFromBottom(binding.buttonPanel)

        binding.btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.onBoardingAlmostFragment)
        }
    }
}