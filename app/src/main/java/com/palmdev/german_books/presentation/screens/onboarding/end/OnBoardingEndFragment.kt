package com.palmdev.german_books.presentation.screens.onboarding.end

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingEndBinding
import com.palmdev.german_books.presentation.BaseFragment

class OnBoardingEndFragment : BaseFragment<FragmentOnboardingEndBinding>(FragmentOnboardingEndBinding::inflate) {

    override val isNavigationVisible: Boolean
        get() = false

    override fun onBackPressed(): Boolean {
        findNavController().navigate(R.id.homeFragment)
        return true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTest.setOnClickListener {
            //TODO: DELETE TEST findNavController().navigate(R.id.testsFragment, bundleOf(Tests.EXAM_OR_QUICK_TEST to Tests.QUICK_TEST))
        }
        binding.btnBooks.setOnClickListener {
            findNavController().navigate(R.id.booksFragment)
        }
        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}