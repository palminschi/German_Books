package com.palmdev.german_books.presentation.screens.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogRestrictedContentBinding

class RestrictedContentDialogFragment : DialogFragment(R.layout.dialog_restricted_content) {

    private lateinit var binding: DialogRestrictedContentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogRestrictedContentBinding.bind(view)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        Glide.with(binding.img)
            .load(R.drawable.ic_diamond)
            .into(binding.img)

        binding.btnPurchase.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(R.id.shopFragment)
        }

        binding.btnClose.setOnClickListener { dialog?.dismiss() }

    }
}