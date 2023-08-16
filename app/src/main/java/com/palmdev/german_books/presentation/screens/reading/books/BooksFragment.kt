package com.palmdev.german_books.presentation.screens.reading.books

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentBooksBinding
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.extensions.toAssetsUri
import com.palmdev.german_books.legacy.Ads
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.reading.ReadBookFragment
import com.palmdev.german_books.presentation.screens.reading.more.MoreBooksFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksFragment : BaseFragment<FragmentBooksBinding>(FragmentBooksBinding::inflate) {

    private val viewModel by activityViewModels<BooksViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Ads.loadInterstitialAd(context = requireContext())

        initBooks()
    }

    private fun initBooks() {
        val onClickBookListener = object : BooksAdapter.Companion.OnClickBookListener {
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
        }
        viewModel.lastReadBook.observe(viewLifecycleOwner) { book ->
            if (book == null) {
                binding.continueContainer.visibility = View.GONE
            } else {
                Glide.with(this)
                    .load("books/images/${book.imagePath}.jpg".toAssetsUri())
                    .into(binding.imgBookContinueReading)

                binding.continueContainer.visibility = View.VISIBLE
                binding.continueBookTitle.text = book.title
                binding.continueBookAuthor.text = book.author

                val readPercent = (book.currentPage * 100) / book.totalPages
                binding.continueProgress.progress = readPercent
                binding.continueProgress.max = 100
                binding.continueReadPercent.text = "$readPercent%"

                binding.continueContainer.setOnClickListener {
                    findNavController().navigate(
                        R.id.readBookFragment,
                        bundleOf(ReadBookFragment.ARG_SELECTED_BOOK_ID to book.id)
                    )
                }
            }
        }
        viewModel.a1Books.observe(viewLifecycleOwner) { books ->
            val adapter = BooksAdapter(onClickBookListener)
            binding.recViewA1.adapter = adapter
            adapter.addBooks(books)

            binding.btnToA1.setOnClickListener {
                findNavController().navigate(
                    R.id.moreBooksFragment, bundleOf(
                        MoreBooksFragment.ARG_SELECTED_BOOKS to MoreBooksFragment.Companion.SelectedBooksType.A1.name
                    )
                )
            }
        }
        viewModel.a2Books.observe(viewLifecycleOwner) { books ->
            val adapter = BooksAdapter(onClickBookListener)
            binding.recViewA2.adapter = adapter
            adapter.addBooks(books)

            binding.btnToA2.setOnClickListener {
                findNavController().navigate(
                    R.id.moreBooksFragment, bundleOf(
                        MoreBooksFragment.ARG_SELECTED_BOOKS to MoreBooksFragment.Companion.SelectedBooksType.A2.name
                    )
                )
            }
        }
        viewModel.b1Books.observe(viewLifecycleOwner) { books ->
            val adapter = BooksAdapter(onClickBookListener)
            binding.recViewB1.adapter = adapter
            adapter.addBooks(books)

            binding.btnToB1.setOnClickListener {
                findNavController().navigate(
                    R.id.moreBooksFragment, bundleOf(
                        MoreBooksFragment.ARG_SELECTED_BOOKS to MoreBooksFragment.Companion.SelectedBooksType.B1.name
                    )
                )
            }
        }
        viewModel.b2Books.observe(viewLifecycleOwner) { books ->
            val adapter = BooksAdapter(onClickBookListener)
            binding.recViewB2.adapter = adapter
            adapter.addBooks(books)

            binding.btnToB2.setOnClickListener {
                findNavController().navigate(
                    R.id.moreBooksFragment, bundleOf(
                        MoreBooksFragment.ARG_SELECTED_BOOKS to MoreBooksFragment.Companion.SelectedBooksType.B2.name
                    )
                )
            }
        }

        binding.btnFavoriteBooks.setOnClickListener {
            findNavController().navigate(
                R.id.moreBooksFragment, bundleOf(
                    MoreBooksFragment.ARG_SELECTED_BOOKS to MoreBooksFragment.Companion.SelectedBooksType.Favorites.name
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateLastBookRead()

        // Firebase Event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, BooksFragment().javaClass.simpleName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, BooksFragment().javaClass.simpleName)
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


}