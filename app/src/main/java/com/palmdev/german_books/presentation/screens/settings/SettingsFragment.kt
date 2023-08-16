package com.palmdev.german_books.presentation.screens.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.palmdev.german_books.BuildConfig
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentSettingsBinding
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.utils.APP_STORE_FULL_URL
import com.palmdev.german_books.utils.CAUGHT_ERROR
import com.palmdev.german_books.utils.DEVELOPER_ACCOUNT_ID
import com.palmdev.german_books.utils.FACEBOOK_URL
import com.palmdev.german_books.utils.INSTAGRAM_URL
import com.palmdev.german_books.utils.PRIVACY_POLICY_URL
import com.palmdev.german_books.utils.TELEGRAM_INVITE_URL
import com.palmdev.german_books.utils.TIKTOK_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLanguage.let {
            binding.tvNativeLangName.text = it.name
            if (it.imageUrl != null) {
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(binding.icUserLang)
            }
        }
        binding.btnUserLanguage.setOnClickListener {
            findNavController().navigate(R.id.translatorLanguagesDialogFragment)
        }

        binding.btnOtherLanguages.setOnClickListener {
            findNavController().navigate(R.id.otherLanguagesFragment)
        }

        binding.tvAppVersion.text = BuildConfig.VERSION_NAME

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.btnSubscription.setOnClickListener {
            findNavController().navigate(R.id.shopFragment)
        }

        binding.btnPolicy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "${getString(R.string.shareAppText)} $APP_STORE_FULL_URL"
            )
            startActivity(Intent.createChooser(shareIntent, getString(R.string.inviteFriend)))
        }

        binding.btnRateApp.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                startActivity(intent)
            } catch (e: Exception) {
                e.message?.let { Log.e(CAUGHT_ERROR, it) }
            }
        }
        binding.btnOurApps.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=$DEVELOPER_ACCOUNT_ID")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                startActivity(intent)
            } catch (e: Exception) {
                e.message?.let { Log.e(CAUGHT_ERROR, it) }
            }
        }
        binding.btnSocialFB.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnSocialInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnSocialTikTok.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TIKTOK_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnSocialTelegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_INVITE_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}