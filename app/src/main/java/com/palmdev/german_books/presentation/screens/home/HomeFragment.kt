package com.palmdev.german_books.presentation.screens.home

import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.home_fragment) {


    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HomeFragmentBinding.bind(view)

        binding.button.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_selectBookFragment) }

    }


}