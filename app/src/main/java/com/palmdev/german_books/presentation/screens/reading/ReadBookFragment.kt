package com.palmdev.german_books.presentation.screens.reading

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.nl.translate.Translator
import com.palmdev.german_books.*
import com.palmdev.german_books.databinding.FragmentBookReadBinding
import com.palmdev.german_books.legacy.Ads
import com.palmdev.german_books.legacy.AppReview
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.dialogs.save_word.SaveWordDialogFragment
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.Languages
import com.palmdev.german_books.utils.Pagination
import com.palmdev.german_books.utils.TTS
import com.palmdev.german_books.utils.Translate
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.math.roundToInt

@AndroidEntryPoint
class ReadBookFragment : BaseFragment<FragmentBookReadBinding>(FragmentBookReadBinding::inflate) {

    private val viewModel by activityViewModels<ReadingViewModel>()
    private var mPagination: Pagination? = null
    private var mCurrentPage = 0
    private var mTotalPages = 100
    private var mReader: BufferedReader? = null
    private var mBookStringBuilder: StringBuilder? = null
    private var mBookId = 1
    private var popupWindow: PopupWindow? = null
    private var mTranslator: Translator? = null
    private val mTTS: TTS by lazy { TTS(requireContext()) }
    private var hintWasShown = false

    override val isNavigationVisible: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBookId = requireArguments().getInt(ARG_SELECTED_BOOK_ID, 1)
        viewModel.setBook(mBookId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Ads.loadInterstitialAd(requireContext())
        viewModel.updateLastBookRead()

        viewModel.currentBook.observe(viewLifecycleOwner) {
            try {
                val inputStream: InputStream =
                    requireContext().assets.open("books/text/${it.contentPath}.txt")
                initBook(inputStream)
                inputStream.close()

                setCurrentPage(it.currentPage)
            } catch (e: IOException) {
                e.message?.let { message -> Log.e(CAUGHT_ERROR, message) }
            }
        }

        initClickableText(binding.bookContent, binding.topContainer)
        initLongTextTranslation()

        viewModel.isDarkMode.observe(viewLifecycleOwner) { enabled ->
            if (enabled) {
                setDarkMode()
            } else {
                setLightMode()
            }
        }

        viewModel.fontSize.observe(viewLifecycleOwner) {
            binding.bookContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
            if (mBookStringBuilder != null) initPagination()
        }

        // Init buttons
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            Ads.showInterstitialAd(requireContext(), requireActivity())
        }

        binding.btnSettings.setOnClickListener { openSettings() }
        binding.btnWords.setOnClickListener { openSettings() }
        binding.tvSavedWordsCount.setOnClickListener { openSettings() }

        viewModel.savedWordsCount.observe(viewLifecycleOwner) {
            binding.tvSavedWordsCount.text = it.toString()
        }
    }

    private fun openSettings() {
        findNavController().navigate(
            R.id.bottomSheetFragment,
            bundleOf(ARG_SELECTED_BOOK_ID to mBookId)
        )
    }

    private fun initLongTextTranslation() {
        binding.btnTranslate.setOnClickListener {
            if (binding.bookContent.length() == 0) return@setOnClickListener
            val selectionStart = binding.bookContent.selectionStart
            val selectionEnd = binding.bookContent.selectionEnd
            if (selectionStart < 0
                || selectionEnd < 2
                || selectionStart >= selectionEnd
                || selectionEnd > binding.bookContent.length())
                return@setOnClickListener

            val selectedText =
                binding.bookContent.text.subSequence(selectionStart, selectionEnd).toString()

            if (selectedText.isNotEmpty()) {
                findNavController().navigate(
                    R.id.saveWordDialogFragment, bundleOf(
                        SaveWordDialogFragment.ARG_TEXT_TO_TRANSLATE to selectedText
                    )
                )
            } else {
                val snackbar = Snackbar.make(
                    binding.bookContent,
                    getText(R.string.selectText),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setTextColor(resources.getColor(R.color.red, null))
                snackbar.setBackgroundTint(resources.getColor(R.color.night_background, null))
                snackbar.show()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        Ads.showInterstitialAd(requireContext(), requireActivity())
        return super.onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        initTranslator()
        viewModel.updateSavedWordsCount()

        viewModel.lastReadBook.observe(viewLifecycleOwner) {
            if (it == null && !hintWasShown) {
                findNavController().navigate(R.id.hintReadingDialogFragment)
                hintWasShown = true
            }
        }
    }


    override fun onStop() {
        super.onStop()
        // Save Current Page
        viewModel.setCurrentBookPages(
            currentPage = mCurrentPage,
            totalPages = mTotalPages
        )
        viewModel.saveLastReadBook(mBookId)
    }

    private fun initTranslator() {
        Translate.createTranslator(
            sourceLanguage = Languages.learningLanguage.code,
            targetLanguage = viewModel.userLanguage.code
        ).addOnCompleteListener {
            mTranslator = Translate.getTranslator()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initClickableText(textView: TextView, header: View) {
        val gestureDetector = GestureDetector(
            textView.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                    if (!this@ReadBookFragment.isAdded) return true
                    val layout: Layout = textView.layout ?: return false
                    val line = layout.getLineForVertical(event.y.roundToInt())
                    val offset = layout.getOffsetForHorizontal(line, event.x)

                    val text = textView.text.toString()

                    val regex = Regex("\\s") // Regex pattern to match any whitespace character
                    val words = text.split(regex) // Split the text by any whitespace character

                    val wordsWithIndices =
                        words.foldIndexed(listOf<Pair<String, IntRange>>()) { index, list, word ->
                            val start =
                                if (index == 0) 0 else list[index - 1].second.last + 1 + regex.find(
                                    text,
                                    list[index - 1].second.last + 1
                                )?.range?.count()!!
                            val end = start + word.length
                            list + Pair(word, start until end)
                        }

                    var word = wordsWithIndices.find { offset in it.second }?.first ?: return false

                    word = word.replace(",", "")
                    word = word.replace(".", "")
                    word = word.replace("!", "")
                    word = word.replace("?", "")
                    word = word.replace("\"", "")
                    if (word.first() == '\'') {
                        word = word.drop(1)
                    }
                    if (word.last() == '\'') {
                        word = word.dropLast(1)
                    }

                    showPopupWindow(
                        clickedText = word,
                        parent = textView,
                        clickEvent = event,
                        headerHeight = header.height
                    )

                    return true
                }
            }
        )
        textView.setOnTouchListener { _, event ->
            return@setOnTouchListener gestureDetector.onTouchEvent(event)
        }
    }

    private fun showPopupWindow(
        clickedText: String,
        parent: View,
        clickEvent: MotionEvent,
        headerHeight: Int
    ) {
        popupWindow?.dismiss()

        val popupView = layoutInflater.inflate(R.layout.popup_translate_word, null)
        popupWindow = PopupWindow(
            popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val tvWord: TextView = popupView.findViewById(R.id.popupText)
        val tvTranslation: TextView = popupView.findViewById(R.id.popupTranslatedText)
        val btnClose: TextView = popupView.findViewById(R.id.btnClose)
        val btnSave: TextView = popupView.findViewById(R.id.btnSave)
        val loading: View = popupView.findViewById(R.id.loading)
        val btnSound: View = popupView.findViewById(R.id.btnSound)

        tvWord.text = clickedText
        mTTS.say(clickedText)
        btnSound.setOnClickListener {
            mTTS.say(clickedText)
        }
        if (mTranslator == null) initTranslator()
        mTranslator?.translate(clickedText)?.addOnSuccessListener {
            loading.visibility = View.GONE
            tvTranslation.text = it
        }
        btnClose.setOnClickListener {
            popupWindow?.dismiss()
        }
        btnSave.setOnClickListener {
            val translation = tvTranslation.text.toString()
            if (translation.isNotEmpty()) {
                viewModel.saveWord(word = clickedText, translation = translation)
            }
            popupWindow?.dismiss()
            viewModel.updateSavedWordsCount()
            val snackbar = Snackbar.make(
                binding.bookContent,
                getText(R.string.wordHasBeenSaved),
                Snackbar.LENGTH_SHORT
            )
            snackbar.setTextColor(resources.getColor(R.color.white, null))
            snackbar.setBackgroundTint(resources.getColor(R.color.night_background, null))
            snackbar.show()
        }

        popupWindow!!.contentView = popupView
        popupWindow!!.showAtLocation(parent, Gravity.NO_GRAVITY, -2000, -2000)
        popupWindow!!.contentView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        val popupHeight = popupWindow!!.contentView.measuredHeight
        val popupWidth = popupWindow!!.contentView.measuredWidth

        val x = clickEvent.x.toInt() - (popupWidth / 2)
        var y = clickEvent.y.toInt() - popupHeight + headerHeight - 25

        // Check if the popup is above the screen, if yes then show it below the word
        if (y < 0) {
            y = clickEvent.y.toInt() + popupHeight - headerHeight - 25
        }

        popupWindow!!.update(x, y, popupWidth, popupHeight)
    }

    private fun setDarkMode() {
        binding.root.setBackgroundColor(resources.getColor(R.color.black, null))
        binding.bookContent.setTextColor(resources.getColor(R.color.textColorWhite, null))
    }

    private fun setLightMode() {
        binding.root.setBackgroundColor(resources.getColor(R.color.beige, null))
        binding.bookContent.setTextColor(resources.getColor(R.color.textColorBrown, null))
    }

    private fun initBook(book: InputStream) {
        mBookStringBuilder = StringBuilder()

        try {
            mReader = BufferedReader(InputStreamReader(book))
            var line: String?
            while (mReader!!.readLine().also { line = it } != null) {
                mBookStringBuilder?.append(line)
                mBookStringBuilder?.append('\n')
            }
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Error reading file", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        } finally {
            if (mReader != null) {
                try {
                    mReader!!.close()
                } catch (e: IOException) {
                    // empty
                }
            }
        }
        initPagination()
    }

    private fun initPagination() {
        val stringBuilder = mBookStringBuilder

        binding.bookContent.viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Removing layout listener to avoid multiple calls
                binding.bookContent.viewTreeObserver.removeOnGlobalLayoutListener(this)
                mPagination = Pagination(
                    stringBuilder,
                    binding.bookContent
                )
                updatePage()
            }
        })

        binding.btnPageBack.setOnClickListener {
            mCurrentPage = if (mCurrentPage > 0) mCurrentPage - 1 else 0
            updatePage()
        }
        binding.btnPageNext.setOnClickListener {
            mPagination?.let { mTotalPages = it.totalPages - 1 }
            mCurrentPage = if (mCurrentPage < mTotalPages) mCurrentPage + 1 else mTotalPages
            updatePage()
        }
    }

    private fun setCurrentPage(page: Int) {
        mCurrentPage = page
    }

    private fun getCurrentPage(): Int {
        return mCurrentPage
    }

    @SuppressLint("SetTextI18n")
    private fun updatePage() {
        mPagination?.let { mTotalPages = it.totalPages - 1 }
        val text = mPagination?.getPageText(mCurrentPage)
        if (text != null) {
            binding.bookContent.text = text
        }

        binding.currentPage.text = "${mCurrentPage + 1} / ${(mPagination?.totalPages)}"

        if (mCurrentPage == 1) {
            activity?.let { AppReview.rateApp(it) }
        }

        if (getCurrentPage() == 0) {
            binding.btnPageBack.visibility = View.INVISIBLE
        } else {
            binding.btnPageBack.visibility = View.VISIBLE
        }
        if (mCurrentPage == mTotalPages) {
            binding.btnPageNext.visibility = View.INVISIBLE
        } else {
            binding.btnPageNext.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_SELECTED_BOOK_ID = "ARG_SELECTED_BOOK_ID"
    }
}