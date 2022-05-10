package com.palmdev.german_books.presentation.screens.dialog_save_word

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.palmdev.domain.model.Word
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogSaveWordBinding
import com.palmdev.german_books.presentation.screens.dialog_restricted_content.RestrictedContentDialogFragment
import com.palmdev.german_books.utils.AppReview
import com.palmdev.german_books.utils.GoogleMLKitTranslator
import com.palmdev.german_books.utils.VoiceText
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaveWordDialogFragment(
    private val word: String? = null,
    private val translatedWord: String? = null
) : DialogFragment() {

    private lateinit var mDialog: Dialog
    private val mWordsLimit = 30
    private lateinit var binding: DialogSaveWordBinding
    private val viewModel: SaveWordViewModel by viewModel()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = layoutInflater.inflate(R.layout.dialog_save_word, null)

        binding = DialogSaveWordBinding.bind(view)

        // Create dialog
        mDialog = Dialog(view.context)
        mDialog.setContentView(view)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set texts
        binding.dialogWord.setText(word)
        binding.dialogTranslatedWord.setText(translatedWord)

        // Text to speech
        val voiceText = VoiceText(requireContext())
        voiceText.init()
        binding.btnSound.setOnClickListener {
            voiceText.play(binding.dialogWord.text.toString())
        }

        binding.btnCancel.setOnClickListener { mDialog.dismiss() }

        viewModel.initWords()

        // Saving
        var isPremiumUser = false
        var numberOfSavedWords = 0
        var lastWord: Word? = null

        viewModel.words.observe(this) {
            if (!it.isNullOrEmpty()) {
                numberOfSavedWords = it.size
                lastWord = it.last()
            }
        }
        viewModel.userPremiumStatus.observe(this) {
            isPremiumUser = it
        }

        binding.btnSave.setOnClickListener {
            mDialog.dismiss()
            // App Review
            if (numberOfSavedWords == 4) AppReview.rateApp(requireActivity())
            // Saving
            if (numberOfSavedWords >= mWordsLimit && !isPremiumUser) {
                val dialogRestrictedContent =
                    RestrictedContentDialogFragment(withAdsOption = false)
                dialogRestrictedContent.show(parentFragmentManager, "TAG")
            } else {
                viewModel.addWord(
                    word = binding.dialogWord.text.toString(),
                    translation = binding.dialogTranslatedWord.text.toString(),
                    lastWord = lastWord
                )
            }
        }
        // Translate
        if (binding.dialogTranslatedWord.text.isEmpty()) {
            translate()
        } else binding.progressBar.visibility = View.GONE

        return mDialog
    }


    private fun translate() {
        val word = binding.dialogWord.text.toString()
        if (word.isNotEmpty()) {
            binding.dialogTranslatedWord.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            GoogleMLKitTranslator
                .translate(word)
                ?.addOnSuccessListener {
                    binding.dialogTranslatedWord.setText(it)
                    binding.dialogTranslatedWord.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.dialogTranslatedWord.visibility = View.VISIBLE
        }
    }

}