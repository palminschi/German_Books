package com.palmdev.german_books.presentation.screens.videos

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentAllVideosBinding
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.videos.video_player.VideoPlayerFragment
import com.palmdev.german_books.utils.LIMIT_VIDEOS_WATCH

class AllVideosFragment : BaseFragment<FragmentAllVideosBinding>(FragmentAllVideosBinding::inflate) {

    private val viewModel: VideosViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.getAllVideos()

        viewModel.videos.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.addAll(it)
            }
        }
    }
}