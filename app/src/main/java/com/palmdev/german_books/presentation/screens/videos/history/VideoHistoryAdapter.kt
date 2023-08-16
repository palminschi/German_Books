package com.palmdev.german_books.presentation.screens.videos.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemSavedVideoBinding
import com.palmdev.german_books.domain.model.SavedVideo
import com.palmdev.german_books.extensions.toFormattedTimeString
import com.palmdev.german_books.extensions.toShortString
import com.palmdev.german_books.presentation.screens.videos.video_player.VideoPlayerFragment

class VideoHistoryAdapter : RecyclerView.Adapter<VideoHistoryAdapter.VideoHistoryViewHolder>() {

    private val videos = ArrayList<SavedVideo>()

    class VideoHistoryViewHolder(itemView: View) : ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(video: SavedVideo) {
            val binding = ItemSavedVideoBinding.bind(itemView)
            val context: Context = itemView.context

            Glide.with(itemView.context)
                .load(video.imageUrl)
                .into(binding.videoImage)
            binding.tvTitle.text = video.title
            binding.tvViews.text =
                video.viewsCount.toShortString() + " " + context.getString(R.string.views)
            binding.tvLikes.text =
                video.likesCount.toShortString() + " " + context.getString(R.string.likes)
            binding.tvDuration.text =
                video.duration.toFormattedTimeString()
            binding.root.setOnClickListener {
                it.findNavController().navigate(
                    R.id.videoPlayerFragment, bundleOf(
                        VideoPlayerFragment.ARG_VIDEO_ID to video.videoId
                    )
                )
            }

            val percentageWatched = video.secondsWatched / (video.duration / 1000) * 100
            binding.watchedProgress.progress = percentageWatched.toInt()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_video, null)
        return VideoHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoHistoryViewHolder, position: Int) {
        holder.bind(video = videos[position])
    }

    fun addAll(data: List<SavedVideo>) {
        videos.clear()
        videos.addAll(data)
        notifyItemRangeChanged(0, videos.size)
    }

    fun addVideo(video: SavedVideo) {
        videos.add(video)
        notifyItemInserted(videos.size)
    }

}