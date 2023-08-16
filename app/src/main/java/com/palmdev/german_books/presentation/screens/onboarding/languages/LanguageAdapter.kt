package com.palmdev.german_books.presentation.screens.onboarding.languages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemLanguageBinding
import com.palmdev.german_books.legacy.models.TranslatorLanguage

class LanguageAdapter(private val context: Context, private val translatorLanguages: List<TranslatorLanguage>) : BaseAdapter() {
    override fun getCount(): Int {
        return translatorLanguages.size
    }

    override fun getItem(position: Int): TranslatorLanguage {
        return translatorLanguages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false)
        }
        val binding = ItemLanguageBinding.bind(view!!)

        binding.languageName.text = translatorLanguages[position].name

        return view
    }

}