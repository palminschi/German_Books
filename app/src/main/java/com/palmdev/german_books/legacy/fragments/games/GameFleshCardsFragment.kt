package com.palmdev.german_books.legacy.fragments.games

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameFleshCardsBinding
import com.palmdev.german_books.legacy.Ads
import com.palmdev.german_books.utils.MyTextToSpeech


class GameFleshCardsFragment : Fragment(R.layout.fragment_game_flesh_cards) {

    private lateinit var binding: FragmentGameFleshCardsBinding
    private var mCurrentWordCounter = 0
    private val mArrayWords = ArrayList<String>()
    private val mArrayTranslatedWords = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameFleshCardsBinding.bind(view)

        // Load Ad
        Ads.loadInterstitialAd(requireContext())

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
                    Ads.showInterstitialAd(requireContext(), requireActivity())
                }
            }
        }

        binding.btnSound.setOnClickListener {
            MyTextToSpeech.play(binding.tvWord.text.toString(),requireContext())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            Ads.showInterstitialAd(requireContext(), requireActivity())
        }

        // Set OnBackPressed Callback
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
            findNavController().navigateUp()
            Ads.showInterstitialAd(requireContext(), requireActivity())
        }
    }

    private fun nextWord(){
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
        binding.tvWord.text = mArrayWords[mCurrentWordCounter]
        binding.tvTranslatedWord.text = mArrayTranslatedWords[mCurrentWordCounter]

        binding.progressBar.max = mArrayWords.size
        binding.progressBar.progress = mCurrentWordCounter + 1
        hideWord()
        // if the word has no sentences

        binding.tvPhrase.visibility = View.GONE

        binding.tvBtnNext.text = getString(R.string.show)

        MyTextToSpeech.play(binding.tvWord.text.toString(),requireContext())
    }

    private fun showWord() {
        binding.hiddenText.visibility = View.GONE
        binding.tvTranslatedWord.visibility = View.VISIBLE

        binding.tvBtnNext.text =
                // if it isn't last word
            if (mCurrentWordCounter != mArrayWords.size) {
                getString(R.string.next)
            } else getString(R.string.done)
    }

    private fun hideWord() {
        binding.tvTranslatedWord.visibility = View.GONE
        binding.hiddenText.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        // Firebase Event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, GameFleshCardsFragment().javaClass.simpleName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, GameFleshCardsFragment().javaClass.simpleName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    companion object {
        const val ARRAY_WORDS = "ARRAY_WORDS"
        const val ARRAY_TRANSLATED_WORDS = "ARRAY_TRANSLATED_WORDS"
    }
}