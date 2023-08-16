package com.palmdev.german_books.presentation.screens.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogFragmentVideosLimitBinding

class VideosLimitDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentVideosLimitBinding

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_fragment_videos_limit, null)
        binding = DialogFragmentVideosLimitBinding.bind(view)
        val dialog = Dialog(view.context)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnClose.setOnClickListener {
            dialog.cancel()
        }
        binding.btnGetPremium.setOnClickListener {
            dialog.cancel()
            findNavController().navigate(R.id.shopFragment)
        }

        return dialog
    }
}