package com.palmdev.german_books.presentation.screens.reading.more

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentBooksMoreBinding
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.reading.ReadBookFragment
import com.palmdev.german_books.presentation.screens.reading.books.BooksViewModel

class MoreBooksFragment :
    BaseFragment<FragmentBooksMoreBinding>(FragmentBooksMoreBinding::inflate) {

    private val viewModel by activityViewModels<BooksViewModel>()
    private var mSelectedBooksType = SelectedBooksType.A1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = requireArguments().getString(ARG_SELECTED_BOOKS)
        if (arg != null) {
            mSelectedBooksType = SelectedBooksType.valueOf(arg)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = GridBooksAdapter(object : GridBooksAdapter.Companion.OnClickBookListener {
            override fun onClick(book: Book) {
                if (book.isPremium) {
                    if (!viewModel.isPremiumUser) {
                        findNavController().navigate(R.id.restrictedContentDialogFragment)
                        return
                    }
                }
                findNavController().navigate(
                    R.id.readBookFragment, bundleOf(
                        ReadBookFragment.ARG_SELECTED_BOOK_ID to book.id
                    )
                )
            }
        })

        binding.recyclerView.adapter = adapter

        when (mSelectedBooksType) {
            SelectedBooksType.A1 -> {
                binding.title.text = getString(R.string.a1Title)
                viewModel.a1Books.observe(viewLifecycleOwner) { adapter.addBooks(it) }
            }

            SelectedBooksType.A2 -> {
                binding.title.text = getString(R.string.a2Title)
                viewModel.a2Books.observe(viewLifecycleOwner) { adapter.addBooks(it) }
            }

            SelectedBooksType.B1 -> {
                binding.title.text = getString(R.string.b1Title)
                viewModel.b1Books.observe(viewLifecycleOwner) { adapter.addBooks(it) }
            }

            SelectedBooksType.B2 -> {
                binding.title.text = getString(R.string.b2Title)
                viewModel.b2Books.observe(viewLifecycleOwner) { adapter.addBooks(it) }
            }

            SelectedBooksType.Favorites -> {
                binding.title.text = getString(R.string.favoriteBooks)
                viewModel.favorites.observe(viewLifecycleOwner) { adapter.addBooks(it) }
                viewModel.initFavoriteBooks()
            }
        }

    }

    companion object {
        const val ARG_SELECTED_BOOKS = "ARG_SELECTED_BOOKS"

        enum class SelectedBooksType { A1, A2, B1, B2, Favorites }
    }
}