package com.palmdev.german_books.presentation.screens.books

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.palmdev.data.util.Base64Coder
import com.palmdev.data.util.Constants
import com.palmdev.domain.model.Book
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemBookBinding

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.BooksHolder>() {

    private val listOfBooks = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BooksHolder(item = item)
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        holder.bind(listOfBooks[position])
    }

    override fun getItemCount(): Int {
        return listOfBooks.size
    }

    fun setBooks(books: List<Book>){
        listOfBooks.clear()
        listOfBooks.addAll(books)
        notifyDataSetChanged()
    }

    class BooksHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val context = item.context
        private val binding = ItemBookBinding.bind(item)
        private val mSharedPrefs =
            context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        fun bind(book: Book) {
            // Title and Author
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            // Decode Book Image
            val decodedBitmap = Base64Coder.decodeImageToByte(book.encodedImage)
            binding.bookImg.setImageBitmap(decodedBitmap)
            // Button Add Book To Favorite
            binding.toggleLike.setOnCheckedChangeListener { _, isChecked ->
                mSharedPrefs.edit().putBoolean(Constants.FAVORITE_BOOK + book.id, isChecked).apply()
            }
            binding.toggleLike.isChecked = book.favorite
            // Open Book
            // TODO: if is premium
            binding.root.setOnClickListener {
                itemView.findNavController().navigate(
                    R.id.action_selectBookFragment_to_bookReadingFragment,
                    bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                )
            }
            // Premium or not
            if (book.premium) {
                binding.cardFree.visibility = View.GONE
                binding.imgPremium.visibility = View.VISIBLE
            }else {
                binding.cardFree.visibility = View.VISIBLE
                binding.imgPremium.visibility = View.GONE
            }


            // Book Difficulty
            val levelLines = listOf(
                binding.levelLine1, binding.levelLine2, binding.levelLine3, binding.levelLine4,
                binding.levelLine5, binding.levelLine6
            )
            levelLines.forEach {
                it.setCardBackgroundColor(context.getColor(R.color.gray_04))
            }
            when (book.difficulty) {
                "EASY" -> {
                    for (i in 0 until 2) {
                        levelLines[i].setCardBackgroundColor(context.getColor(R.color.green_2))
                    }
                }
                "MEDIUM" -> {
                    for (i in 0 until 4) {
                        levelLines[i].setCardBackgroundColor(context.getColor(R.color.yellow))
                    }
                }
                "HARD" -> {
                    levelLines.forEach {
                        it.setCardBackgroundColor(context.getColor(R.color.red))
                    }
                }
            }

        }
    }
}