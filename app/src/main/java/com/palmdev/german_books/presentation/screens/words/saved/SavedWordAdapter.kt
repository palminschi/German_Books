package com.palmdev.german_books.presentation.screens.words.saved

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.ItemWordBinding
import com.palmdev.german_books.domain.model.SavedWord
import com.palmdev.german_books.utils.TTS

class SavedWordAdapter(
    private val context: Context,
    private val tts: TTS,
    private val words: List<SavedWord>,
    private val needRepetition: Boolean,
    private val onClickDeleteItem: (word: SavedWord) -> Unit
) : BaseAdapter() {

    private lateinit var binding: ItemWordBinding

    override fun getCount(): Int {
        return words.size
    }

    override fun getItem(position: Int): Any {
        return words[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false)
        }
        binding = ItemWordBinding.bind(view!!)

        binding.tvWord.text = words[position].value
        binding.tvTranslation.text = words[position].translation

        words[position].transcription?.let {
            binding.tvTranscription.text = it
            binding.tvTranscription.visibility = View.VISIBLE
            binding.tvTranscriptionLine.visibility = View.VISIBLE
        }

        binding.btnDelete.visibility = View.VISIBLE
        binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.deleteWordConfirmation))
                .setNegativeButton(context.getString(R.string.no)) { dialog, _ -> dialog?.cancel() }
                .setPositiveButton(context.getString(R.string.delete)) {dialog, _ ->
                    onClickDeleteItem(words[position])
                    dialog?.dismiss()
                    notifyDataSetChanged()
                }
                .create()
            dialog.show()
        }


     /*   if (needRepetition) {
            binding.redHint.visibility = View.VISIBLE
        } else {
            binding.redHint.visibility = View.GONE
        }*/

        binding.btnSound.setOnClickListener {
            tts.say(words[position].value)
        }

        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        return view
    }


}