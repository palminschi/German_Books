package com.palmdev.german_books.presentation.screens.game_select_word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentGameSelectWordBinding
import com.palmdev.german_books.presentation.screens.group_of_words.GroupOfWordsFragment
import com.palmdev.german_books.utils.VoiceText
import kotlin.random.Random

class GameSelectWordFragment : Fragment() {

    private lateinit var binding: FragmentGameSelectWordBinding
    private val mWordsArray = ArrayList<String>()
    private val mTranslationsArray = ArrayList<String>()
    private var mCurrentWordCounter = 0
    private lateinit var mCallback: OnBackPressedCallback
    private lateinit var mVoiceText: VoiceText
    private var mRightAnswer = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_select_word, container, false)
        binding = FragmentGameSelectWordBinding.bind(view)
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
        binding.progressBar.max = mWordsArray.size
        binding.progressBar.progress = mCurrentWordCounter + 1

        mVoiceText.play(mWordsArray[mCurrentWordCounter])

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
            choice1.setCardBackgroundColor(resources.getColor(R.color.background_second))
            choice2.setCardBackgroundColor(resources.getColor(R.color.background_second))
            choice3.setCardBackgroundColor(resources.getColor(R.color.background_second))
        }
        // if this is last word
        if (mCurrentWordCounter == mWordsArray.size - 1) {
            binding.tvBtnNext.text = getText(R.string.done)
        }

        // put right answer into a random position
        val randomPositionOfRightAnswer = Random.nextInt(1, 4)
        mRightAnswer = randomPositionOfRightAnswer
        // get 2 another words
        var randomPositionOfWordN1 = Random.nextInt(mWordsArray.size)
        while (randomPositionOfWordN1 == mCurrentWordCounter) {
            randomPositionOfWordN1 = Random.nextInt(mWordsArray.size)
        }
        var randomPositionOfWordN2 = Random.nextInt(mWordsArray.size)
        while (
            randomPositionOfWordN2 == mCurrentWordCounter ||
            randomPositionOfWordN2 == randomPositionOfWordN1
        ) {
            randomPositionOfWordN2 = Random.nextInt(mWordsArray.size)
        }

        when (mRightAnswer) {
            1 -> {
                binding.tvChoice1.text = mTranslationsArray[mCurrentWordCounter]
                binding.tvChoice2.text = mTranslationsArray[randomPositionOfWordN1]
                binding.tvChoice3.text = mTranslationsArray[randomPositionOfWordN2]
            }
            2 -> {
                binding.tvChoice1.text = mTranslationsArray[randomPositionOfWordN1]
                binding.tvChoice2.text = mTranslationsArray[mCurrentWordCounter]
                binding.tvChoice3.text = mTranslationsArray[randomPositionOfWordN2]
            }
            3 -> {
                binding.tvChoice1.text = mTranslationsArray[randomPositionOfWordN1]
                binding.tvChoice2.text = mTranslationsArray[randomPositionOfWordN2]
                binding.tvChoice3.text = mTranslationsArray[mCurrentWordCounter]
            }
        }

    }

    private fun onSelectAnswer(cardView: CardView, textView: TextView) {
        // if answer is right
        if (textView.text == mTranslationsArray[mCurrentWordCounter]) {
            val anim = ScaleAnimation(0f, 1f, 0f, 1f, 0.5f, 0.5f)
            anim.duration = 200
            binding.btnNext.animation = anim
            binding.btnNext.visibility = View.VISIBLE

            binding.apply {
                choice1.isClickable = false
                choice2.isClickable = false
                choice3.isClickable = false
            }
            cardView.setCardBackgroundColor(resources.getColor(R.color.green_2))
        } else {
            cardView.animate().setDuration(300).alpha(0f).withEndAction {
                cardView.visibility = View.INVISIBLE
            }
        }
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