package com.palmdev.german_books.presentation.screens.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.WordsFragmentBinding
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class WordsFragment : Fragment() {

    private val viewModel: WordsViewModel by viewModel()
    private lateinit var binding: WordsFragmentBinding
    private val mAdapter = WordsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.words_fragment, container, false)
        binding = WordsFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init Recycler View
        binding.recView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recView.adapter = mAdapter
        viewModel.groupsOfWords.observe(viewLifecycleOwner) {
            mAdapter.addGroups(it)
        }

        binding.btnSaveWord.setOnClickListener {
            val dialog = SaveWordDialogFragment()
            dialog.show(parentFragmentManager, "TAG")
        }
    }

}