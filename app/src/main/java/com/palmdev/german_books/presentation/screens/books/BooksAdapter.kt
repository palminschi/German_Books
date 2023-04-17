package com.palmdev.german_books.presentation.screens.books

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.palmdev.data.util.Base64Coder
import com.palmdev.data.util.Constants
import com.palmdev.domain.model.Book
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemBookBinding
import com.palmdev.german_books.presentation.screens.dialog_restricted_content.RestrictedContentDialogFragment
import com.palmdev.german_books.utils.AdMob
import com.palmdev.german_books.utils.Network

private const val AD_TYPE = 0
private const val CONTENT_TYPE = 1
private const val AD_CONDITION = 4

class BooksAdapter(
    private val fragmentManager: FragmentManager,
    private val isPremiumUser: Boolean,
    private val navController: NavController
) : RecyclerView.Adapter<BooksAdapter.BooksHolder>() {

    private val listOfBooks = ArrayList<Book>()
    private var isNetworkActive = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        isNetworkActive = Network.isNetworkAvailable(parent.context)

        val item = if (viewType == AD_TYPE) {
            LayoutInflater.from(parent.context).inflate(R.layout.native_ad_type_2, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        }

        return BooksHolder(item = item, fragmentManager = fragmentManager)
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        if (isPremiumUser || !isNetworkActive) {
            holder.bind(listOfBooks[position])
            return
        }

        if (getItemViewType(position) == AD_TYPE) {
            AdMob.loadNativeAd(
                context = holder.itemView.context,
                root = holder.itemView.rootView as ViewGroup
            )
        } else {
            if (position > AD_CONDITION) {
                holder.bind(listOfBooks[position - 1])
            } else {
                holder.bind(listOfBooks[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPremiumUser || !isNetworkActive) CONTENT_TYPE
        else {
            when (position) {
                AD_CONDITION -> AD_TYPE
                else -> CONTENT_TYPE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isPremiumUser || listOfBooks.size < AD_CONDITION || !isNetworkActive) listOfBooks.size
        else listOfBooks.size + 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBooks(books: List<Book>) {
        listOfBooks.clear()
        listOfBooks.addAll(books)
        notifyDataSetChanged()
    }

    class BooksHolder(item: View, private val fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(item) {

        fun bind(book: Book) {
            val context: Context = itemView.context
            val binding = ItemBookBinding.bind(itemView)
            val sharedPrefs =
                context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)

            // Title and Author
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            // Decode Book Image
            val decodedBitmap = Base64Coder.decodeImageToByte(book.encodedImage)
            binding.bookImg.setImageBitmap(decodedBitmap)
            // Button Add Book To Favorite
            binding.toggleLike.setOnCheckedChangeListener { _, isChecked ->
                sharedPrefs.edit().putBoolean(Constants.FAVORITE_BOOK + book.id, isChecked).apply()
            }
            binding.toggleLike.isChecked = book.favorite
            // Open Book
            binding.root.setOnClickListener {
                val userPremiumStatus = sharedPrefs.getBoolean(Constants.USER_PREMIUM_STATUS, false)
                if (book.isPremium && !userPremiumStatus) {
                    val dialog = RestrictedContentDialogFragment(
                        withAdsOption = true,
                        onUserEarnedRewardListener = {
                            itemView.findNavController().navigate(
                                R.id.bookReadingFragment,
                                bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                            )
                        }
                    )
                    dialog.show(fragmentManager, "TAG")
                } else {
                    itemView.findNavController().navigate(
                        R.id.bookReadingFragment,
                        bundleOf(BooksFragment.ARG_OPENED_BOOK to book.id)
                    )
                }

            }
            // Premium or not
            if (book.isPremium) {
                binding.cardFree.visibility = View.GONE
                binding.imgPremium.visibility = View.VISIBLE
            } else {
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