package com.palmdev.german_books.presentation.screens.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.BooksFragmentBinding
import com.palmdev.german_books.utils.AdMob
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksFragment : Fragment(R.layout.books_fragment) {


    private val viewModel: BooksViewModel by viewModel()
    private lateinit var binding: BooksFragmentBinding
    private lateinit var mAdapter: BooksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BooksFragmentBinding.bind(view)



        viewModel.premiumStatus.observe(viewLifecycleOwner) {
            mAdapter = BooksAdapter(
                fragmentManager = parentFragmentManager,
                isPremiumUser = it,
                navController = findNavController()
            )

            initRecyclerView()
            viewModel.initAllBooks()
            setBooksToAdapter()
            initBooksSorting()
        }
    }

    private fun initRecyclerView() {
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = mAdapter
    }

    private fun setBooksToAdapter() {
        viewModel.books.value?.let {
            mAdapter.setBooks(it)
        }
    }

    private fun initBooksSorting() {
        binding.btnAllBooks.isChecked = true
        binding.btnEasyBooks.isChecked = false
        binding.btnMediumBooks.isChecked = false
        binding.btnHardBooks.isChecked = false
        binding.btnLikedBooks.isChecked = false

        binding.btnAllBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                viewModel.initAllBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            } else it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnEasyBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                viewModel.initEasyBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            } else it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnMediumBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                viewModel.initMediumBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            } else it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnHardBooks.setOnCheckedChangeListener { it, isChecked ->
            if (isChecked) {
                viewModel.initHardBooks()
                setBooksToAdapter()
                it.setTextColor(requireContext().getColor(R.color.main_orange))
            } else it.setTextColor(requireContext().getColor(R.color.gray_03))
        }
        binding.btnLikedBooks.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.initFavoriteBooks()
                setBooksToAdapter()
            }
        }
    }

    companion object {
        const val ARG_OPENED_BOOK = "ARG_OPENED_BOOK"
    }

}