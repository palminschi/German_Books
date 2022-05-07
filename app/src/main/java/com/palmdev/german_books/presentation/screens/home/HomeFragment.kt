package com.palmdev.german_books.presentation.screens.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.palmdev.data.util.Base64Coder
import com.palmdev.data.util.Constants
import com.palmdev.german_books.BuildConfig
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.HomeFragmentBinding
import com.palmdev.german_books.presentation.screens.books.BooksFragment
import com.palmdev.german_books.presentation.screens.dialog_restricted_content.RestrictedContentDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.home_fragment) {


    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        initNavButtons()
        initRatingButtons()
        setLastBookRead()
        setLastSavedWord()
        initNewBooks(id1 = 8, id2 = 0, id3 = 2)

    }

    private fun initNewBooks(id1: Int, id2: Int, id3: Int) {

        binding.newBook1.visibility = View.GONE
        binding.newBook2.visibility = View.GONE
        binding.newBook3.visibility = View.GONE

        viewModel.initNewBooks(id1, id2, id3)

        viewModel.newBooks.observe(viewLifecycleOwner) { books ->
            books[0]?.let { book ->
                binding.newBook1.visibility = View.VISIBLE
                binding.bookTitle1.text = book.title
                binding.bookAuthor1.text = book.author
                binding.imgBook1.setImageBitmap(Base64Coder.decodeImageToByte(book.encodedImage))
                binding.newBook1.setOnClickListener {
                    if (book.isPremium && viewModel.userPremiumStatus.value == false) {
                        val dialog = RestrictedContentDialogFragment(
                            withAdsOption = true,
                            onUserEarnedRewardListener = {
                                findNavController().navigate(
                                    R.id.bookReadingFragment,
                                    bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                                )
                            }
                        )
                        dialog.show(parentFragmentManager, "TAG")
                    } else {
                        findNavController().navigate(
                            R.id.bookReadingFragment,
                            bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                        )
                    }
                }
            }
            books[1]?.let { book ->
                binding.newBook2.visibility = View.VISIBLE
                binding.bookTitle2.text = book.title
                binding.bookAuthor2.text = book.author
                binding.imgBook2.setImageBitmap(Base64Coder.decodeImageToByte(book.encodedImage))
                binding.newBook2.setOnClickListener {
                    if (book.isPremium) {
                        val dialog = RestrictedContentDialogFragment(
                            withAdsOption = true,
                            onUserEarnedRewardListener = {
                                findNavController().navigate(
                                    R.id.bookReadingFragment,
                                    bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                                )
                            }
                        )
                        dialog.show(parentFragmentManager, "TAG")
                    } else {
                        findNavController().navigate(
                            R.id.bookReadingFragment,
                            bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                        )
                    }
                }
            }
            books[2]?.let { book ->
                binding.newBook3.visibility = View.VISIBLE
                binding.bookTitle3.text = book.title
                binding.bookAuthor3.text = book.author
                binding.imgBook3.setImageBitmap(Base64Coder.decodeImageToByte(book.encodedImage))
                binding.newBook3.setOnClickListener {
                    if (book.isPremium) {
                        val dialog = RestrictedContentDialogFragment(
                            withAdsOption = true,
                            onUserEarnedRewardListener = {
                                findNavController().navigate(
                                    R.id.bookReadingFragment,
                                    bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                                )
                            }
                        )
                        dialog.show(parentFragmentManager, "TAG")
                    } else {
                        findNavController().navigate(
                            R.id.bookReadingFragment,
                            bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                        )
                    }
                }
            }

        }
    }

    private fun setLastSavedWord() {
        viewModel.setLastSavedWord()
        viewModel.lastWord.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.lastWordContainer.visibility = View.GONE
            } else {
                binding.lastWordContainer.visibility = View.VISIBLE
                binding.lastWord.text = it.word
                binding.lastWordTranslation.text = it.translation
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLastBookRead() {
        viewModel.setLastBook()
        if (viewModel.lastBook.value == null) {
            binding.lastBookContainer.visibility = View.GONE
        }
        viewModel.lastBook.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                binding.lastBookContainer.visibility = View.VISIBLE
                binding.lastBookTitle.text = book.title
                binding.lastBookAuthor.text = book.author
                val decodedImageBitmap = Base64Coder.decodeImageToByte(book.encodedImage)
                binding.imgLastBook.setImageBitmap(decodedImageBitmap)
                binding.lastBookContainer.setOnClickListener {
                    findNavController().navigate(
                        R.id.bookReadingFragment,
                        bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                    )
                }

                viewModel.setReadingProgress(book.id)
                viewModel.lastBookReadingProgress.observe(viewLifecycleOwner) {
                    val currentPage = (it.currentPage + 1).toFloat()
                    val totalPages = (it.totalPages + 1).toFloat()
                    val progress = (currentPage / totalPages * 100).toInt()

                    binding.readingProgressBar.progress = progress
                    binding.tvReadingProgress.text = "$progress%"
                }
            }
        }

    }

    private fun initNavButtons() {
        binding.btnTranslator.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_translatorFragment)
        }
        binding.btnViewAllBooks.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectBookFragment)
        }
        binding.btnAllWords.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wordsFragment)
        }
        binding.btnGetPremium.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_shopFragment)
        }
        binding.btnOurApps.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://developer?id=DevPalm")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=DevPalm")
                    )
                )
            }
        }
    }

    private fun initRatingButtons() {
        viewModel.userRatedApp.observe(viewLifecycleOwner) { isRated ->
            if (isRated) binding.ratingContainer.visibility = View.GONE
            else binding.ratingContainer.visibility = View.VISIBLE
        }

        fun goToPlayMarket() {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                )
            )
        }

        fun hideRatingView() {
            binding.ratingContainer.animate().alpha(0f).setDuration(500).withEndAction {
                binding.ratingContainer.visibility = View.GONE
            }
            viewModel.setAppIsRated(true)

            val snackBar = Snackbar.make(
                binding.root,
                getString(R.string.thxForFeedback),
                Snackbar.LENGTH_LONG
            )
            snackBar.setActionTextColor(resources.getColor(R.color.main_orange))
            snackBar.setAction(getString(R.string.close)) {
                snackBar.dismiss()
            }
            snackBar.show()
        }

        binding.apply {
            star2.setOnClickListener {
                star1.isChecked = true
                hideRatingView()
            }
            star3.setOnClickListener {
                star1.isChecked = true
                star2.isChecked = true
                hideRatingView()
            }
            star4.setOnClickListener {
                goToPlayMarket()
                star1.isChecked = true
                star2.isChecked = true
                star3.isChecked = true
                hideRatingView()
            }
            star5.setOnClickListener {
                goToPlayMarket()
                star1.isChecked = true
                star2.isChecked = true
                star3.isChecked = true
                star4.isChecked = true
                hideRatingView()
            }
        }

    }

}