package com.palmdev.german_books.presentation.screens.book_reading

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.data.util.Constants
import com.palmdev.german_books.MainActivity
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.BookReadingFragmentBinding
import com.palmdev.german_books.presentation.screens.books.BooksFragment
import com.palmdev.german_books.presentation.screens.dialog_save_word.SaveWordDialogFragment
import com.palmdev.german_books.presentation.screens.dialog_translator_languages.TranslatorLanguagesDialogFragment
import com.palmdev.german_books.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookReadingFragment : Fragment(R.layout.book_reading_fragment) {


    private lateinit var binding: BookReadingFragmentBinding
    private val viewModel: BookReadingViewModel by viewModel()

    private var mBookId = 0
    private var mBookContent: String = ""
    private var mCurrentPage = 0
    private lateinit var mPagination: Pagination


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookReadingFragmentBinding.bind(view)
        // Load Ad
        AdMob.loadInterstitialAd(requireContext())

        val translatorLanguage = viewModel.translatorPreferences.value

        if (translatorLanguage?.name == Constants.SHARED_PREFS_NO_DATA) {
            val dialog = TranslatorLanguagesDialogFragment()
            dialog.isCancelable = false
            dialog.show(
                parentFragmentManager,
                "TAG"
            )
        } else if (translatorLanguage != null) {
            GoogleMLKitTranslator.createTranslator("de", translatorLanguage.code)
        }

        // Set Book Content by ID
        mBookId = requireArguments().getInt(BooksFragment.ARG_OPENED_BOOK)
        viewModel.initBook(mBookId)
        mBookContent = viewModel.bookContent.value ?: ""
        divideTextIntoPages(
            text = mBookContent,
            textView = binding.tvBookContent
        )

        // Set the last page read
        viewModel.initCurrentPage(bookId = mBookId)
        mCurrentPage = viewModel.currentPage.value ?: 0

        // Empty Text Selection Callback
        binding.tvBookContent.customSelectionActionModeCallback = EmptyTextSelection()


        setButtons()
    }

    private fun setButtons() {

        // Button translate the text
        binding.btnTranslate.setOnClickListener {
            val selectionStart = binding.tvBookContent.selectionStart
            val selectionEnd = binding.tvBookContent.selectionEnd
            val selectedText = binding.tvBookContent.text.substring(
                startIndex = selectionStart,
                endIndex = selectionEnd
            )

            if (selectedText.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toastSelectText),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val dialog = SaveWordDialogFragment(
                    word = selectedText
                )
                dialog.show(parentFragmentManager, "TAG")
            }
        }
        // Button pronunciation
        val voiceText = VoiceText(requireContext())
        voiceText.init()

        binding.btnPronounce.setOnClickListener {
            val selectionStart = binding.tvBookContent.selectionStart
            val selectionEnd = binding.tvBookContent.selectionEnd
            val selectedText = binding.tvBookContent.text.substring(
                startIndex = selectionStart,
                endIndex = selectionEnd
            )

            if (selectedText.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toastSelectText),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                voiceText.play(selectedText)
            }
        }
        // Button back
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            if (viewModel.userPremiumStatus.value == false) AdMob.showInterstitialAd(
                requireContext(),
                requireActivity()
            )
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
            if (viewModel.userPremiumStatus.value == false) AdMob.showInterstitialAd(
                requireContext(),
                requireActivity()
            )
        }

        // Button settings
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(
                R.id.action_bookReadingFragment_to_readingBottomSheetFragment,
                bundleOf(BOOK_ID to mBookId)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        MainActivity.hideBottomNavigation()

    }

    override fun onStop() {
        super.onStop()
        MainActivity.showBottomNavigation()
        // Save Reading Progress (the last page read)
        viewModel.saveReadingProgress(
            bookId = mBookId,
            currentPage = mCurrentPage,
            totalPages = mPagination.size() - 1
        )
        viewModel.saveLastBookRead(
            bookId = mBookId
        )
    }


    private fun divideTextIntoPages(text: String, textView: TextView) {
        textView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Removing layout listener to avoid multiple calls
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mPagination = Pagination(
                    text,
                    textView.width,
                    textView.height,
                    textView.paint,
                    textView.lineSpacingMultiplier,
                    textView.lineSpacingExtra,
                    textView.includeFontPadding
                )
                setPage()
            }
        })

        binding.btnPageBack.setOnClickListener {
            mCurrentPage = if (mCurrentPage > 0) mCurrentPage - 1 else 0
            setPage()
        }
        binding.btnPageNext.setOnClickListener {
            mCurrentPage =
                if (mCurrentPage < mPagination.size() - 1) mCurrentPage + 1 else mPagination.size() - 1
            setPage()
        }
    }

    private fun setPage() {
        binding.tvBookContent.text = mPagination[mCurrentPage] ?: ""

        val currentPage = "${mCurrentPage + 1} / ${(mPagination.size())}"
        binding.currentPage.text = currentPage

        binding.btnPageBack.visibility =
            if (mCurrentPage == 0) View.INVISIBLE
            else View.VISIBLE

        binding.btnPageNext.visibility =
            if (mCurrentPage == mPagination.size() - 1) View.INVISIBLE
            else View.VISIBLE
    }

    companion object {
        const val BOOK_ID = "BOOK_ID"
    }
}