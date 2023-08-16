package com.palmdev.german_books.presentation.screens.videos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemVideoSmallBinding
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toFormattedTimeString
import com.palmdev.german_books.extensions.toShortString

class SmallVideosAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<SmallVideosAdapter.SmallVideosViewHolder>() {

    private val videos = ArrayList<VideoInfo>()

    inner class SmallVideosViewHolder(itemView: View) : ViewHolder(itemView) {

        private val binding = ItemVideoSmallBinding.bind(itemView)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallVideosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_small, null)
        return SmallVideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: SmallVideosViewHolder, position: Int) {
        holder.bind(video = videos[position])
    }

    fun addAll(data: List<VideoInfo>) {
        val oldSize = videos.size
        videos.addAll(data)
        notifyItemRangeChanged(oldSize, videos.size)
    }

    fun addVideo(video: VideoInfo) {
        videos.add(video)
        notifyItemInserted(videos.size)
    }

    companion object {
        interface OnItemClickListener {
            fun onClick(item: VideoInfo)
        }
    }
}