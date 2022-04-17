package com.palmdev.german_books.presentation.screens.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.BooksFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : Fragment(R.layout.books_fragment) {


    private val viewModel: BooksViewModel by viewModel()
    private lateinit var binding: BooksFragmentBinding
    private val mAdapter by lazy {  BooksAdapter(parentFragmentManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BooksFragmentBinding.bind(view)

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = mAdapter
        viewModel.initAllBooks()
        setBooksToAdapter()
        initBooksSorting()

    }

    private fun setBooksToAdapter() {
        viewModel.books.value?.let {
            mAdapter.setBooks(it)
        }
    }

    private fun initBooksSorting() {
        binding.btnAllBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked){
                viewModel.initAllBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            }
            else  it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnEasyBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked){
                viewModel.initEasyBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            }
            else  it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnMediumBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked){
                viewModel.initMediumBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            }
            else  it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnHardBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked){
                viewModel.initHardBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            }
            else  it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnLikedBooks.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                viewModel.initFavoriteBooks()
                setBooksToAdapter()
            }
        }
    }

    companion object {
        const val ARG_OPENED_BOOK = "ARG_OPENED_BOOK"
    }

}