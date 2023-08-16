package com.palmdev.german_books.presentation.screens.settings.our_apps

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.databinding.FragmentOtherLanguagesBinding
import com.palmdev.german_books.presentation.BaseFragment

class OtherLanguagesFragment :
    BaseFragment<FragmentOtherLanguagesBinding>(FragmentOtherLanguagesBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val adapter = AppsAdapter(requireContext(), OtherApps().all)
        binding.gridView.adapter = adapter
    }
}