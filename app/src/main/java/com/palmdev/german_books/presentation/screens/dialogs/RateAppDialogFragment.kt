package com.palmdev.german_books.presentation.screens.dialogs

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.DialogRateAppBinding
import com.palmdev.german_books.presentation.screens.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateAppDialogFragment : DialogFragment(R.layout.dialog_rate_app) {

    private lateinit var binding: DialogRateAppBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogRateAppBinding.bind(view)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnClose.setOnClickListener { dialog?.cancel() }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.setUserRatedApp()
            if (rating >= 4f) {
                val appPackageName = requireActivity().packageName
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }
            Toast.makeText(requireContext(), getString(R.string.thanks), Toast.LENGTH_SHORT).show()
            dialog?.cancel()
        }

    }
}