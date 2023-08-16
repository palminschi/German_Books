package com.palmdev.german_books.presentation.screens.purchases

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.BuildConfig
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentPurchasesEndBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.SUPPORT_EMAIL

class PurchasesEndFragment : BaseFragment<FragmentPurchasesEndBinding>(FragmentPurchasesEndBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.btnCloseMain.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.btnContactEmail.setOnClickListener {
            val subject =
                "Support ${getString(R.string.app_name)} | ${BuildConfig.VERSION_NAME}"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$SUPPORT_EMAIL?subject=$subject")
            }
            startActivity(intent)
        }

        binding.btnDetails.setOnClickListener {
            binding.layoutInfo.visibility = View.VISIBLE
        }

        binding.btnManageSubs.setOnClickListener {
            val subscriptionsUrl = "https://play.google.com/store/account/subscriptions"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subscriptionsUrl))
            startActivity(intent)
        }
    }
}