package com.palmdev.german_books.presentation.screens.onboarding.time

import android.os.Bundle
import android.view.View
import com.palmdev.german_books.databinding.FragmentOnboardingTimeBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.animations.AnimAppearFromLeft
import com.palmdev.german_books.presentation.animations.AnimAppearFromRight

class OnBoardingTimeFragment :
    BaseFragment<FragmentOnboardingTimeBinding>(FragmentOnboardingTimeBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        return true
    }

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
                //findNavController().navigate()
            }
        }
    }
}