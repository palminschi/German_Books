package com.palmdev.german_books.presentation.screens.videos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentVideosBinding
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toFormattedTimeString
import com.palmdev.german_books.extensions.toShortString
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.videos.video_player.VideoPlayerFragment
import com.palmdev.german_books.utils.LIMIT_VIDEOS_WATCH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideosFragment : BaseFragment<FragmentVideosBinding>(FragmentVideosBinding::inflate) {

    private val viewModel: VideosViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLargeVideos()
        initSecondRecyclerView()
        initLastWatchedVideo()
        initFirstRecyclerView()

        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.videoHistoryFragment)
        }
        binding.btnSavedVideos.setOnClickListener {
            findNavController().navigate(R.id.savedVideoFragment)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.updatePremiumStatus()
    }



    private fun initLargeVideos() {
        ViewCompat.setNestedScrollingEnabled(binding.videosListView, false)
        val adapter = LargeVideosAdapter(object : LargeVideosAdapter.Companion.OnItemClickListener {
            override fun onClick(item: VideoInfo) {
                viewModel.addNewWatchedVideo()
                if (
                    viewModel.isPremiumUser.value == false &&
                    viewModel.watchedVideosToday.value!! >= LIMIT_VIDEOS_WATCH
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
        binding.videosListView.adapter = adapter

        binding.videosLoadingProgress.visibility = View.VISIBLE
        binding.btnAllVideos.visibility = View.GONE

        binding.btnAllVideos.setOnClickListener {
            findNavController().navigate(R.id.allVideosFragment)
        }

        viewModel.recommendedVideos.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.videosLoadingProgress.visibility = View.GONE
                binding.btnAllVideos.visibility = View.VISIBLE
                adapter.addAll(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initLastWatchedVideo() {
        binding.cardContinueWatching.visibility = View.GONE
        binding.layoutContinueWatching.visibility = View.GONE
        viewModel.lastWatchedVideo.observe(viewLifecycleOwner) { video ->
            if (video == null) {
                binding.cardContinueWatching.visibility = View.GONE
                binding.layoutContinueWatching.visibility = View.GONE
            } else {
                binding.cardContinueWatching.visibility = View.VISIBLE
                binding.layoutContinueWatching.visibility = View.VISIBLE

                Glide.with(this)
                    .load(video.imageUrl)
                    .into(binding.imageContinueWatching)
                binding.tvTitleContinueWatching.text = video.title
                binding.tvViewsContinueWatching.text =
                    video.viewsCount.toShortString() + " " + getString(R.string.views)
                binding.tvLikesContinueWatching.text =
                    video.likesCount.toShortString() + " " + getString(R.string.likes)
                binding.tvDurationContinueWatching.text =
                    video.duration.toFormattedTimeString()

                val percentageWatched = video.secondsWatched / (video.duration / 1000) * 100
                binding.watchedProgress.progress = percentageWatched.toInt()

                binding.cardContinueWatching.setOnClickListener {
                    findNavController().navigate(
                        R.id.videoPlayerFragment,
                        bundleOf(VideoPlayerFragment.ARG_VIDEO_ID to video.videoId)
                    )
                }
            }
        }
    }

    private fun initFirstRecyclerView() {
        val adapter = SmallVideosAdapter(object : SmallVideosAdapter.Companion.OnItemClickListener {
            override fun onClick(item: VideoInfo) {
                viewModel.addNewWatchedVideo()
                if (
                    viewModel.isPremiumUser.value == false &&
                    viewModel.watchedVideosToday.value!! >= LIMIT_VIDEOS_WATCH
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
        binding.recViewFirst.adapter = adapter
        viewModel.popularVideos.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) adapter.addAll(it)
        }
    }

    private fun initSecondRecyclerView() {
        val adapter = SmallVideosAdapter(object : SmallVideosAdapter.Companion.OnItemClickListener {
            override fun onClick(item: VideoInfo) {
                viewModel.addNewWatchedVideo()
                if (
                    viewModel.isPremiumUser.value == false &&
                    viewModel.watchedVideosToday.value!! >= LIMIT_VIDEOS_WATCH
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
        binding.recViewSecond.adapter = adapter
        viewModel.topRatedVideos.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) adapter.addAll(it)
        }
    }

}