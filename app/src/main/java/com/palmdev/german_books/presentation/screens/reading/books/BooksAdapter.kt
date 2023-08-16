package com.palmdev.german_books.presentation.screens.reading.books

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemBookLargeBinding
import com.palmdev.german_books.domain.model.Book

class BooksAdapter(
    private val onClickBookListener: OnClickBookListener
) : RecyclerView.Adapter<BooksAdapter.BookHolder>() {

    private val mBooks = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_book_large, parent, false)

        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(mBooks[position])
    }

    override fun getItemCount(): Int {
        return mBooks.size
    }

    fun addBooks(data: List<Book>) {
        mBooks.clear()
        mBooks.addAll(data)
        notifyDataSetChanged()
    }

    inner class BookHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = ItemBookLargeBinding.bind(item)

        fun bind(book: Book) {
            binding.root.setOnClickListener {
                onClickBookListener.onClick(book)
            }

            Glide.with(itemView)
                .asBitmap()
                .load(Uri.parse("file:///android_asset/books/images/${book.imagePath}.jpg"))
                .into(binding.imgBookCover)

            binding.tvTitle.text = book.title
            binding.tvAuthor.text = book.author
            binding.tvLevel.text = book.level
            if (book.isPremium) {
                binding.free.visibility = View.GONE
            } else {
                binding.free.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        interface OnClickBookListener {
            fun onClick(book: Book)
        }
    }
}