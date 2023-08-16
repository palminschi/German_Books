package com.palmdev.german_books.presentation.screens.onboarding.explore

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.data.storage.UserStorage
import com.palmdev.german_books.databinding.FragmentOnboardingExploreBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.animations.AnimAppearFromRight

class OnBoardingExploreFragment :
    BaseFragment<FragmentOnboardingExploreBinding>(FragmentOnboardingExploreBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false


    private var mCounter = 0

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserStorage(requireContext()).setIsFirstOpen(false)
        val images = listOf(
            R.drawable.img_onboarding_part1,
            R.drawable.img_onboarding_part2,
            R.drawable.img_onboarding_part3,
        )


        val texts = listOf(
            getString(R.string.onBoardingExplore1),
            getString(R.string.onBoardingExplore2),
            getString(R.string.onBoardingExplore3),
        )

        Glide.with(this)
            .load(images[mCounter])
            .into(binding.img)
        binding.tvTitle.text = texts[mCounter]
        anim()

        binding.btnNext.setOnClickListener {
            mCounter++
            if (mCounter == texts.size - 1) {
                binding.btnNext.textButton = getString(R.string.start)
            }

            if (mCounter >= texts.size) {
                findNavController().navigate(R.id.onBoardingPaywallTrialFragment)
                return@setOnClickListener
            }

            Glide.with(this)
                .load(images[mCounter])
                .into(binding.img)
            binding.tvTitle.text = texts[mCounter]
            anim()
        }
    }

    private fun anim() {
        AnimAppearFromRight(binding.img)
        AnimAppearFromRight(binding.tvTitle)
    }

}