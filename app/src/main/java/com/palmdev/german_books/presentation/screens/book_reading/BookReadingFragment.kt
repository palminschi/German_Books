package com.palmdev.german_books.presentation.screens.book_reading

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.data.util.Constants
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.BookReadingFragmentBinding
import com.palmdev.german_books.presentation.MainActivity
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import com.palmdev.german_books.utils.Pagination
import com.palmdev.german_books.utils.TextToClickable
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookReadingFragment : Fragment(R.layout.book_reading_fragment) {


    private lateinit var binding: BookReadingFragmentBinding
    private val viewModel: BookReadingViewModel by viewModel()

    private var mBookId = 0
    private var mBookContent: String = ""
    private var mCurrentPage = 0
    private lateinit var mPagination: Pagination
    private lateinit var mTextToClickable: TextToClickable

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AAA", "Fragment Destroyed")
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookReadingFragmentBinding.bind(view)

        viewModel.initTranslatorPreferences()
        val language = viewModel.translatorPreferences.value
        if (language?.name == Constants.SHARED_PREFS_NO_DATA){
            val dialog = TranslatorLanguagesDialogFragment(cancelable = false)
            dialog.show(
                parentFragmentManager,
                "TAG"
            )
        }else if (language != null) {
            GoogleMLKitTranslator.createTranslator("en", language.code) //TODO en -> de
        }





        // Set Book Content by ID
        viewModel.initBook(mBookId) // TODO: Get Book Id from Args
        mBookContent = viewModel.bookContent.value ?: ""
        divideTextIntoPages(
            text = mBookContent,
            textView = binding.tvBookContent
        )

        // Set the last page read
        viewModel.initCurrentPage(mCurrentPage)
        mCurrentPage = viewModel.currentPage.value ?: 0

        // Init TextToClickable
        mTextToClickable = TextToClickable()

        // Set coordinates of click for Popup window
        binding.tvBookContent.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                mTextToClickable.setCoordinates(
                    x = event.x.toInt(),
                    y = event.y.toInt()
                )
                false
            } else false
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
                updateText()
            }
        })

        binding.btnPageBack.setOnClickListener {
            mCurrentPage = if (mCurrentPage > 0) mCurrentPage - 1 else 0
            updateText()
        }
        binding.btnPageNext.setOnClickListener {
            mCurrentPage =
                if (mCurrentPage < mPagination.size() - 1) mCurrentPage + 1 else mPagination.size() - 1
            updateText()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateText() {
        val text = mPagination[mCurrentPage]
        if (text != null) {
            binding.tvBookContent.text = text
            mTextToClickable.convertTextToClickable(text, binding.tvBookContent)
        }

        binding.currentPage.text = "${mCurrentPage + 1} / ${(mPagination.size())}"

        if (mCurrentPage == 0) {
            binding.btnPageBack.visibility = View.INVISIBLE
        } else {
            binding.btnPageBack.visibility = View.VISIBLE
        }
        if (mCurrentPage == mPagination.size() - 1) {
            binding.btnPageNext.visibility = View.INVISIBLE
        } else {
            binding.btnPageNext.visibility = View.VISIBLE
        }
    }
}