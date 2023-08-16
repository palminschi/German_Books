package com.palmdev.german_books.presentation.screens.onboarding.start

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingStartBinding
import com.palmdev.german_books.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingStartFragment :
    BaseFragment<FragmentOnboardingStartBinding>(FragmentOnboardingStartBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(R.drawable.img_onboarding_start)
            .into(binding.img)

        Glide.with(this)
            .load(R.mipmap.ic_launcher)
            .into(binding.appIcon)


        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.onBoardingLanguagesFragment)
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}