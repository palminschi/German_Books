package com.palmdev.german_books.presentation.screens.group_of_words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.domain.model.Word
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.GroupOfWordsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupOfWordsFragment : Fragment() {

    private val viewModel: GroupOfWordsViewModel by viewModel()
    private lateinit var binding: GroupOfWordsFragmentBinding
    private val mWordsViews by lazy {
        listOf(
            binding.word01, binding.word02, binding.word03, binding.word04, binding.word05,
            binding.word06, binding.word07, binding.word08, binding.word09, binding.word10
        )
    }
    private val mTranslationViews by lazy {
        listOf(
            binding.translation01, binding.translation02, binding.translation03,
            binding.translation04, binding.translation05, binding.translation06,
            binding.translation07, binding.translation08, binding.translation09,
            binding.translation10
        )
    }
    private val mLinesViews by lazy {
        listOf(
            binding.line01, binding.line02, binding.line03, binding.line04, binding.line05,
            binding.line06, binding.line07, binding.line08, binding.line09, binding.line10
        )
    }
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

        mGroupId = requireArguments().getInt(ARG_GROUP_ID)

        viewModel.getWords(mGroupId)
        viewModel.words.observe(viewLifecycleOwner) {
            mWordsArray.addAll(it)
            setContent()
        }


    }

    private fun setContent() {
        mLinesViews.forEach { it.visibility = View.INVISIBLE }
        for (i in 0 until mWordsArray.size) {
            mLinesViews[i].visibility = View.VISIBLE
            mWordsViews[i].text = mWordsArray[i].word
            mTranslationViews[i].text = mWordsArray[i].translation
        }
    }

    companion object {
        const val ARG_GROUP_ID = "ARG_GROUP_ID"
    }
}