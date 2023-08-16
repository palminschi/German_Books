package com.palmdev.german_books.presentation.screens.videos.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.palmdev.german_books.databinding.FragmentVideoHistoryBinding
import com.palmdev.german_books.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoHistoryFragment : BaseFragment<FragmentVideoHistoryBinding>(
    FragmentVideoHistoryBinding::inflate
) {

    private val viewModel: VideoHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VideoHistoryAdapter()
        binding.recView.adapter = adapter
        viewModel.watchedVideos.observe(viewLifecycleOwner) {
            adapter.addAll(it)
        }
    }


}