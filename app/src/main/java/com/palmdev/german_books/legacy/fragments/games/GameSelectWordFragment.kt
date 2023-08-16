package com.palmdev.german_books.legacy.fragments.games

import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameSelectWordBinding
import com.palmdev.german_books.legacy.Ads
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_TRANSLATED_WORDS
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_WORDS
import com.palmdev.german_books.utils.MyTextToSpeech
import kotlin.random.Random


class GameSelectWordFragment : Fragment(R.layout.fragment_game_select_word) {

    private lateinit var binding: FragmentGameSelectWordBinding
    private var mCurrentWordCounter = 0
    private val mArrayWords = ArrayList<String>()
    private val mArrayTranslatedWords = ArrayList<String>()
    private var mRightAnswer = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameSelectWordBinding.bind(view)

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

        binding.choice1.setOnClickListener { onSelectAnswer(it as CardView, binding.tvChoice1) }
        binding.choice2.setOnClickListener { onSelectAnswer(it as CardView, binding.tvChoice2) }
        binding.choice3.setOnClickListener { onSelectAnswer(it as CardView, binding.tvChoice3) }
        binding.btnNext.setOnClickListener {
            when (binding.tvBtnNext.text) {
                getText(R.string.next) -> {
                    mCurrentWordCounter++
                    nextWord()
                }
                getText(R.string.done) -> {
                    findNavController().navigateUp()
                    findNavController().navigateUp()
                    Ads.showInterstitialAd(requireContext(), requireActivity())
                }
            }
        }
        binding.btnSound.setOnClickListener {
            MyTextToSpeech.play(binding.tvWord.text, requireContext())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            Ads.showInterstitialAd(requireContext(), requireActivity())
        }

        // Set OnBackPressed Callback
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
            findNavController().popBackStack()
            Ads.showInterstitialAd(requireContext(), requireActivity())
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
        binding.tvWord.text = mArrayWords[mCurrentWordCounter]
        binding.progressBar.max = mArrayWords.size
        binding.progressBar.progress = mCurrentWordCounter + 1

        MyTextToSpeech.play(mArrayWords[mCurrentWordCounter], requireContext())

        binding.btnNext.visibility = View.INVISIBLE
        binding.apply {
            choice1.isClickable = true
            choice2.isClickable = true
            choice3.isClickable = true
            choice1.animate().alpha(1f)
            choice2.animate().alpha(1f)
            choice3.animate().alpha(1f)
            choice1.visibility = View.VISIBLE
            choice2.visibility = View.VISIBLE
            choice3.visibility = View.VISIBLE
            choice1.setCardBackgroundColor(resources.getColor(R.color.button_white, null))
            choice2.setCardBackgroundColor(resources.getColor(R.color.button_white, null))
            choice3.setCardBackgroundColor(resources.getColor(R.color.button_white, null))
        }
        // if this is last word
        if (mCurrentWordCounter == mArrayWords.size - 1) {
            binding.tvBtnNext.text = getText(R.string.done)
        }

        // if the word has no sentence

        // put right answer into a random position
        val randomPositionOfRightAnswer = Random.nextInt(1, 4)
        mRightAnswer = randomPositionOfRightAnswer
        // get 2 another words
        var randomPositionOfWordN1 = Random.nextInt(mArrayWords.size)
        while (randomPositionOfWordN1 == mCurrentWordCounter) {
            randomPositionOfWordN1 = Random.nextInt(mArrayWords.size)
        }
        var randomPositionOfWordN2 = Random.nextInt(mArrayWords.size)
        while (
            randomPositionOfWordN2 == mCurrentWordCounter ||
            randomPositionOfWordN2 == randomPositionOfWordN1
        ) {
            randomPositionOfWordN2 = Random.nextInt(mArrayWords.size)
        }

        when (mRightAnswer) {
            1 -> {
                binding.tvChoice1.text = mArrayTranslatedWords[mCurrentWordCounter]
                binding.tvChoice2.text = mArrayTranslatedWords[randomPositionOfWordN1]
                binding.tvChoice3.text = mArrayTranslatedWords[randomPositionOfWordN2]
            }
            2 -> {
                binding.tvChoice1.text = mArrayTranslatedWords[randomPositionOfWordN1]
                binding.tvChoice2.text = mArrayTranslatedWords[mCurrentWordCounter]
                binding.tvChoice3.text = mArrayTranslatedWords[randomPositionOfWordN2]
            }
            3 -> {
                binding.tvChoice1.text = mArrayTranslatedWords[randomPositionOfWordN1]
                binding.tvChoice2.text = mArrayTranslatedWords[randomPositionOfWordN2]
                binding.tvChoice3.text = mArrayTranslatedWords[mCurrentWordCounter]
            }
        }

    }

    private fun onSelectAnswer(cardView: CardView, textView: TextView) {
        // if answer is right
        if (textView.text == mArrayTranslatedWords[mCurrentWordCounter]) {
            val anim = ScaleAnimation(0f, 1f, 0f, 1f, 0.5f, 0.5f)
            anim.duration = 200
            binding.btnNext.animation = anim
            binding.btnNext.visibility = View.VISIBLE

            // TODO -> Animation
            binding.apply {
                choice1.isClickable = false
                choice2.isClickable = false
                choice3.isClickable = false
            }
            cardView.setCardBackgroundColor(resources.getColor(R.color.green_3, null))
        } else {
            cardView.animate().setDuration(300).alpha(0f).withEndAction {
                cardView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Firebase Event
        val bundle = Bundle()
        bundle.putString(
            FirebaseAnalytics.Param.SCREEN_NAME,
            GameSelectWordFragment().javaClass.simpleName
        )
        bundle.putString(
            FirebaseAnalytics.Param.SCREEN_CLASS,
            GameSelectWordFragment().javaClass.simpleName
        )
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


}