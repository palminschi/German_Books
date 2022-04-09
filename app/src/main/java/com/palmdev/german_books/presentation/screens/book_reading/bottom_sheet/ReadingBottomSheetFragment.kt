package com.palmdev.german_books.presentation.screens.book_reading.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ReadingBottomSheetFragmentBinding
import com.palmdev.german_books.presentation.screens.book_reading.BookReadingFragment
import com.palmdev.german_books.presentation.screens.dialog_translator_languages.TranslatorLanguagesDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReadingBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: ReadingBottomSheetViewModel by viewModel()
    private lateinit var binding: ReadingBottomSheetFragmentBinding
    private var mBookID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.reading_bottom_sheet_fragment, container, false)
        binding = ReadingBottomSheetFragmentBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBookID = requireArguments().getInt(BookReadingFragment.BOOK_ID, 0)

        // Button Saved Words
        viewModel.numberOfSavedWords.observe(viewLifecycleOwner) {
            binding.numberOfSelectedWords.text = it.toString()
        }
        binding.lSavedWords.setOnClickListener {
            findNavController().navigate(R.id.wordsFragment)
        }

        // Button translator language
        viewModel.translatorLanguage.observe(viewLifecycleOwner){
            binding.tvLanguage.text = it.name
        }
        binding.lTranslatorLang.setOnClickListener {
            val dialog = TranslatorLanguagesDialogFragment()
            dialog.show(parentFragmentManager, "TAG")
        }

        // Button Add to favorites
        viewModel.getFavoriteStatus(mBookID)
        viewModel.favoriteStatus.observe(viewLifecycleOwner) {
            binding.toggleLike.isChecked = it
        }
        binding.toggleLike.setOnClickListener {
            viewModel.setFavoriteStatus(bookId = mBookID, binding.toggleLike.isChecked)
        }
    }

}