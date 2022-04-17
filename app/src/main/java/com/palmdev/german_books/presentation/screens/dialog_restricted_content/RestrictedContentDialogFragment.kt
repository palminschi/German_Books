package com.palmdev.german_books.presentation.screens.dialog_restricted_content

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogRestrictedContentFragmentBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestrictedContentDialogFragment(
    private val withAdsOption: Boolean = false
//TODO ADS
) : DialogFragment() {

    private lateinit var binding: DialogRestrictedContentFragmentBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.dialog_restricted_content_fragment, null)
        binding = DialogRestrictedContentFragmentBinding.bind(view)

        val dialog = Dialog(requireContext())
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnClose.setOnClickListener { onDismiss(dialog) }
        binding.btnPurchase.setOnClickListener {
            onDismiss(dialog)
            findNavController().navigate(R.id.shopFragment)
        }
        if (withAdsOption) {
            binding.showAdsContainer.visibility = View.VISIBLE
            binding.dialogSubTitle.text = getText(R.string.limitedContent)
            binding.btnShowAds.setOnClickListener {
                // TODO Ads
            }
        } else {
            binding.showAdsContainer.visibility = View.GONE
            binding.dialogSubTitle.text = getText(R.string.max30Words)
        }
        return dialog
    }


}