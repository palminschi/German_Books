package com.palmdev.german_books.presentation.screens.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.WordsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WordsFragment : Fragment() {

    private val viewModel: WordsViewModel by viewModel()
    private lateinit var binding: WordsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.words_fragment, container, false)
        binding = WordsFragmentBinding.bind(view)
        return view
    }


}