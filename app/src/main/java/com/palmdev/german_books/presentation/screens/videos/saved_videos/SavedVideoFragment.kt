package com.palmdev.german_books.presentation.screens.videos.saved_videos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentVideoHistoryBinding
import com.palmdev.german_books.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedVideoFragment : BaseFragment<FragmentVideoHistoryBinding>(
    FragmentVideoHistoryBinding::inflate
) {

    private val viewModel: SavedVideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = getString(R.string.savedVideos)

        val adapter = SavedVideoAdapter()
        binding.recView.adapter = adapter
        viewModel.savedVideos.observe(viewLifecycleOwner) {
            adapter.addAll(it)
        }
    }


}