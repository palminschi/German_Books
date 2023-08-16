package com.palmdev.german_books.presentation.screens.onboarding.languages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.palmdev.german_books.R
import com.palmdev.german_books.databinding.FragmentOnboardingLanguagesBinding
import com.palmdev.german_books.legacy.models.TranslatorLanguage
import com.palmdev.german_books.presentation.BaseFragment
import com.palmdev.german_books.presentation.screens.onboarding.OnBoardingViewModel
import com.palmdev.german_books.utils.Languages
import com.palmdev.german_books.utils.Translate
import com.palmdev.german_books.utils.TranslatorLanguages
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class OnBoardingLanguagesFragment :
    BaseFragment<FragmentOnboardingLanguagesBinding>(FragmentOnboardingLanguagesBinding::inflate) {

    private val mSelectedTranslatorLanguage = MutableLiveData<TranslatorLanguage>()
    private val viewModel by viewModels<OnBoardingViewModel>()

    override val isNavigationVisible: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TranslatorLanguages.initLanguages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSelectedTranslatorLanguage.observe(viewLifecycleOwner) { language ->
            binding.tvLanguage.text = language.name
            binding.btnConfirm.setOnClickListener {

                Translate.createTranslator(
                    sourceLanguage = Languages.learningLanguage.code,
                    targetLanguage = language.code
                )
                viewModel.setUserLanguage(languageName = language.name, languageCode = language.code)
                findNavController().navigate(R.id.onBoardingPurposeFragment)
            }
        }

        initListView()
    }

    private fun initListView() {

        val languages = TranslatorLanguages.allTranslatorLanguages

        val adapter = LanguageAdapter(requireContext(), languages)

        binding.listView.adapter = adapter
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            mSelectedTranslatorLanguage.postValue(languages[position])
        }

        val deviceLanguage = Locale.getDefault().language
        if (!deviceLanguage.equals("de")) {
            val foundLang = languages.find { it.code == deviceLanguage }
            if (foundLang != null) {
                binding.listView.setSelection(languages.indexOf(foundLang))
            }
        }


    }
}