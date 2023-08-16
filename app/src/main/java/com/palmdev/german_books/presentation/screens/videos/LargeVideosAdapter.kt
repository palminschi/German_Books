package com.palmdev.german_books.presentation.screens.videos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemVideoLargeBinding
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toFormattedTimeString
import com.palmdev.german_books.extensions.toShortString

class LargeVideosAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<LargeVideosAdapter.LargeVideosViewHolder>() {

    private val videos = ArrayList<VideoInfo>()

    inner class LargeVideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemVideoLargeBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(video: VideoInfo) {
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
                listener.onClick(video)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LargeVideosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_large, parent, false)
        return LargeVideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: LargeVideosViewHolder, position: Int) {
        holder.bind(video = videos[position])
    }

    fun addAll(data: List<VideoInfo>) {
        val oldSize = videos.size
        videos.addAll(data)
        notifyItemRangeChanged(oldSize, videos.size)
    }

    companion object {
        interface OnItemClickListener {
            fun onClick(item: VideoInfo)
        }
    }
}