package com.palmdev.german_books.presentation.screens.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.domain.model.Book
import com.palmdev.domain.model.Word
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


        viewModel.getGroups()
        val words = arrayListOf<Word>()
        binding.getLast.setOnClickListener {
            viewModel.getWords()
        }
        binding.addWord.setOnClickListener {
            viewModel.addWord()
        }
        viewModel.words.observe(
            viewLifecycleOwner
        ) {
            words.addAll(it)
            if (!it.isNullOrEmpty()) {
                val lastId = words.last().id
                val lastGroup = words.last().group
                binding.textView.text = "ID: $lastId \n Group: $lastGroup"
            }
        }

        /*viewModel.groups.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()) {
                binding.textView.text = it.last().numberOfWords.toString()
            }
        }*/

    }


}