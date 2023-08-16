package com.palmdev.german_books.presentation.screens.words.saved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentSavedWordsBinding
import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_TRANSLATED_WORDS
import com.palmdev.german_books.legacy.fragments.games.GameFleshCardsFragment.Companion.ARRAY_WORDS
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.TTS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedWordsFragment : BaseFragment<FragmentSavedWordsBinding>(FragmentSavedWordsBinding::inflate) {

    private val viewModel by viewModels<SavedWordsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        viewModel.savedWords.observe(viewLifecycleOwner) { words ->
            if (words.isNullOrEmpty()) {
                binding.tvNoWords.visibility = View.VISIBLE
                binding.recView.visibility = View.GONE
            } else {

                binding.tvNoWords.visibility = View.GONE
                binding.recView.visibility = View.VISIBLE
                val adapter =
                    SavedWordAdapter(requireContext(), TTS(requireContext()), words, false) {
                        viewModel.deleteWord(it)
                    }
                binding.recView.adapter = adapter

                if (words.size > 10) {
                    initGameButtons(words.shuffled().takeLast(10))
                } else {
                    initGameButtons(words.shuffled())
                }

            }

        }


    }

    private fun initGameButtons(words: List<SavedWord>) {

        binding.btnGameFleshCards.setOnClickListener {
            findNavController().navigate(
                R.id.gameFleshCardsFragment,
                bundleOf(
                    ARRAY_WORDS to words.map { it.value },
                    ARRAY_TRANSLATED_WORDS to words.map { it.translation },
                )
            )
        }
        binding.btnGameChoice.setOnClickListener {
            // if there are too few words
            if (words.size < 5) {
                Toast.makeText(
                    requireContext(),
                    getText(R.string.toastTooFewWordsForGame),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(
                    R.id.gameSelectWordFragment,
                    bundleOf(
                        ARRAY_WORDS to words.map { it.value },
                        ARRAY_TRANSLATED_WORDS to words.map { it.translation }
                    )
                )
            }
        }
        binding.btnGameWrite.setOnClickListener {
            findNavController().navigate(
                R.id.gameWriteWordFragment,
                bundleOf(
                    ARRAY_WORDS to words.map { it.value },
                    ARRAY_TRANSLATED_WORDS to words.map { it.translation },
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initWords()
        // Firebase Event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, SavedWordsFragment().javaClass.simpleName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, SavedWordsFragment().javaClass.simpleName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}