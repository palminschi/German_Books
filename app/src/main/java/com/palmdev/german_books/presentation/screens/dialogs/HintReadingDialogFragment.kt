package com.palmdev.german_books.presentation.screens.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogHintReadingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HintReadingDialogFragment : DialogFragment(R.layout.dialog_hint_reading) {

    private lateinit var binding: DialogHintReadingBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogHintReadingBinding.bind(view)

        binding.btnClose.setOnClickListener { dialog?.cancel() }

    }
}