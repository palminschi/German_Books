package com.palmdev.german_books.presentation.screens.settings.our_apps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemAppBinding
import com.palmdev.german_books.utils.CAUGHT_ERROR

class AppsAdapter(
    private val context: Context,
    private val apps: List<AppInfo>
) : BaseAdapter() {


    override fun getCount(): Int {
        return apps.size
    }

    override fun getItem(position: Int): AppInfo {
        return apps[position]
    }

    override fun getItemId(position: Int): Long {
        return apps.indexOf(apps[position]).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false)
        }

        val binding = ItemAppBinding.bind(view!!)

        Glide.with(context)
            .load(apps[position].imageUrl)
            .into(binding.appIcon)

        binding.tvName.text = apps[position].name

        binding.btnInstall.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${apps[position].packageName}"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.message?.let { Log.e(CAUGHT_ERROR, it) }
            }
        }

        return view
    }
}