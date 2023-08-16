package com.palmdev.german_books.presentation.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.data.model.ToDoTask
import com.palmdev.german_books.data.storage.UserStorage
import com.palmdev.german_books.databinding.FragmentHomeBinding
import com.palmdev.german_books.domain.model.Book
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toAssetsUri
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.MainActivity
import com.palmdev.german_books.presentation.screens.reading.ReadBookFragment
import com.palmdev.german_books.presentation.screens.reading.books.BooksAdapter
import com.palmdev.german_books.presentation.screens.videos.SmallVideosAdapter
import com.palmdev.german_books.presentation.screens.videos.video_player.VideoPlayerFragment
import com.palmdev.german_books.utils.FACEBOOK_URL
import com.palmdev.german_books.utils.INSTAGRAM_URL
import com.palmdev.german_books.utils.LIMIT_VIDEOS_WATCH
import com.palmdev.german_books.utils.TELEGRAM_INVITE_URL
import com.palmdev.german_books.utils.TIKTOK_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var mBackPressedTime: Long = 0
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstOpen = UserStorage(requireContext()).isFirstOpen
        if (firstOpen) {
            if (!viewModel.hasPremium()) {
                findNavController().navigate(R.id.onBoardingStartFragment)
            }
        } else if (viewModel.isFirstSessionToday()) {
            if (!viewModel.userRatedApp) {
                findNavController().navigate(R.id.rateAppDialogFragment)
            } else if (!viewModel.hasPremium()) {
                findNavController().navigate(R.id.discountPurchaseFragment)
            }
        }

        if (viewModel.hasPremium()) {
            binding.btnGetPremium.visibility = View.GONE
        } else {
            binding.btnGetPremium.visibility = View.VISIBLE
            binding.btnGetPremium.setOnClickListener {
                findNavController().navigate(R.id.shopFragment)
            }
        }

        loadImages()
        initDailyTasks()
        initContinueReading()
        initNewBooks()
        initVideos()
        initSocials()
        binding.tvDailyStreakCount.text = viewModel.dailyStreak.toString()

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTasks()
        viewModel.updateLastReadBook()
        viewModel.checkSubscription()
    }

    override fun onBackPressed(): Boolean {
        if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
            activity?.moveTaskToBack(true)
            exitProcess(0)
        } else {
            Toast.makeText(
                requireContext(), getString(R.string.toastExitApp), Toast.LENGTH_SHORT
            ).show()
        }
        mBackPressedTime = System.currentTimeMillis()
        return true
    }

    private fun initSocials() {
        binding.btnSocialFB.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnSocialInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnSocialTikTok.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TIKTOK_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnSocialTelegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_INVITE_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun loadImages() {
        Glide.with(this)
            .load(R.drawable.avatar_m_b1)
            .into(binding.imgProfile)
        Glide.with(this)
            .load(R.drawable.img_people_community)
            .into(binding.imgCommunity)
        Glide.with(this)
            .load(R.drawable.ic_social_fb)
            .into(binding.iconFacebook)
        Glide.with(this)
            .load(R.drawable.ic_social_tiktok)
            .into(binding.iconTikTok)
        Glide.with(this)
            .load(R.drawable.ic_social_insta)
            .into(binding.iconInstagram)
        Glide.with(this)
            .load(R.drawable.ic_social_telegram)
            .into(binding.iconTelegram)
        Glide.with(this)
            .load(R.drawable.ic_premium)
            .into(binding.iconPremium)
        Glide.with(this)
            .load(R.drawable.settings)
            .into(binding.iconSettings)
        Glide.with(this)
            .load(R.drawable.ic_daily_streak)
            .into(binding.imgDailyStreak)
    }

    private fun initDailyTasks() {
        viewModel.toDoTasks.observe(viewLifecycleOwner) {
            it.forEach { task ->
                when (task.taskType) {
                    ToDoTask.TaskType.READ_BOOK -> {
                        binding.task1Progress.max = task.max
                        binding.task1Progress.progress = task.progress
                        if (task.isCompleted) {
                            binding.task1Checkbox.isChecked = true
                            binding.task1Checkbox.buttonTintList =
                                ColorStateList.valueOf(requireContext().getColor(R.color.blue_accent))
                            binding.task1Text.paintFlags =
                                binding.task1Text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        binding.task1.setOnClickListener {
                            (requireActivity() as MainActivity).navigateToNavBarDestination(R.id.booksFragment)
                        }
                    }
                    ToDoTask.TaskType.SAVE_WORDS -> {
                        binding.task2Progress.max = task.max
                        binding.task2Progress.progress = task.progress
                        if (task.isCompleted) {
                            binding.task2Checkbox.isChecked = true
                            binding.task2Checkbox.buttonTintList =
                                ColorStateList.valueOf(requireContext().getColor(R.color.blue_accent))
                            binding.task2Text.paintFlags =
                                binding.task2Text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        binding.task2.setOnClickListener {
                            (requireActivity() as MainActivity).navigateToNavBarDestination(R.id.booksFragment)
                        }
                    }
                    ToDoTask.TaskType.WATCH_VIDEO -> {
                        binding.task3Progress.max = task.max
                        binding.task3Progress.progress = task.progress
                        if (task.isCompleted) {
                            binding.task3Checkbox.isChecked = true
                            binding.task3Checkbox.buttonTintList =
                                ColorStateList.valueOf(requireContext().getColor(R.color.blue_accent))
                            binding.task3Text.paintFlags =
                                binding.task3Text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        binding.task3.setOnClickListener {
                            (requireActivity() as MainActivity).navigateToNavBarDestination(R.id.videosFragment)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initContinueReading() {
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
    }

    private fun initNewBooks() {
        val onClickBookListener = object : BooksAdapter.Companion.OnClickBookListener {
            override fun onClick(book: Book) {
                if (book.isPremium) {
                    if (!viewModel.hasPremium()) {
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
        val adapter = BooksAdapter(onClickBookListener)
        binding.booksRecView.adapter = adapter

        binding.btnToBooks.setOnClickListener {
            (requireActivity() as MainActivity).navigateToNavBarDestination(R.id.booksFragment)
        }
        viewModel.newBooks.observe(viewLifecycleOwner) {
            adapter.addBooks(it)
        }
    }

    private fun initVideos() {
        binding.btnToVideos.setOnClickListener {
            (requireActivity() as MainActivity).navigateToNavBarDestination(R.id.videosFragment)
        }
        val adapter = SmallVideosAdapter(object : SmallVideosAdapter.Companion.OnItemClickListener {
            override fun onClick(item: VideoInfo) {
                viewModel.addNewWatchedVideo()
                if (
                    !viewModel.hasPremium() && viewModel.watchedVideosToday.value!! >= LIMIT_VIDEOS_WATCH
                ) {
                    findNavController().navigate(R.id.videosLimitDialogFragment)
                } else {
                    findNavController().navigate(
                        R.id.videoPlayerFragment, bundleOf(
                            VideoPlayerFragment.ARG_VIDEO_ID to item.videoId
                        )
                    )
                }
            }
        })
        binding.videosRecView.adapter = adapter
        viewModel.videos.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) adapter.addAll(it)
        }
    }

}