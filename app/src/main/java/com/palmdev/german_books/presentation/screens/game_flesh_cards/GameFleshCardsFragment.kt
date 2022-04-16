package com.palmdev.german_books.presentation.screens.game_flesh_cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameFleshCardsBinding
import com.palmdev.german_books.presentation.screens.group_of_words.GroupOfWordsFragment
import com.palmdev.german_books.utils.VoiceText

class GameFleshCardsFragment : Fragment() {

    private lateinit var binding: FragmentGameFleshCardsBinding
    private val mWordsArray = ArrayList<String>()
    private val mTranslationsArray = ArrayList<String>()
    private var mCurrentWordCounter = 0
    private lateinit var mCallback: OnBackPressedCallback
    private lateinit var mVoiceText: VoiceText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_flesh_cards, container, false)
        binding = FragmentGameFleshCardsBinding.bind(view)
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

        updateContent()

        setOnBackPressedCallback()

        binding.btnNext.setOnClickListener {
            when (binding.tvBtnNext.text) {
                getString(R.string.show) -> {
                    mCurrentWordCounter++
                    showWord()
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
        binding.btnSound.setOnClickListener {
            mVoiceText.play(binding.tvWord.text.toString())
        }

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
        binding.tvWord.text = mWordsArray[mCurrentWordCounter]
        binding.tvTranslatedWord.text = mTranslationsArray[mCurrentWordCounter]
        binding.progressBar.max = mWordsArray.size
        binding.progressBar.progress = mCurrentWordCounter + 1
        hideWord()

        binding.tvBtnNext.text = getString(R.string.show)

        mVoiceText.play(binding.tvWord.text.toString())
    }

    private fun hideWord() {
        binding.tvTranslatedWord.visibility = View.GONE
        binding.hiddenText.visibility = View.VISIBLE
    }

    private fun showWord() {
        binding.hiddenText.visibility = View.GONE
        binding.tvTranslatedWord.visibility = View.VISIBLE

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