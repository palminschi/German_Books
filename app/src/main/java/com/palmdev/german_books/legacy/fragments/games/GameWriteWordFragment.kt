package com.palmdev.german_books.legacy.fragments.games

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameWriteWordBinding
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_TRANSLATED_WORDS
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_WORDS
import com.palmdev.german_books.utils.MyTextToSpeech
import java.util.Locale

class GameWriteWordFragment : Fragment(R.layout.fragment_game_write_word) {

    private lateinit var binding: FragmentGameWriteWordBinding
    private var mCurrentWordCounter = 0
    private val mArrayWords = ArrayList<String>()
    private val mArrayTranslatedWords = ArrayList<String>()
    private lateinit var mWord: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameWriteWordBinding.bind(view)

        // Get data from arguments
        requireArguments().getStringArrayList(ARRAY_WORDS)?.let {
            mArrayWords.addAll(it)
        }
        requireArguments().getStringArrayList(ARRAY_TRANSLATED_WORDS)?.let {
            mArrayTranslatedWords.addAll(it)
        }

        updateContent()

        binding.btnNext.setOnClickListener {
            when (binding.tvBtnNext.text) {
                getString(R.string.check) -> {
                    checkAnswer()
                }
                getString(R.string.next) -> {
                    nextWord()
                }
                getString(R.string.done) -> {
                    findNavController().navigateUp()
                    findNavController().navigateUp()
                }
            }
        }

        binding.btnSound.setOnClickListener { MyTextToSpeech.play(mWord, requireContext()) }
        binding.editText.requestFocus()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }

        // Set OnBackPressed Callback
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
    }

    private fun nextWord() {
        binding.bottomContainer.animate()
            // disappearing
            .translationX(-1000f).rotation(-90f).setDuration(200).withEndAction {
                updateContent()
                // appearing
                binding.bottomContainer.animate().translationX(0f).rotation(0f).duration = 150
            }
        binding.topContainer.animate()
            // disappearing
            .translationX(-1000f).rotation(90f).setDuration(200).withEndAction {
                updateContent()
                // appearing
                binding.topContainer.animate().translationX(0f).rotation(0f).duration = 150
            }
    }

    private fun updateContent() {
        mWord = mArrayWords[mCurrentWordCounter]
        binding.progressBar.max = mArrayWords.size
        binding.progressBar.progress = mCurrentWordCounter + 1

        binding.tvTranslatedWord.text = mArrayTranslatedWords[mCurrentWordCounter]

        binding.tvPhrase.text = ""

        binding.tvPhrase.visibility = View.GONE
        binding.editText.setText("")
        binding.editText.setTextColor(Color.BLACK)
        binding.tvBtnNext.text = getText(R.string.check)

    }

    private fun checkAnswer() {
        val rightAnswer = mArrayWords[mCurrentWordCounter].lowercase(Locale.getDefault())
        val userAnswer = binding.editText.text.toString().lowercase(Locale.getDefault())

        binding.editText.setText(mArrayWords[mCurrentWordCounter])

        if (userAnswer == rightAnswer) {
            binding.editText.setTextColor(resources.getColor(R.color.green))
        } else binding.editText.setTextColor(Color.RED)

        binding.tvPhrase.visibility = View.VISIBLE
        MyTextToSpeech.play(mArrayWords[mCurrentWordCounter], requireContext())

        mCurrentWordCounter++

        binding.tvBtnNext.text =
                // if it isn't last word
            if (mCurrentWordCounter != mArrayWords.size) {
                getString(R.string.next)
            } else getString(R.string.done)


    }

    override fun onResume() {
        super.onResume()
        // Firebase Event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, GameWriteWordFragment().javaClass.simpleName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, GameWriteWordFragment().javaClass.simpleName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}