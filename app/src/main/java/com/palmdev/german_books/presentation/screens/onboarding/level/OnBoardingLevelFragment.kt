package com.palmdev.german_books.presentation.screens.onboarding.level

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingLevelBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.animations.AnimAppearFromLeft
import com.palmdev.german_books.presentation.animations.AnimAppearFromRight

class OnBoardingLevelFragment :
    BaseFragment<FragmentOnboardingLevelBinding>(FragmentOnboardingLevelBinding::inflate) {

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
                findNavController().navigate(R.id.onBoardingBooksFragment)
            }
        }

        Glide.with(this)
            .load(R.drawable.avatar_m_a1)
            .into(binding.imgBeginner)
        Glide.with(this)
            .load(R.drawable.avatar_m_a2)
            .into(binding.imgIntermediate)
        Glide.with(this)
            .load(R.drawable.avatar_m_b2)
            .into(binding.imgAdvanced)
    }
}