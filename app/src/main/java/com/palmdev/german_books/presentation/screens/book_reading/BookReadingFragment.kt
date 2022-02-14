package com.palmdev.german_books.presentation.screens.book_reading

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.BookReadingFragmentBinding

class BookReadingFragment : Fragment(R.layout.book_reading_fragment) {

    private lateinit var binding: BookReadingFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BookReadingFragmentBinding.bind(view)

    }
}