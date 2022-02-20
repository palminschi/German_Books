package com.palmdev.german_books.presentation.screens.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.TranslatorFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslatorFragment : Fragment() {

    private val viewModel: TranslatorViewModel by viewModel()
    private lateinit var binding: TranslatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.translator_fragment, container, false)
        binding = TranslatorFragmentBinding.bind(view)
        return view
    }

}