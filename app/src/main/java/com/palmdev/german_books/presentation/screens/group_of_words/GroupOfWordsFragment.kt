package com.palmdev.german_books.presentation.screens.group_of_words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.domain.model.Word
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.GroupOfWordsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupOfWordsFragment : Fragment() {

    private val viewModel: GroupOfWordsViewModel by viewModel()
    private lateinit var binding: GroupOfWordsFragmentBinding
    private val mWordsViews = ArrayList<TextView>()
    private val mTranslationViews = ArrayList<TextView>()
    private val mLinesViews = ArrayList<LinearLayout>()
    private val mWordsArray = ArrayList<Word>()
    private var mGroupId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.group_of_words_fragment, container, false)
        binding = GroupOfWordsFragmentBinding.bind(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWordsArray.clear()
        // Get id from args
        mGroupId = requireArguments().getInt(ARG_GROUP_ID)

        // Set title
        val title = getString(R.string.groupOfWords) + " " + (mGroupId + 1)
        binding.groupOfWordsTitle.text = title

        // Set words
        viewModel.getWords(mGroupId)
        viewModel.words.observe(viewLifecycleOwner) {
            mWordsArray.addAll(it)
            setContent()
        }

        // Games
        binding.btnGameFleshCards.setOnClickListener {
            goToGame(R.id.action_groupOfWordsFragment_to_gameFleshCardsFragment)
        }

        binding.btnGameWrite.setOnClickListener {
            // TODO: If is premium user
            goToGame(R.id.action_groupOfWordsFragment_to_gameWriteWordFragment)
        }

        binding.btnGameChoice.setOnClickListener {
            if (mWordsArray.size < 4) {
                Toast.makeText(
                    requireContext(),
                    getText(R.string.toastMin4Words),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                goToGame(R.id.action_groupOfWordsFragment_to_gameSelectWordFragment)
            }
        }
    }

    private fun goToGame(action: Int) {
        findNavController().navigate(
            action,
            bundleOf(
                ARG_WORDS_ARRAY to mWordsArray.map { it.word },
                ARG_TRANSLATIONS_ARRAY to mWordsArray.map { it.translation }
            )
        )
    }

    private fun setContent() {
        binding.apply {
            mLinesViews.addAll(
                listOf(
                    line01, line02, line03, line04, line05, line06, line07, line08, line09, line10
                )
            )
            mTranslationViews.addAll(
                listOf(
                    translation01, translation02, translation03, translation04, translation05,
                    translation06, translation07, translation08, translation09, translation10
                )
            )
            mWordsViews.addAll(
                listOf(
                    word01, word02, word03, word04, word05, word06, word07, word08, word09, word10
                )
            )
        }

        mLinesViews.forEach { it.visibility = View.INVISIBLE }
        for (i in 0 until mWordsArray.size) {
            mLinesViews[i].visibility = View.VISIBLE
            mWordsViews[i].text = mWordsArray[i].word
            mTranslationViews[i].text = mWordsArray[i].translation
        }
    }

    companion object {
        const val ARG_GROUP_ID = "ARG_GROUP_ID"
        const val ARG_WORDS_ARRAY = "ARG_WORDS_ARRAY"
        const val ARG_TRANSLATIONS_ARRAY = "ARG_TRANSLATIONS_ARRAY"
    }
}