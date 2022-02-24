package com.palmdev.german_books.presentation.screens.words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.palmdev.domain.model.GroupOfWords
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemGroupOfWordsBinding
import com.palmdev.german_books.presentation.screens.group_of_words.GroupOfWordsFragment

class WordsAdapter : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {

    private val groupsOfWords = ArrayList<GroupOfWords>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.item_group_of_words, parent, false)
        return WordsViewHolder(item = item)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(groupsOfWords[position])
    }

    override fun getItemCount(): Int {
        return groupsOfWords.size
    }

    fun addGroups(list: List<GroupOfWords>) {
        groupsOfWords.clear()
        groupsOfWords.addAll(list)
        notifyDataSetChanged()
    }

    class WordsViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ItemGroupOfWordsBinding.bind(item)

        fun bind(groupOfWords: GroupOfWords) {
            val title =
                itemView.context.getString(R.string.groupOfWords) + " " + (groupOfWords.groupId + 1)
            binding.groupTitle.text = title
            binding.numberOfWords.text = groupOfWords.numberOfWords.toString()
            binding.root.setOnClickListener {
                itemView.findNavController().navigate(
                    R.id.groupOfWordsFragment,
                    bundleOf(
                        GroupOfWordsFragment.ARG_GROUP_ID to groupOfWords.groupId
                    )
                )
            }
        }
    }
}