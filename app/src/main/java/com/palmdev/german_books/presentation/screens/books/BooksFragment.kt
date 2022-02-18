package com.palmdev.german_books.presentation.screens.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.SelectBookFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : Fragment(R.layout.select_book_fragment) {



    private val viewModel: BooksViewModel by viewModel()
    private lateinit var binding: SelectBookFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SelectBookFragmentBinding.bind(view)

        binding.button2.setOnClickListener { findNavController().navigate(R.id.action_selectBookFragment_to_bookReadingFragment) }

    }


}