package com.palmdev.german_books.presentation.screens.game_write_word

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameWriteWordBinding
import com.palmdev.german_books.presentation.screens.group_of_words.GroupOfWordsFragment
import com.palmdev.german_books.utils.VoiceText
import java.util.*
import kotlin.collections.ArrayList


class GameWriteWordFragment : Fragment() {

    private lateinit var binding: FragmentGameWriteWordBinding
    private val mWordsArray = ArrayList<String>()
    private val mTranslationsArray = ArrayList<String>()
    private var mCurrentWordCounter = 0
    private lateinit var mCallback: OnBackPressedCallback
    private lateinit var mVoiceText: VoiceText
    private var mWord = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_write_word, container, false)
        binding = FragmentGameWriteWordBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVoiceText = VoiceText(requireContext())
        mVoiceText.init()

        requireArguments().getStringArrayList(GroupOfWordsFragment.ARG_WORDS_ARRAY)?.let {
            mWordsArray.addAll(it)
        }
        requireArguments().getStringArrayList(GroupOfWordsFragment.ARG_TRANSLATIONS_ARRAY)?.let {
            mTranslationsArray.addAll(it)
        }

        setOnBackPressedCallback()

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

        binding.btnSound.setOnClickListener { mVoiceText.play(mWord) }
        binding.editText.requestFocus()

        binding.btnBack.setOnClickListener {
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
        mWord = mWordsArray[mCurrentWordCounter]
        binding.progressBar.max = mWordsArray.size
        binding.progressBar.progress = mCurrentWordCounter + 1

        binding.tvTranslatedWord.text = mTranslationsArray[mCurrentWordCounter]

        binding.editText.setText("")
        binding.editText.setTextColor(Color.WHITE)
        binding.tvBtnNext.text = getText(R.string.check)

    }

    private fun checkAnswer() {
        val rightAnswer = mWordsArray[mCurrentWordCounter].lowercase(Locale.getDefault())
        val userAnswer = binding.editText.text.toString().lowercase(Locale.getDefault())

        binding.editText.setText(mWordsArray[mCurrentWordCounter])

        if (userAnswer == rightAnswer) {
            binding.editText.setTextColor(resources.getColor(R.color.green))
        } else binding.editText.setTextColor(Color.RED)

        mVoiceText.play(mWordsArray[mCurrentWordCounter])

        mCurrentWordCounter++

        binding.tvBtnNext.text =
                // if it isn't last word
            if (mCurrentWordCounter != mWordsArray.size) {
                getString(R.string.next)
            } else getString(R.string.done)


    }

    private fun setOnBackPressedCallback() {
        mCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().popBackStack()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, mCallback)
    }

}