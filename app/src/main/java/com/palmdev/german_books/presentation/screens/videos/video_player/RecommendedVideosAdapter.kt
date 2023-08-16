package com.palmdev.german_books.presentation.screens.videos.video_player

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemVideoDarkBinding
import com.palmdev.german_books.domain.model.VideoInfo
import com.palmdev.german_books.extensions.toFormattedTimeString
import com.palmdev.german_books.extensions.toShortString

class RecommendedVideosAdapter(
    private val context: Context
) : BaseAdapter() {

    private val videos = ArrayList<VideoInfo>()

    override fun getCount(): Int {
        return videos.size
    }

    override fun getItem(position: Int): Any {
        return videos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_video_dark, parent, false)
        }
        val binding = ItemVideoDarkBinding.bind(view!!)

        val video = videos[position]

        Glide.with(context)
            .load(video.imageUrl)
            .into(binding.videoImage)
        binding.tvTitle.text = video.title
        binding.tvViews.text =
            video.viewsCount.toShortString() + " " + context.getString(R.string.views)
        binding.tvLikes.text =
            video.likesCount.toShortString() + " " + context.getString(R.string.likes)
        binding.tvDuration.text =
            video.duration.toFormattedTimeString()

        return view
    }


    fun addVideos(data: List<VideoInfo>) {
        videos.addAll(data)
        notifyDataSetChanged()
    }
}