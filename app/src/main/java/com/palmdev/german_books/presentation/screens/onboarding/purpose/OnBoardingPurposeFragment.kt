package com.palmdev.german_books.presentation.screens.onboarding.purpose

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingPurposeBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.animations.AnimAppearFromLeft
import com.palmdev.german_books.presentation.animations.AnimAppearFromRight

class OnBoardingPurposeFragment :
    BaseFragment<FragmentOnboardingPurposeBinding>(FragmentOnboardingPurposeBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false
    private val options by lazy {
        listOf(
            binding.option1,
            binding.option2,
            binding.option3,
            binding.option4,
            binding.option5,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimAppearFromLeft(options[0])
        AnimAppearFromRight(options[1])
        AnimAppearFromLeft(options[2])
        AnimAppearFromRight(options[3])
        AnimAppearFromLeft(options[4])

        options.forEach {
            it.setOnClickListener {
                findNavController().navigate(R.id.onBoardingLevelFragment)
            }
        }
    }
}